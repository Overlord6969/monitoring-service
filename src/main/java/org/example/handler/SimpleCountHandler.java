package org.example.handler;

import org.example.model.AlertConfig;

public class SimpleCountHandler implements AlertConfigHandler {
    @Override
    public boolean checkThreshold(int eventCount, long startTime, long currentTime, AlertConfig config) {
        return eventCount >= config.getCount();
    }
}
