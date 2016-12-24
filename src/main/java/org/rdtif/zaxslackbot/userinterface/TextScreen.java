package org.rdtif.zaxslackbot.userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract class TextScreen {
    private static final int LINES = 25;
    private static final int COLUMNS = 80;
    private final List<TextScreenLine> screenLines = new ArrayList<>(LINES);
    private TextScreenPosition cursorPosition = new TextScreenPosition(0, 0);

    TextScreen() {
        for (int i = 0; i < LINES; i++) {
            screenLines.add(new TextScreenLine());
        }
    }

    abstract void update();

    void writeString(String string) {
        if (cursorPosition.getColumn() + string.length() > COLUMNS) {
            throw new RuntimeException("Out of bounds");
        }
        TextScreenLine line = screenLines.get(cursorPosition.getRow());
        String left = line.getText().substring(0, cursorPosition.getColumn());
        String right = line.getText().substring(cursorPosition.getColumn() + string.length());
        line.setText(left + string + right);
    }

    void scroll(int lines) {
        if (lines == 0) {
            return;
        }

        for (int i = 0; i < lines; i++) {
            screenLines.get(i).setText(screenLines.get(i + 1).getText());
        }
        screenLines.get(lines).setText("");
    }

    void eraseLine() {
        screenLines.get(cursorPosition.getRow()).setText("");
    }

    String getText() {
        return screenLines.stream().map(TextScreenLine::getText).collect(Collectors.joining("\n"));
    }

    void moveCursorTo(TextScreenPosition position) {
        cursorPosition = position;
    }

    void translateCursorBy(TextScreenExtent extent) {
        cursorPosition = new TextScreenPosition(cursorPosition.getRow() + extent.getRows(), cursorPosition.getColumn() + extent.getColumns());
    }

}
