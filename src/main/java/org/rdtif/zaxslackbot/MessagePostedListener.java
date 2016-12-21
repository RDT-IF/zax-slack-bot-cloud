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
    private final LanguageProcessor languageProcessor;

    @Inject
    MessagePostedListener(LanguageProcessor languageProcessor) {
        this.languageProcessor = languageProcessor;
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.languageProcessor.registerPattern(mapper.readValue(getClass().getClassLoader().getResourceAsStream("GreetingPattern.json"), LanguagePattern.class));
            this.languageProcessor.registerPattern(mapper.readValue(getClass().getClassLoader().getResourceAsStream("ListGamesPattern.json"), LanguagePattern.class));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        if (!event.getSender().isBot()) {
            session.sendMessage(event.getChannel(), languageProcessor.responseTo(event.getMessageContent()));
        }
    }
}
