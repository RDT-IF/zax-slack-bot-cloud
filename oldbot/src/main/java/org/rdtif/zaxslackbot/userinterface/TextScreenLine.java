package org.rdtif.zaxslackbot.userinterface;

class TextScreenLine {
    private final String text;

    TextScreenLine(String text) {
        this.text = text;
    }

    String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
