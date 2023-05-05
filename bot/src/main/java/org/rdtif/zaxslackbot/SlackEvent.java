package org.rdtif.zaxslackbot;

class SlackEvent {
    SlackEvent(String challenge, String type) {
        this.challenge = challenge;
        this.type = type;
    }

    String getChallenge() {
        return challenge;
    }

    String getType() {
        return type;
    }

    private final String challenge;
    private final String type;
}
