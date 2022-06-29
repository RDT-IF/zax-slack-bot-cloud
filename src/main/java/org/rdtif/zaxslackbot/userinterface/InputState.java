package org.rdtif.zaxslackbot.userinterface;

public class InputState {
    public InputMode mode;
    public String currentInput;

    public InputState(InputMode mode, String currentInput) {
        this.mode = mode;
        this.currentInput = currentInput;
    }
}
