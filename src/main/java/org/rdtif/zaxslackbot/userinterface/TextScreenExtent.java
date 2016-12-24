package org.rdtif.zaxslackbot.userinterface;

class TextScreenExtent {
    private final int rows;
    private final int columns;

    TextScreenExtent(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    int getRows() {
        return rows;
    }

    int getColumns() {
        return columns;
    }
}
