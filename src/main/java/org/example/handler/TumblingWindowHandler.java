package org.example.handler;

import org.example.model.AlertConfig;

public class TumblingWindowHandler implements AlertConfigHandler {

    @Override
    public boolean checkThreshold(int eventCount, long startTime, long currentTime, AlertConfig config) {
        return eventCount >= config.getCount() && (currentTime - startTime) <= config.getWindowSizeInSecs() * 1000L;
    }
}
