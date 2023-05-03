package org.rdtif.zaxslackbot.userinterface;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

abstract class TextScreen {
    private final List<TextScreenLine> screenLines;
    private final Extent size;
    private Position cursorPosition = new Position(0, 0);
    private int upperWindowBottomRowPointer = -1;
    private int version;
    private int currentWindow;

    TextScreen(Extent size) {
        this.size = size;
        screenLines = new ArrayList<>(size.getRows());
        for (int i = 0; i < size.getRows(); i++) {
            screenLines.add(new TextScreenLine(" "));
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
        return position.getRow() < 0 || position.getRow() >= size.getRows() || position.getColumn() < 0 || position.getColumn() >= size.getColumns();
    }

    String getJoinedText() {
        return screenLines.stream().map(TextScreenLine::getText).collect(Collectors.joining("\n", "", "\n"));
    }

    void print(String string) {
        StringTokenizer tokenizer = new StringTokenizer(string, "\b\n\r", true);

        // Now process one token at a time.
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            switch (token) {
                case "\b":
                    if (cursorPosition.getColumn() == 0) {
                        cursorPosition = new Position(cursorPosition.getRow() - 1, size.getColumns() - 1);
                    } else {
                        cursorPosition = new Position(cursorPosition.getRow(), cursorPosition.getColumn() - 1);
                    }
                    StringBuilder builder = new StringBuilder(screenLines.get(cursorPosition.getRow()).getText());
                    builder.setCharAt(cursorPosition.getColumn(), ' ');
                    screenLines.set(cursorPosition.getRow(), new TextScreenLine(builder.toString()));
                    break;
                case "\n":
                    // Move to the beginning of the next line
                    goToStartOfNextLine();
                    break;
                case "\r":
                    // Move to the beginning of the current line
                    cursorPosition = new Position(cursorPosition.getRow(), 0);
                    break;
                default:
                    printSubstring(token);
            }
        }
    }

    private void printSubstring(String token) {
        while (!token.isEmpty()) {
            int charactersRemainingInCurrentRow = size.getColumns() - cursorPosition.getColumn();
            int nextLength = charactersRemainingInCurrentRow < token.length() ? charactersRemainingInCurrentRow : token.length();
            if (token.length() > charactersRemainingInCurrentRow) {
                while (!isValidWrapCharacter(token.charAt(nextLength - 1))) {
                    nextLength--;
                }
            }
            TextScreenLine line = screenLines.get(cursorPosition.getRow());
            String currentText = line.getText();

            if (cursorPosition.getColumn() > currentText.length()) {
                currentText = StringUtils.rightPad(currentText, cursorPosition.getColumn());
            }

            String left = currentText.substring(0, cursorPosition.getColumn());
            String right = "";

            if (cursorPosition.getColumn() + nextLength < currentText.length()) {
                right = currentText.substring(cursorPosition.getColumn() + nextLength);
            }
            screenLines.set(cursorPosition.getRow(), new TextScreenLine(left + token.substring(0, nextLength) + right));
            token = token.substring(nextLength);
            update();
            if (!token.isEmpty()) {
                goToStartOfNextLine();
            }
        }
    }

    private boolean isValidWrapCharacter(Character character) {
        return Character.isWhitespace(character)
                || (character == '.')
                || (character == '!')
                || (character == '?')
                || (character == ':')
                || (character == ';')
                || (character == ',')
                ;
    }

    private void goToStartOfNextLine() {
        if (cursorPosition.getRow() + 1 == size.getRows()) {
            scrollUp(1);
            cursorPosition = new Position(cursorPosition.getRow(), 0);
        } else {
            cursorPosition = new Position(cursorPosition.getRow() + 1, 0);
        }
    }

    void eraseLine() {
        screenLines.set(cursorPosition.getRow(), new TextScreenLine(" "));
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
            screenLines.set(size.getRows() - 1, new TextScreenLine(" "));
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
                default:
                    return size;
            }
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

    void setTextStyle(int style) {

    }

    void setCurrentWindow(int currentWindow) {
        this.currentWindow = currentWindow;
    }

    int getCurrentWindow() {
        return currentWindow;
    }

    void setCursorPosition(int x, int y) {
        cursorPosition = new Position(y, x);
    }
}
