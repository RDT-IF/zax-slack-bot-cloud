package org.rdtif.zaxslackbot.userinterface;

class TextScreenLine {
    private String text = "";
    private boolean changed;

    String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
        this.changed = true;
    }

    @Override
    public String toString() {
        return text;
    }

    boolean hasChanged() {
        return changed;
    }
}
