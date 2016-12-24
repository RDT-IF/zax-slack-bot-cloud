package org.rdtif.zaxslackbot.userinterface;

class TextScreenLine {
    private String timeStamp = "";
    private String text = "";
    private boolean changed;

    void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    String getTimeStamp() {
        return timeStamp;
    }

    String getText() {
        return text;
    }

    void setText(String text) {
        this.text = text;
        this.changed = true;
    }

    @Override
    public String toString() {
        return timeStamp + ": " + text;
    }

    boolean hasChanged() {
        return changed;
    }
}
