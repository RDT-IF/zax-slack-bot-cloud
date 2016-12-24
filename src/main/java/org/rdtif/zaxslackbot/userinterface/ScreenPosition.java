package org.rdtif.zaxslackbot.userinterface;

class ScreenPosition {
    private final int row;
    private final int column;

    ScreenPosition(int row, int column) {
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
