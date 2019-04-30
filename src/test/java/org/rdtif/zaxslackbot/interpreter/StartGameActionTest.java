package org.rdtif.zaxslackbot.interpreter;

import com.google.common.eventbus.EventBus;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.zaxsoft.zax.zmachine.ZCPU;
import com.zaxsoft.zax.zmachine.ZUserInterface;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rdtif.zaxslackbot.GameFileRepository;
import org.rdtif.zaxslackbot.ZaxSlackBotConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StartGameActionTest {
    private static final String DEFAULT_MESSAGE = RandomStringUtils.randomAlphabetic(13);
    private static final String START_MESSAGE = RandomStringUtils.randomAlphabetic(12);
    private static final String GAME_DIRECTORY_PROPERTY_NAME = "game-directory";

    private final LanguagePattern languagePattern = createPattern();
    private final ZCpuFactory zCpuFactory = mock(ZCpuFactory.class);
    private final ZCPU zcpu = mock(ZCPU.class);
    private final SlackSession session = mock(SlackSession.class);
    private final SlackChannel channel = mock(SlackChannel.class);
    private final GameFileRepository repository = mock(GameFileRepository.class);
    private final StartGameAction startGameAction = new StartGameAction(new ZaxSlackBotConfiguration(createProperties()), session, repository, zCpuFactory, new EventBus());

    @BeforeEach
    void beforeEach() {
        when(zCpuFactory.create(any(ZUserInterface.class))).thenReturn(zcpu);
    }

    @Test
    void returnDefaultMessageIfGameDoesNotExist() {
        String badGameName = RandomStringUtils.randomAlphabetic(12);
        String message = startGameAction.execute(channel, "play " + badGameName, languagePattern);
        assertThat(message, equalTo(DEFAULT_MESSAGE));
    }

    @Test
    void returnStartMessageWithGameName() {
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));
        String message = startGameAction.execute(channel, input, languagePattern);

        assertThat(message, equalTo(START_MESSAGE + " " + gameName));
    }

    @Test
    void useFactoryToCreateZCPU() {
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));
        startGameAction.execute(channel, input, languagePattern);

        verify(zCpuFactory).create(any(ZUserInterface.class));
    }

    @Test
    void initializeZCPU() {
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));
        startGameAction.execute(channel, input, languagePattern);

        verify(zcpu).initialize(anyString());
    }

    @Test
    void initializeZCPUWithGamePath() {
        String gameDirectory = RandomStringUtils.randomAlphabetic(14);
        Properties properties = new Properties();
        properties.setProperty(GAME_DIRECTORY_PROPERTY_NAME, gameDirectory);
        StartGameAction startGameAction = new StartGameAction(new ZaxSlackBotConfiguration(properties), null, repository, zCpuFactory, new EventBus());
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));

        startGameAction.execute(channel, input, languagePattern);

        verify(zcpu).initialize(gameDirectory + "/" + gameName);
    }

    @Test
    void runZCPU() {
        String gameName = RandomStringUtils.randomAlphabetic(12);
        String input = "play " + gameName;

        when(repository.fileNames()).thenReturn(Collections.singletonList(gameName));

        startGameAction.execute(channel, input, languagePattern);

        verify(zcpu).start();
    }

    private LanguagePattern createPattern() {
        LanguagePattern pattern = new LanguagePattern();
        pattern.setPattern("(play|start|open|run) (.*)");
        LanguageResponse response1 = new LanguageResponse();
        response1.setName("default");
        response1.setResponses(Collections.singletonList(DEFAULT_MESSAGE));
        LanguageResponse response2 = new LanguageResponse();
        response2.setName("start");
        response2.setResponses(Collections.singletonList(START_MESSAGE));
        pattern.setResponses(Arrays.asList(response1, response2));
        return pattern;
    }

    private Properties createProperties() {
        Properties properties = new Properties();
        properties.setProperty(GAME_DIRECTORY_PROPERTY_NAME, RandomStringUtils.randomAlphabetic(13));
        return properties;
    }
}