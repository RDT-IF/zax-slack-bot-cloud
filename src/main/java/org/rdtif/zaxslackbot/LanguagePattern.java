package org.rdtif.zaxslackbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LanguagePattern {
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

    public void setResponses(List<LanguageResponse> responses) {
        this.responses = responses;
    }

    public Optional<LanguageResponse> responseFor(String name) {
        return responses.stream().filter(response -> name.equalsIgnoreCase(response.getName())).findFirst();
    }
}
