package org.rdtif.zaxslackbot.userinterface;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.rdtif.zaxslackbot.Zoey;

import java.awt.*;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SlackZUserInterfaceTest {
    // These are my best guess based on observations -- scaling will change the actual.
    private static final int FONT_WIDTH_IN_PIXELS = 8;
    private static final int FONT_HEIGHT_IN_PIXELS = 16;
    private static final int Z_MACHINE_BLACK = 2;
    private static final int Z_MACHINE_WHITE = 9;
    private final SlackTextScreen slackTextScreen = mock(SlackTextScreen.class);
    private final SlackZUserInterface slackZUserInterface = new SlackZUserInterface(slackTextScreen);

    @Test
    void initializeInitializesScreen() {
        slackZUserInterface.initialize(0);

        verify(slackTextScreen).initialize(0);
    }

    @Test
    void initializeCallsUpdate() {
        slackZUserInterface.initialize(0);

        verify(slackTextScreen).update();
    }

    @Test
    void eraseWindow() {
        int window = new Random().nextInt(1);
        slackZUserInterface.eraseWindow(window);

        verify(slackTextScreen).eraseWindow(window);
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
    void getScreenCharactersReturnsDimensionFromTextScreenExtent() {
        Extent size = Zoey.randomExtent();
        when(slackTextScreen.getSize()).thenReturn(size);
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        Dimension screenSizeInCharacters = userInterface.getScreenCharacters();

        assertThat(screenSizeInCharacters.height, equalTo(size.getRows()));
        assertThat(screenSizeInCharacters.width, equalTo(size.getColumns()));
    }

    @Test
    void getFontSizeReturnsEightBy16() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        Dimension fontSizeInPixels = userInterface.getFontSize();

        assertThat(fontSizeInPixels.width, equalTo(FONT_WIDTH_IN_PIXELS));
        assertThat(fontSizeInPixels.height, equalTo(FONT_HEIGHT_IN_PIXELS));
    }

    @Test
    void getScreenUnits() {
        Extent size = Zoey.randomExtent();
        when(slackTextScreen.getSize()).thenReturn(size);
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        Dimension screenSizeInPixels = userInterface.getScreenUnits();

        assertThat(screenSizeInPixels.width, equalTo(FONT_WIDTH_IN_PIXELS * size.getColumns()));
        assertThat(screenSizeInPixels.height, equalTo(FONT_HEIGHT_IN_PIXELS * size.getRows()));
    }

    @Test
    void getDefaultForeground() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        assertThat(userInterface.getDefaultForeground(), equalTo(Z_MACHINE_BLACK));
    }

    @Test
    void getDefaultBackground() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        assertThat(userInterface.getDefaultBackground(), equalTo(Z_MACHINE_WHITE));
    }

    @Test
    void hasBoldFaceShouldReturnTrue() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        assertThat(userInterface.hasBoldface(), equalTo(true));
    }

    @Test
    void hasItalicShouldReturnTrue() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        assertThat(userInterface.hasItalic(), equalTo(true));
    }

    @Test
    void hasFixedWidthShouldReturnTrue() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        assertThat(userInterface.hasFixedWidth(), equalTo(true));
    }


    @Test
    void defaultFontProportionalShouldReturnFalse() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        assertThat(userInterface.defaultFontProportional(), equalTo(false));
    }

    @Test
    void hasColorsShouldReturnFalse() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        assertThat(userInterface.hasColors(), equalTo(false));
    }

    @Test
    void hasTimedInputShouldReturnFalse() {
        SlackZUserInterface userInterface = new SlackZUserInterface(slackTextScreen);

        assertThat(userInterface.hasTimedInput(), equalTo(false));
    }
}
