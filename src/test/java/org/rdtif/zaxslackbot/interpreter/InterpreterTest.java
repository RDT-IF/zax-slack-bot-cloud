package org.rdtif.zaxslackbot.interpreter;

import com.google.common.collect.ImmutableMap;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.rdtif.zaxslackbot.eventbus.AppMentionBusEvent;
import org.rdtif.zaxslackbot.slack.SlackClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class InterpreterTest {
    private static final String TEST_ACTION_RESPONSE = RandomStringUtils.randomAlphanumeric(12);
    private static final Map<LanguageAction, Action> actionMap = ImmutableMap.<LanguageAction, Action>builder()
            .put(LanguageAction.ListGames, new TestAction())
            .build();
    private final SlackClient slackClient = mock(SlackClient.class);
    private final Interpreter interpreter = new Interpreter(actionMap, slackClient);

    @Test
    void returnResponseWhenInputMatchesPattern() {
        interpreter.registerPattern(createLanguagePattern("pattern", "some response"));
        interpreter.interpret(new AppMentionBusEvent("channelID", "pattern"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text("some response")
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    @Test
    void returnResponseWhenInputMatchesPatternIncludesChannelID() {
        interpreter.registerPattern(createLanguagePattern("pattern", "some response"));
        interpreter.interpret(new AppMentionBusEvent("channelID", "pattern"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text("some response")
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    @Test
    void matchCorrectLanguagePattern() {
        interpreter.registerPattern(createLanguagePattern("matching pattern", "matching response"));
        interpreter.registerPattern(createLanguagePattern("non-matching pattern", "non-matching response"));
        interpreter.interpret(new AppMentionBusEvent("channelID", "matching pattern"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text("matching response")
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    @Test
    void caseInsensitiveMatching() {
        interpreter.registerPattern(createLanguagePattern("PATTERN", "some response"));
        interpreter.interpret(new AppMentionBusEvent("channelID", "pattern"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text("some response")
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    @Test
    void noRegisteredPatterns() {
        interpreter.interpret(new AppMentionBusEvent("channelID", "input"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text(LanguagePattern.DEFAULT_MESSAGE)
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    @Test
    void inputDoesNotMatchAnyPattern() {
        interpreter.registerPattern(createLanguagePattern("/some other pattern/g", "none matching pattern"));
        interpreter.interpret(new AppMentionBusEvent("channelID", "input"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text(LanguagePattern.DEFAULT_MESSAGE)
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    @Test
    void returnDefaultResponseIfPatternHasNoResponses() {
        interpreter.registerPattern(createLanguagePattern("matching pattern"));
        interpreter.interpret(new AppMentionBusEvent("channelID", "matching pattern"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text(LanguagePattern.DEFAULT_MESSAGE)
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    @Test
    void doNotRegisterNullPattern() {
        interpreter.registerPattern(null);
        interpreter.interpret(new AppMentionBusEvent("channelID", "input"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text(LanguagePattern.DEFAULT_MESSAGE)
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    @Test
    void doNotRegisterPatternWithoutAPattern() {
        interpreter.registerPattern(new LanguagePattern());
        interpreter.interpret(new AppMentionBusEvent("channelID", "input"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text(LanguagePattern.DEFAULT_MESSAGE)
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    @Test
    void useSpecifiedAction() {
        LanguagePattern languagePattern = new LanguagePattern();
        languagePattern.setPattern("input");
        languagePattern.setAction(LanguageAction.ListGames);

        interpreter.registerPattern(languagePattern);
        interpreter.interpret(new AppMentionBusEvent("channelID", "input"));

        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .text(TEST_ACTION_RESPONSE)
                .build();
        verify(slackClient).chatPostMessage(request);
    }

    private LanguagePattern createLanguagePattern(String pattern, String... response) {
        LanguagePattern languagePattern = new LanguagePattern();
        languagePattern.setPattern(pattern);

        LanguageResponse languageResponse = new LanguageResponse();
        languageResponse.setName("default");
        languageResponse.setResponses(Arrays.asList(response));
        languagePattern.setResponses(Collections.singletonList(languageResponse));

        return languagePattern;
    }

    private static class TestAction implements Action {
        @Override
        public String execute(String channelID, String input, LanguagePattern pattern) {
            return TEST_ACTION_RESPONSE;
        }
    }
}