package org.rdtif.zaxslackbot.userinterface;

public class TextScreenPosition {
    private final int row;
    private final int column;

    public TextScreenPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }
}
