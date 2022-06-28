package org.rdtif.zaxslackbot.eventbus;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class PostToChannelBusEventTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(PostToChannelBusEvent.class).verify();
    }
}