package org.rdtif.zaxslackbot.userinterface;

import java.util.Objects;

public class Extent {
    private final int rows;
    private final int columns;

    public Extent(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    int getRows() {
        return rows;
    }

    int getColumns() {
        return columns;
    }

    @Override
    public final boolean equals(Object other) {
        if (other instanceof Extent) {
            Extent otherExtent = (Extent) other;
            return rows == otherExtent.rows && columns == otherExtent.columns;
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(rows, columns);
    }
}

