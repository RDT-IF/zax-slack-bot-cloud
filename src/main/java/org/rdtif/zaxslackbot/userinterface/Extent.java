package org.rdtif.zaxslackbot.userinterface;

class Extent {
    private final int rows;
    private final int columns;

    Extent(int rows, int columns) {
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
