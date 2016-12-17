package org.rdtif.zaxslackbot;

import java.util.List;

public class LanguagePattern {
    private String pattern;
    private LanguageAction action;
    private List<LanguageResponse> responses;

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
}
