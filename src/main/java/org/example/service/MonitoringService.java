package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.handler.AlertConfigHandler;
import org.example.model.AlertConfig;
import org.example.model.ClientConfig;
import org.example.model.DispatchStrategy;
import org.example.handler.SimpleCountHandler;
import org.example.handler.SlidingWindowHandler;
import org.example.handler.TumblingWindowHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitoringService {
    private static final Logger logger = LogManager.getLogger(MonitoringService.class);

    private final Map<String, List<ClientConfig>> clientConfigs;
    private final Map<String, Integer> eventCounts;
    private final Map<String, Long> eventTimestamps;
    private final Map<AlertConfig.Type, AlertConfigHandler> alertConfigHandlers;
    private final AlertingService alertingService;

    public MonitoringService(List<ClientConfig> clientConfigs) {
        this.clientConfigs = new HashMap<>();
        this.eventCounts = new HashMap<>();
        this.eventTimestamps = new HashMap<>();
        this.alertConfigHandlers = new HashMap<>();

        // Initialize the dispatch strategies map
        Map<String, List<DispatchStrategy>> dispatchStrategies = new HashMap<>();

        for (ClientConfig config : clientConfigs) {
            this.clientConfigs.computeIfAbsent(config.getClient(), k -> new ArrayList<>()).add(config);
            String key = config.getClient() + ":" + config.getEventType();
            dispatchStrategies.put(key, config.getDispatchStrategyList());
        }

        this.alertingService = new AlertingService(dispatchStrategies);

        alertConfigHandlers.put(AlertConfig.Type.SIMPLE_COUNT, new SimpleCountHandler());
        alertConfigHandlers.put(AlertConfig.Type.TUMBLING_WINDOW, new TumblingWindowHandler());
        alertConfigHandlers.put(AlertConfig.Type.SLIDING_WINDOW, new SlidingWindowHandler());
    }

    public void captureEvent(String client, String eventType) {
        String key = client + ":" + eventType;
        eventCounts.put(key, eventCounts.getOrDefault(key, 0) + 1);
        eventTimestamps.putIfAbsent(key, System.currentTimeMillis());

        for (ClientConfig config : clientConfigs.get(client)) {
            if (config.getEventType().equals(eventType)) {
                logger.info("MonitoringService: Client {} {} {} starts", client, eventType, config.getAlertConfig().getType());
                checkThreshold(config, key);
                logger.info("MonitoringService: Client {} {} {} ends", client, eventType, config.getAlertConfig().getType());
            }
        }
    }

    private void checkThreshold(ClientConfig config, String key) {
        AlertConfig alertConfig = config.getAlertConfig();
        int eventCount = eventCounts.get(key);
        long currentTime = System.currentTimeMillis();
        long startTime = eventTimestamps.get(key);

        AlertConfigHandler handler = alertConfigHandlers.get(alertConfig.getType());
        if (handler != null && handler.checkThreshold(eventCount, startTime, currentTime, alertConfig)) {
            logger.info("MonitoringService: Client {} {} threshold breached", config.getClient(), config.getEventType());
            alertingService.dispatchAlerts(config.getClient(), config.getEventType());
            eventCounts.put(key, 0);
            eventTimestamps.put(key, currentTime);
        }
    }
}


