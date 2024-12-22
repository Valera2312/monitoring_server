package com.vp.monitoring_server;

import com.vp.monitoring_server.service.AgentPollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MonitoringServerApplication {

	@Autowired
	AgentPollService agentPollService;

	public static void main(String[] args) {

		SpringApplication.run(MonitoringServerApplication.class, args);

	}
}
