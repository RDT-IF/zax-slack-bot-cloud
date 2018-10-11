package org.rdtif.zaxslackbot.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LanguagePattern {
    static final String DEFAULT_MESSAGE = "I do not understand what you want.";
    private final Random random = new Random();
    private String pattern = "";
    private LanguageAction action = LanguageAction.Default;
    private List<LanguageResponse> responses = new ArrayList<>();

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public LanguageAction getAction() {
        return action;
    }

    public void setAction(LanguageAction action) {
        this.action = action;
    }

    public List<LanguageResponse> getResponses() {
        return responses;
    }

    void setResponses(List<LanguageResponse> responses) {
        this.responses = responses;
    }

    String responseForFirst() {
        if (responses.size() > 0) {
            return selectResponse(responses.get(0).getResponses());
        }
        return DEFAULT_MESSAGE;
    }

    String responseFor(String name) {
        return responses.stream()
                .filter(response -> name.equalsIgnoreCase(response.getName()))
                .findFirst()
                .map(languageResponse -> selectResponse(languageResponse.getResponses()))
                .orElse(DEFAULT_MESSAGE);

    }

    private String selectResponse(List<String> responses) {
        int size = responses.size();
        if (size > 0) {
            return responses.get(random.nextInt(size));
        } else {
            return DEFAULT_MESSAGE;
        }
    }
}
