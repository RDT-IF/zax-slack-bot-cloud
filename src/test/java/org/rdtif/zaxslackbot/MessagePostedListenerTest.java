package org.rdtif.zaxslackbot;

import com.google.common.eventbus.EventBus;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPersona;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.SlackUser;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rdtif.zaxslackbot.interpreter.LanguageProcessor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessagePostedListenerTest {
    private final SlackPersona persona = mock(SlackPersona.class);
    private final SlackUser sender = mock(SlackUser.class);
    private final SlackSession slackSession = mock(SlackSession.class);
    private final SlackMessagePosted messagePosted = mock(SlackMessagePosted.class);
    private final SlackChannel channel = mock(SlackChannel.class);

    private final LanguageProcessor languageProcessor = new EchoProcessor();
    private final MessagePostedListener messagePostedListener = new MessagePostedListener(languageProcessor, new EventBus());

    private String id;

    @BeforeEach
    void setup() {
        id = RandomStringUtils.randomAlphabetic(5);
        when(persona.getId()).thenReturn(id);
        when(slackSession.sessionPersona()).thenReturn(persona);
        when(messagePosted.getChannel()).thenReturn(channel);
    }

    @Test
    void speakWhenSpokenTo() {
        String tag = "<@" + id + ">";
        String message = RandomStringUtils.randomAlphabetic(10);

        when(sender.isBot()).thenReturn(false);
        when(messagePosted.getSender()).thenReturn(sender);
        when(messagePosted.getMessageContent()).thenReturn(tag + " " + message);

        when(messagePosted.getChannel()).thenReturn(channel);

        messagePostedListener.onEvent(messagePosted, slackSession);

        verify(slackSession).sendMessage(eq(channel), eq(message));
    }

    @Test
    void speakWhenSpokenToInDirectMessage() {
        String message = RandomStringUtils.randomAlphabetic(10);

        when(sender.isBot()).thenReturn(false);
        when(messagePosted.getSender()).thenReturn(sender);
        when(messagePosted.getMessageContent()).thenReturn(message);

        when(channel.isDirect()).thenReturn(true);
        when(messagePosted.getChannel()).thenReturn(channel);

        messagePostedListener.onEvent(messagePosted, slackSession);

        verify(slackSession).sendMessage(eq(channel), eq(message));
    }

    @Test
    void whenAValidConversationResultsInAnException() {
        String tag = "<@" + id + ">";
        String message = RandomStringUtils.randomAlphabetic(10);
        RuntimeException exception = mock(RuntimeException.class);
        MessagePostedListener messagePostedListener = new MessagePostedListener(new ExceptionThrowingProcessor(exception), new EventBus());

        when(sender.isBot()).thenReturn(false);
        when(messagePosted.getSender()).thenReturn(sender);
        when(messagePosted.getMessageContent()).thenReturn(tag + " " + message);

        when(messagePosted.getChannel()).thenReturn(channel);

        messagePostedListener.onEvent(messagePosted, slackSession);

        verify(exception).printStackTrace();
    }

    @Test
    void doNotTalkToBots() {
        when(sender.isBot()).thenReturn(true);
        when(messagePosted.getSender()).thenReturn(sender);

        messagePostedListener.onEvent(messagePosted, slackSession);

        verify(slackSession, never()).sendMessage(any(SlackChannel.class), any(String.class));
    }

    @Test
    void doNotSpeakUnlessSpokenTo() {
        String message = RandomStringUtils.randomAlphabetic(10);

        when(sender.isBot()).thenReturn(false);
        when(messagePosted.getSender()).thenReturn(sender);
        when(messagePosted.getMessageContent()).thenReturn(message);

        messagePostedListener.onEvent(messagePosted, slackSession);

        verify(slackSession, never()).sendMessage(any(SlackChannel.class), any(String.class));
    }

    private static class EchoProcessor extends LanguageProcessor {
        EchoProcessor() {
            super(null);
        }

        @Override
        public String responseTo(SlackChannel channel, String input) {
            return input;
        }
    }

    private static class ExceptionThrowingProcessor extends LanguageProcessor {
        private final RuntimeException exception;

        ExceptionThrowingProcessor(RuntimeException exception) {
            super(null);
            this.exception = exception;
        }

        @Override
        public String responseTo(SlackChannel channel, String input) {
            throw exception;
        }
    }
}