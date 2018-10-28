package org.rdtif.zaxslackbot;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.rdtif.zaxslackbot.interpreter.LanguageProcessor;

import javax.inject.Inject;

class MessagePostedListener implements SlackMessagePostedListener {
    private final LanguageProcessor languageProcessor;

    @Inject
    MessagePostedListener(LanguageProcessor languageProcessor) {
        this.languageProcessor = languageProcessor;
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        // So the slack-api library swallows exceptions. Catch them here so that we can actually have insight into
        // when our code throws an exception.
        try {
            if (!event.getSender().isBot()) {
                String messageContent = event.getMessageContent();
                String tag = "<@" + session.sessionPersona().getId() + ">";
                if (event.getChannel().isDirect() || messageContent.contains(tag)) {
                    String message = languageProcessor.responseTo(event.getChannel(), messageContent.replaceAll(tag, "").trim());
                    session.sendMessage(event.getChannel(), message);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
