package com.vp.monitoring_server.service;

import java.util.concurrent.CompletableFuture;

public interface AgentPollService {

   void pollAgent(String agentUrl, String metric) throws InterruptedException;
}
