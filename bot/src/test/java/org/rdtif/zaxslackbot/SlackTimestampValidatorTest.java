package org.rdtif.zaxslackbot;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SlackTimestampValidatorTest {
    private final SlackTimestampValidator validator = new SlackTimestampValidator();

    @Test
    void timeStampInValidRangeMinimum() {
        long timestamp = Instant.now().toEpochMilli();

        boolean valid = validator.validate(String.valueOf(timestamp));

        assertThat(valid, equalTo(true));
    }

    @Test
    void timeStampInValidRangeMaximum() {
        long timestamp = Instant.now().toEpochMilli() - (5 * 60 * 1000);

        boolean valid = validator.validate(String.valueOf(timestamp));

        assertThat(valid, equalTo(true));
    }

    @Test
    void timeStampAboveValidRange() {
        long timestamp = Instant.now().toEpochMilli() + 1;

        boolean valid = validator.validate(String.valueOf(timestamp));

        assertThat(valid, equalTo(false));
    }

    @Test
    void timeStampBelowValidRange() {
        long timestamp = Instant.now().toEpochMilli() - (5 * 60 * 1000) - 1;

        boolean valid = validator.validate(String.valueOf(timestamp));

        assertThat(valid, equalTo(false));
    }
}
