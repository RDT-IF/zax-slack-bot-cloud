package org.rdtif.zaxslackbot.interpreter;

import java.util.List;

class LanguageResponse {
    private String name;
    private List<String> responses;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    List<String> getResponses() {
        return responses;
    }

    void setResponses(List<String> responses) {
        this.responses = responses;
    }
}
