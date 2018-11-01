package org.rdtif.zaxslackbot.userinterface;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract class TextScreen {
    private final List<TextScreenLine> screenLines;
    private final Extent size;
    private Position cursorPosition = new Position(0, 0);
    private int upperWindowBottomRowPointer = -1;
    private int version;

    TextScreen(Extent size) {
        this.size = size;
        screenLines = new ArrayList<>(size.getRows());
        for (int i = 0; i < size.getRows(); i++) {
            screenLines.add(new TextScreenLine(""));
        }
    }

    void setVersion(int version) {
        this.version = version;
    }

    abstract void update();

    Extent getSize() {
        return size;
    }

    Position getCursorPosition() {
        return cursorPosition;
    }

    void moveCursorBy(Extent extent) {
        moveCursorTo(cursorPosition.translateBy(extent));
    }

    void moveCursorTo(Position position) {
        if (isValid(position)) {
            throw new IndexOutOfBoundsException("Attempt to move cursor to invalid position " + position);
        }
        cursorPosition = position;
    }

    private boolean isValid(Position position) {
        return position.getRow() < 0 || position.getRow() >= size.getRows()
                || position.getColumn() < 0 || position.getColumn() >= size.getColumns();
    }

    String getJoinedText() {
        return screenLines.stream().map(TextScreenLine::getText).collect(Collectors.joining("\n", "", "\n"));
    }

    void print(String string) {
        if (cursorPosition.getColumn() + string.length() > size.getColumns()) {
            throw new IndexOutOfBoundsException("Attempt to print a string beyond the edge of screen");
        }

        TextScreenLine line = screenLines.get(cursorPosition.getRow());
        String text = line.getText();
        if (cursorPosition.getColumn() > text.length()) {
            text = StringUtils.rightPad(text, cursorPosition.getColumn());
        }

        String left = text.substring(0, cursorPosition.getColumn());
        String right = "";
        if (cursorPosition.getColumn() + string.length() < text.length()) {
            right = text.substring(cursorPosition.getColumn() + string.length());
        }
        screenLines.set(cursorPosition.getRow(), new TextScreenLine(left + string + right));
    }

    void eraseLine() {
        screenLines.set(cursorPosition.getRow(), new TextScreenLine(""));
    }

    void scroll(int lines) {
        if (lines >= 0) {
            scrollUp(lines);
        } else {
            scrollDown(0 - lines);
        }
    }

    private void scrollUp(int lines) {
        for (int j = 0; j < lines; j++) {
            for (int i = 0; i < size.getRows() - 1; i++) {
                screenLines.set(i, new TextScreenLine(screenLines.get(i + 1).getText()));
            }
            screenLines.set(size.getRows() - 1, new TextScreenLine(""));
            update();
        }
    }

    private void scrollDown(int lines) {
        for (int j = 0; j < lines; j++) {
            for (int i = size.getRows() - 1; i > 0; i--) {
                screenLines.set(i, new TextScreenLine(screenLines.get(i - 1).getText()));
            }
            screenLines.set(0, new TextScreenLine(""));
            update();
        }
    }

    boolean hasUpperWindow() {
        return version >= 3;
    }

    Extent getWindowSize(int window) {
        if (upperWindowBottomRowPointer == -1) {
            switch (window) {
                case 0:
                    return new Extent(0, 0);
                case 1:
                    return size;
            }
            return size;
        }

        switch (window) {
            case 0:
                return new Extent(upperWindowBottomRowPointer + 1, size.getColumns());
            case 1:
                return new Extent(size.getRows() - upperWindowBottomRowPointer - 1, size.getColumns());
        }
        return new Extent(0, 0);
    }

    void splitScreen(int upperWindowLines) {
        this.upperWindowBottomRowPointer = upperWindowLines - 1;
    }

    void eraseWindow(int window) {
        for (int i = 0; i < screenLines.size(); i++) {
            screenLines.set(i, new TextScreenLine(""));
        }
    }

    int getVersion() {
        return version;
    }
}
