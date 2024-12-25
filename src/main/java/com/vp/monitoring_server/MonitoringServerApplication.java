package com.vp.monitoring_server;

import com.vp.monitoring_server.config.datasource.properties.DatabaseProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(DatabaseProperties.class)
@SpringBootApplication
public class MonitoringServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(MonitoringServerApplication.class, args);

	}
}
