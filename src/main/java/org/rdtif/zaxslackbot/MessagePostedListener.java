package org.rdtif.zaxslackbot;

import java.io.IOException;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.rdtif.zaxslackbot.interpreter.LanguagePattern;
import org.rdtif.zaxslackbot.interpreter.LanguageProcessor;

class MessagePostedListener implements SlackMessagePostedListener {
    private final ObjectMapper mapper = new ObjectMapper();
    private final LanguageProcessor languageProcessor;

    @Inject
    MessagePostedListener(LanguageProcessor languageProcessor) {
        this.languageProcessor = languageProcessor;
        this.languageProcessor.registerPattern(loadLanguagePattern("GreetingPattern.json"));
        this.languageProcessor.registerPattern(loadLanguagePattern("ListGamesPattern.json"));
        this.languageProcessor.registerPattern(loadLanguagePattern("StartGamesPattern.json"));
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        if (!event.getSender().isBot()) {
            session.sendMessage(event.getChannel(), languageProcessor.responseTo(event.getMessageContent()));
        }
    }

    private LanguagePattern loadLanguagePattern(String filename) {
        try {
            return mapper.readValue(getClass().getClassLoader().getResourceAsStream(filename), LanguagePattern.class);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
