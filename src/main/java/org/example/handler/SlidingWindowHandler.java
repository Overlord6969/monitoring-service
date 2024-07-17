package org.example.handler;

import org.example.model.AlertConfig;

import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowHandler implements AlertConfigHandler {
    private final Queue<Long> eventTimestamps = new LinkedList<>();

    @Override
    public boolean checkThreshold(int eventCount, long startTime, long currentTime, AlertConfig config) {
        long windowSizeInMillis = config.getWindowSizeInSecs() * 1000L;
        long windowStart = currentTime - windowSizeInMillis;

        // Add the new event's timestamp
        eventTimestamps.add(currentTime);

        // Remove events that are outside the sliding window
        while (!eventTimestamps.isEmpty() && eventTimestamps.peek() < windowStart) {
            eventTimestamps.poll();
        }

        // Check if the number of events in the window meets the threshold
        return eventTimestamps.size() >= config.getCount();
    }
}

