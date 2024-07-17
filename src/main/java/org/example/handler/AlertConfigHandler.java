package org.example.handler;

import org.example.model.AlertConfig;

public interface AlertConfigHandler {
    boolean checkThreshold(int eventCount, long startTime, long currentTime, AlertConfig config);
}

