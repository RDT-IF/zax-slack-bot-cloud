package org.rdtif.zaxslackbot.userinterface;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class ExtentTest {
    @Test
    void equalsContract() {
        EqualsVerifier.forClass(Extent.class).verify();
    }
}
