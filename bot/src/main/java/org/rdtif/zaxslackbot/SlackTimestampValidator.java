package org.rdtif.zaxslackbot;

import java.time.Instant;

class SlackTimestampValidator {
    boolean validate(String eventTimestamp) {
        long timestamp = Long.parseLong(eventTimestamp);
        long now = Instant.now().toEpochMilli();
        long elapsed = now - timestamp;
        return elapsed >= 0 && elapsed <= TIME_LIMIT_IN_MILLISECONDS;
    }

    private static final int TIME_LIMIT_IN_MINUTES = 5;
    private final static long TIME_LIMIT_IN_MILLISECONDS = TIME_LIMIT_IN_MINUTES * 60 * 1000;
}