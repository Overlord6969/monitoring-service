package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.DispatchStrategy;

import java.util.List;
import java.util.Map;

public class AlertingService {
    private static final Logger logger = LogManager.getLogger(AlertingService.class);
    private final Map<String, List<DispatchStrategy>> dispatchStrategies;

    public AlertingService(Map<String, List<DispatchStrategy>> dispatchStrategies) {
        this.dispatchStrategies = dispatchStrategies;
    }

    public void dispatchAlerts(String client, String eventType) {
        String key = client + ":" + eventType;
        List<DispatchStrategy> strategies = dispatchStrategies.get(key);
        if (strategies != null) {
            for (DispatchStrategy strategy : strategies) {
                logger.info("AlertingService: Dispatching to {}", strategy.getType());
                strategy.dispatch();
            }
        } else {
            logger.warn("No dispatch strategies found for client {} and event type {}", client, eventType);
        }
    }
}
