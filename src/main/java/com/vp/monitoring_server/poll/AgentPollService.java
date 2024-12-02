package com.vp.monitoring_server.poll;

import java.util.concurrent.CompletableFuture;

public interface AgentPollService {

   CompletableFuture<String> pollAgent(String agentUrl);
}
