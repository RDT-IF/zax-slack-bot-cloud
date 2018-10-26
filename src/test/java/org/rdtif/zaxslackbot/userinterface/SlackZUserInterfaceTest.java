package org.rdtif.zaxslackbot.userinterface;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SlackZUserInterfaceTest {
    private final SlackTextScreen slackTextScreen = mock(SlackTextScreen.class);
    private final SlackZUserInterface slackZUserInterface = new SlackZUserInterface(slackTextScreen);

    @Test
    void initializeInitializesScreen() {
        slackZUserInterface.initialize(0);

        verify(slackTextScreen).initialize();
    }

    @Test
    void initializeCallsUpdate() {
        slackZUserInterface.initialize(0);

        verify(slackTextScreen).update();
    }

    @Test
    void displayMessageWhenFatal() {
        String message = RandomStringUtils.randomAlphabetic(13);

        assertThrows(ZaxFatalException.class, () -> new SlackZUserInterface(slackTextScreen).fatal(message));


        verify(slackTextScreen).print(message);
    }

    @Test
    void fatalThrowsException() {
        assertThrows(ZaxFatalException.class, () -> new SlackZUserInterface(slackTextScreen).fatal(RandomStringUtils.randomAlphabetic(13)));
    }

    @Test
    void fatalThrowsExceptionWithMessage() {
        String message = RandomStringUtils.randomAlphabetic(13);
        ZaxFatalException exception = assertThrows(ZaxFatalException.class, () -> new SlackZUserInterface(slackTextScreen).fatal(message));
        assertThat(exception.getMessage(), equalTo(message));
    }

    @Test
    void defaultFontProportionalShouldReturnFalse() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);
        assertThat(userInterface.defaultFontProportional(), equalTo(false));
    }

}
