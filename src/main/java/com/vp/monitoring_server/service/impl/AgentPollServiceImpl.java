package com.vp.monitoring_server.service.impl;

import com.vp.monitoring_server.network.MonitoringClient;
import com.vp.monitoring_server.network.MonitoringClientHandler;
import com.vp.monitoring_server.network.Socketimpl;
import com.vp.monitoring_server.service.AgentPollService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AgentPollServiceImpl implements AgentPollService {

    private static final String CPU_LOAD_METRIC = "get_cpu_load";

    @Override
    public void pollAgent(String agentUrl, String metric /* agent list */) throws InterruptedException {

        MonitoringClient monitoringClient = new MonitoringClient("127.0.0.1", 8080);
        monitoringClient.start();
        monitoringClient.startPolling(CPU_LOAD_METRIC);
    }

    @PostConstruct
    public void init() throws InterruptedException {

        log.info("AgentPollServiceImpl initialized. Starting polling...");
        // Запуск опроса агентов через некоторое время

        //запросить список агентов


        pollAgent("http://localhost", CPU_LOAD_METRIC);
    }
}
