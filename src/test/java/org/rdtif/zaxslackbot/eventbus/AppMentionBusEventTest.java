package org.rdtif.zaxslackbot.eventbus;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AppMentionBusEventTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(AppMentionBusEvent.class).verify();
    }
}