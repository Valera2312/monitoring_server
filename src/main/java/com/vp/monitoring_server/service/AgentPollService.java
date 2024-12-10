package com.vp.monitoring_server.service;

import java.util.concurrent.CompletableFuture;

public interface AgentPollService {

   CompletableFuture<String> pollAgent(String agentUrl);
}
