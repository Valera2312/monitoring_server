package com.vp.monitoring_server.poll.seduler;

import com.vp.monitoring_server.poll.AgentPollService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduledAgentPoller {

    private AgentPollService agentPollService;

    @Scheduled(fixedRate = 5000) // Каждые 5 секунд
    public void pollAgents() throws ExecutionException, InterruptedException {

        List<String> agents = List.of("http://agent1.local", "http://agent2.local", "http://agent3.local");

        List<CompletableFuture<String>> futures = agents.stream()
                .map(agent -> agentPollService.pollAgent(agent))
                .toList();

        // Ждём завершения всех задач
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> {
                    futures.forEach(future -> {
                        try {
                            log.info(future.get());
                        } catch (InterruptedException | ExecutionException e) {
                            log.error("Ошибка при выполнении задачи: ", e);
                        }
                    });
                }).join();

//        agents.forEach(agent -> agentPollService.pollAgent(agent)
//                .thenAccept(result -> log.info("Результат опроса агента {}: {}", agent, result))
//                .exceptionally(e -> {
//                    log.error("Ошибка при опросе агента {}: {}", agent, e.getMessage());
//                    return null;
//                })
//        );
    }
}
