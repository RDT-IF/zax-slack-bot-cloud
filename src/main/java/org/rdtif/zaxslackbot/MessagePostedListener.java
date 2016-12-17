package org.rdtif.zaxslackbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

import java.io.IOException;

class MessagePostedListener implements SlackMessagePostedListener{

    private LanguageProcessor languageProcessor = new LanguageProcessor();

    MessagePostedListener() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            languageProcessor.registerPattern(mapper.readValue(getClass().getClassLoader().getResourceAsStream("GreetingPattern.json"), LanguagePattern.class));
            languageProcessor.registerPattern(mapper.readValue(getClass().getClassLoader().getResourceAsStream("ListGamesPattern.json"), LanguagePattern.class));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        if(!event.getSender().isBot()) {
            session.sendMessage(event.getChannel(), languageProcessor.responseTo(event.getMessageContent()));
//            session.sendMessage(event.getChannel(), "I don't understand that yet.");
        }
    }
}
