package com.vp.monitoring_server.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private final DataSourceFactory dataSourceFactory;

    private final Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();

    private final ThreadLocal<String> currentDataSource = new ThreadLocal<>();

    public DynamicRoutingDataSource(DataSource defaultDataSource, DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return currentDataSource.get();
    }

    public void switchDataSource(String dbKey) {
        if (!targetDataSources.containsKey(dbKey)) {
            DataSource newDataSource = dataSourceFactory.createDataSource(dbKey);
            targetDataSources.put(dbKey, newDataSource);
            super.setTargetDataSources(targetDataSources);
            super.afterPropertiesSet();
        }
        currentDataSource.set(dbKey);
    }

    public void clearDataSource() {
        currentDataSource.remove();
    }

}
