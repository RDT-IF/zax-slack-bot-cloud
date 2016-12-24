package org.rdtif.zaxslackbot.userinterface;

class TextScreenPosition {
    private final int row;
    private final int column;

    TextScreenPosition(int row, int column) {
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
