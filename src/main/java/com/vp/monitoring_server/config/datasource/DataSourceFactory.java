package com.vp.monitoring_server.config.datasource;

import com.vp.monitoring_server.config.datasource.properties.DatabaseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataSourceFactory {

    @Value("${databases.default}")
    private String dbNameDefault;

    private final DatabaseProperties databaseProperties;

    public DataSource createDataSource(String dbKey) {
        Map<String, DatabaseProperties.DataSourceConfig> configs = databaseProperties.getDatabaseConfigs();
        DatabaseProperties.DataSourceConfig config = configs.get(dbKey);

        if (config == null) {
            throw new IllegalArgumentException("Unknown database key: " + dbKey);
        }

        return DataSourceBuilder.create()
                .url(config.getUrl())
                .driverClassName(config.getDriverClassName())
                .username(config.getUsername())
                .password(config.getPassword())
                .build();
    }

    @Bean
    public DataSource defaultDataSource() {
        Map<String, DatabaseProperties.DataSourceConfig> configs = databaseProperties.getDatabaseConfigs();
        DatabaseProperties.DataSourceConfig config = configs.get(dbNameDefault);

        if (config == null) {
            throw new IllegalArgumentException("Default database configuration not found for key: " + dbNameDefault);
        }

        return DataSourceBuilder.create()
                .url(config.getUrl())
                .username(config.getUsername())
                .driverClassName(config.getDriverClassName())
                .password(config.getPassword())
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DynamicRoutingDataSource dynamicRoutingDataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dynamicRoutingDataSource);
        factoryBean.setPackagesToScan("com/vp/monitoring_server/domain/entities");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return factoryBean;
    }
}
