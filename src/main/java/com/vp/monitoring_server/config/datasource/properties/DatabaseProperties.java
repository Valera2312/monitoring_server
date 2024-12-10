package com.vp.monitoring_server.config.datasource.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;
@Getter
@Component
public class DatabaseProperties {

    private Map<String, DataSourceConfig> databaseConfigs;

    @Getter
    @Setter
    public static class DataSourceConfig {
        private String driverClassName;
        private String url;
        private String username;
        private String password;

    }
}
