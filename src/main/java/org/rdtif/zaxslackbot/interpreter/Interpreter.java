package org.rdtif.zaxslackbot.interpreter;

import com.google.common.eventbus.Subscribe;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import org.rdtif.zaxslackbot.eventbus.AppMentionBusEvent;
import org.rdtif.zaxslackbot.eventbus.EventBusSubscriber;
import org.rdtif.zaxslackbot.slack.SlackClient;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EventBusSubscriber
public class Interpreter {
    private final Map<LanguageAction, Action> actionMap;
    private final SlackClient slackClient;
    private final List<LanguagePattern> languagePatterns = new ArrayList<>();

    @Inject
    public Interpreter(Map<LanguageAction, Action> actionMap, SlackClient slackClient) {
        this.actionMap = actionMap;
        this.slackClient = slackClient;
    }

    @Subscribe
    public void interpret(AppMentionBusEvent event) {
        String text = determineResponse(event.text(), event.channelID());
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(event.channelID())
                .text(text)
                .build();
        slackClient.chatPostMessage(request);
    }

    private String determineResponse(String input, String channelID) {
        for (LanguagePattern pattern : languagePatterns) {
            if (input.toLowerCase().matches(pattern.getPattern().toLowerCase())) {
                Action action = actionMap.get(pattern.getAction());
                if (action == null) {
                    return pattern.responseForFirst();
                } else {
                    return action.execute(channelID, input, pattern);
                }
            }
        }
        return LanguagePattern.DEFAULT_MESSAGE;
    }

    void registerPattern(LanguagePattern languagePattern) {
        if (languagePattern == null || languagePattern.getPattern() == null) {
            return;
        }

        languagePatterns.add(languagePattern);
    }
}
