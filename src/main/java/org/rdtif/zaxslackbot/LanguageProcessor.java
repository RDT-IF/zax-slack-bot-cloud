package org.rdtif.zaxslackbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.inject.Inject;

class LanguageProcessor {
    private final Map<LanguageAction, Action> actionMap;
    static final String DEFAULT_MESSAGE = "I do not understand what you want.";

    private final Random random = new Random();
    private List<LanguagePattern> languagePatterns = new ArrayList<>();

    @Inject
    LanguageProcessor(Map<LanguageAction, Action> actionMap) {
        this.actionMap = actionMap;
    }

    String responseTo(String input) {
        for (LanguagePattern pattern : languagePatterns) {
            if (input.toLowerCase().matches(pattern.getPattern().toLowerCase())) {
                Action action = actionMap.get(pattern.getAction());
                if (action == null) {
                    return getResponseFromPattern(pattern);
                } else {
                    return action.execute(pattern);
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
