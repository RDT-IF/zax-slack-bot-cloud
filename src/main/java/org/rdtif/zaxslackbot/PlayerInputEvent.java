package org.rdtif.zaxslackbot;

public class PlayerInputEvent {
    private final String playerInput;

    public PlayerInputEvent(String playerInput) {
        this.playerInput = playerInput;
    }

    public String getPlayerInput() {
        return playerInput;
    }
}
