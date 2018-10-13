package org.rdtif.zaxslackbot.userinterface;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SlackZUserInterfaceTest {
    @Test
    void fatalThrowsException() {
        assertThrows(ZaxFatalException.class, () -> new SlackZUserInterface().fatal(RandomStringUtils.randomAlphabetic(13)));
    }

    @Test
    void fatalThrowsExceptionWithMessage() {
        String message = RandomStringUtils.randomAlphabetic(13);
        ZaxFatalException exception = assertThrows(ZaxFatalException.class, () -> new SlackZUserInterface().fatal(message));
        assertThat(exception.getMessage(), equalTo(message));
    }

    @Test
    void defaultFontProportionalShouldReturnFalse() {
        SlackZUserInterface userInterface = new SlackZUserInterface();
        assertThat(userInterface.defaultFontProportional(), equalTo(false));
    }

}
