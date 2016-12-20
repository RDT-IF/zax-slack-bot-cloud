package org.rdtif.zaxslackbot;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

class LanguageProcessor {
    private static final Map<LanguageAction, Action> ACTION_MAP = ImmutableMap.<LanguageAction, Action>builder()
            .put(LanguageAction.ListGames, new ListGamesAction())
            .build();
    static final String DEFAULT_MESSAGE = "I do not understand what you want.";

    private final Random random = new Random();
    private List<LanguagePattern> languagePatterns = new ArrayList<>();

    String responseTo(String input) {
        for (LanguagePattern pattern : languagePatterns) {
            if (input.toLowerCase().matches(pattern.getPattern().toLowerCase())) {
                Action action = ACTION_MAP.get(pattern.getAction());
                if (action == null) {
                    return getResponseFromPattern(pattern);
                } else {
                    return action.execute(pattern.getResponses());
                }
            }
        }

        return DEFAULT_MESSAGE;
    }

    void registerPattern(LanguagePattern languagePattern) {
        if (languagePattern == null || languagePattern.getPattern() == null) {
            return;
        }

        languagePatterns.add(languagePattern);
    }

    private String getResponseFromPattern(LanguagePattern languagePattern) {
        if (languagePattern.getResponses() != null && languagePattern.getResponses().size() > 0) {
            LanguageResponse languageResponse = languagePattern.getResponses().get(0);
            int size = languageResponse.getResponses().size();
            return languageResponse.getResponses().get(random.nextInt(size));
        }

        return DEFAULT_MESSAGE;
    }

}
