package com.vp.monitoring_server.service.impl;

import com.vp.monitoring_server.service.AgentPollService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AgentPollServiceImpl implements AgentPollService {

    @Async
    @Override
    public CompletableFuture<String> pollAgent(String agentUrl) {
        log.info("Опрос агента: {}", agentUrl);
        try {
            Thread.sleep(1000); // Имитация опроса
            log.info("Агент {} успешно опрошен", agentUrl);
            return CompletableFuture.completedFuture("Опрос завершён для: " + agentUrl);
        } catch (InterruptedException e) {
            log.error("Ошибка при опросе агента {}", agentUrl, e);
            return CompletableFuture.completedFuture("Ошибка опроса: " + agentUrl);
        }
    }
}
