package org.rdtif.zaxslackbot.interpreter;

import java.util.List;

public class LanguageResponse {
    private String name;
    private List<String> responses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }
}
