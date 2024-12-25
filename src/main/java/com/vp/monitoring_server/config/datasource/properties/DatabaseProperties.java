package com.vp.monitoring_server.config.datasource.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "databases")
public class DatabaseProperties {

    private Map<String, DataSourceConfig> databaseConfigs;

    public DatabaseProperties(Map<String, DataSourceConfig> databaseConfigs) {
        this.databaseConfigs = databaseConfigs;
    }

    @Getter
    @Setter
    public static class DataSourceConfig {
        private String driverClassName;
        private String url;
        private String username;
        private String password;

    }
}
