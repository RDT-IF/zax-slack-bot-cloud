package org.rdtif.zaxslackbot.userinterface;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextScreenTest {
    private static final Extent TINY_EXTENT = new Extent(1, 1);

    @Test
    void getCursorPositionNeverReturnsNull() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);

        Position position = textScreen.getCursorPosition();

        assertThat(position, notNullValue());
    }

    @Test
    void cursorPositionDefaultsToZeroZero() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);

        Position position = textScreen.getCursorPosition();

        assertThat(position, equalTo(new Position(0, 0)));
    }

    @Test
    void moveCursorBy() {
        TestTextScreen textScreen = new TestTextScreen(new Extent(50, 50));
        Extent extent = randomExtent();

        textScreen.moveCursorBy(extent);

        assertThat(textScreen.getCursorPosition().getRow(), equalTo(extent.getRows()));
        assertThat(textScreen.getCursorPosition().getColumn(), equalTo(extent.getColumns()));
    }

    @Test
    void moveCursorUpByTooManyRows() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        int rows = 0 - new Random().nextInt(100) + 1;
        Extent extent = new Extent(rows, 0);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> textScreen.moveCursorBy(extent));
        assertThat(exception.getMessage(), equalTo("Attempt to move cursor to invalid position " + new Position(extent.getRows(), extent.getColumns())));
    }

    @Test
    void moveCursorDownByTooManyRows() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        Extent extent = new Extent(new Random().nextInt(100) + 1, 0);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> textScreen.moveCursorBy(extent));
        assertThat(exception.getMessage(), equalTo("Attempt to move cursor to invalid position " + new Position(extent.getRows(), extent.getColumns())));
    }

    @Test
    void moveCursorLeftByTooManyColumns() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        int columns = 0 - new Random().nextInt(100) + 2;
        Extent extent = new Extent(0, columns);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> textScreen.moveCursorBy(extent));
        assertThat(exception.getMessage(), equalTo("Attempt to move cursor to invalid position " + new Position(extent.getRows(), extent.getColumns())));
    }

    @Test
    void moveCursorRightByTooManyColumns() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        Extent extent = new Extent(0, new Random().nextInt(100) + 1);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> textScreen.moveCursorBy(extent));
        assertThat(exception.getMessage(), equalTo("Attempt to move cursor to invalid position " + new Position(extent.getRows(), extent.getColumns())));
    }

    @Test
    void moveCursorTo() {
        TestTextScreen textScreen = new TestTextScreen(new Extent(50, 50));
        Position position = randomPosition();

        textScreen.moveCursorTo(position);

        assertThat(textScreen.getCursorPosition().getRow(), equalTo(position.getRow()));
        assertThat(textScreen.getCursorPosition().getColumn(), equalTo(position.getColumn()));
    }

    @Test
    void moveCursorToRowTooLow() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        int row = 0 - new Random().nextInt(100) + 2;
        Position position = new Position(row, 0);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> textScreen.moveCursorTo(position));
        assertThat(exception.getMessage(), equalTo("Attempt to move cursor to invalid position " + position));
    }

    @Test
    void moveCursorToRowTooHigh() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        Position position = new Position(new Random().nextInt(100) + 1, 0);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> textScreen.moveCursorTo(position));
        assertThat(exception.getMessage(), equalTo("Attempt to move cursor to invalid position " + position));
    }

    @Test
    void moveCursorToColumnTooLow() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        int column = 0 - new Random().nextInt(100) + 1;
        Position position = new Position(0, column);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> textScreen.moveCursorTo(position));
        assertThat(exception.getMessage(), equalTo("Attempt to move cursor to invalid position " + position));
    }

    @Test
    void moveCursorToColumnTooHigh() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        Position position = new Position(0, new Random().nextInt(100) + 1);

        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> textScreen.moveCursorTo(position));
        assertThat(exception.getMessage(), equalTo("Attempt to move cursor to invalid position " + position));
    }

    @Test
    void getJoinedTextNeverReturnNull() {
        String text = new TestTextScreen(new Extent(1, 1)).getJoinedText();
        assertThat(text, notNullValue());
    }

    @Test
    void getJoinedTextContainsPrintedLine() {
        String line = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 25));
        textScreen.print(line);

        String text = textScreen.getJoinedText();

        assertThat(text, equalTo(line + "\n"));
    }

    @Test
    void getJoinedTextContainsAllPrintedLines() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(3, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);

        String text = textScreen.getJoinedText();

        String expected = line1 + "\n" + line2 + "\n" + line3 + "\n";
        assertThat(text, equalTo(expected));
    }

    @Test
    void getJoinedTextSeparatesLinesWithNewline() {
        String text = new TestTextScreen(new Extent(3, 1)).getJoinedText();
        assertThat(text, equalTo("\n\n\n"));
    }

    @Test
    void printStringInMiddleOfLine() {
        String original = RandomStringUtils.randomAlphanumeric(100);
        String newPortion = "~" + RandomStringUtils.randomAlphanumeric(25) + "~";
        Position position = randomRowZeroPosition();
        String expected = original.replaceFirst(original.substring(position.getColumn(), position.getColumn() + 27), newPortion) + "\n";

        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 100));
        textScreen.print(original);
        textScreen.moveCursorTo(position);

        textScreen.print(newPortion);

        assertThat(textScreen.getJoinedText(), equalTo(expected));
    }

    @Test
    void printStringInMiddleOfLineNewPortionLonger() {
        String original = RandomStringUtils.randomAlphanumeric(15);
        String newPortion = "~" + RandomStringUtils.randomAlphanumeric(25) + "~";
        Position position = new Position(0, 5);
        String expected = original.substring(0, 5) + newPortion + "\n";

        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 100));
        textScreen.print(original);
        textScreen.moveCursorTo(position);

        textScreen.print(newPortion);

        assertThat(textScreen.getJoinedText(), equalTo(expected));
    }

    @Test
    void printStringPositionedBeyondCurrentLengthOfOutput() {
        String original = RandomStringUtils.randomAlphanumeric(1);
        String newPortion = "~" + RandomStringUtils.randomAlphanumeric(25) + "~";
        Position position = new Position(0, 5);
        String expected = original + "    " + newPortion + "\n";

        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 100));
        textScreen.print(original);
        textScreen.moveCursorTo(position);

        textScreen.print(newPortion);

        assertThat(textScreen.getJoinedText(), equalTo(expected));
    }

    @Test
    void preventPrintFromExceedingColumnWidth() {
        String line = RandomStringUtils.randomAlphanumeric(50);
        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 25));


        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> textScreen.print(line));
        assertThat(exception.getMessage(), equalTo("Attempt to print a string beyond the edge of screen"));
    }

    @Test
    void eraseLine() {
        String line = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 25));
        textScreen.print(line);

        textScreen.eraseLine();

        String text = textScreen.getJoinedText();
        assertThat(text, equalTo("\n"));
    }

    @Test
    void eraseLineUsesCursorPosition() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(3, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);
        textScreen.moveCursorBy(new Extent(-1, 0));
        textScreen.eraseLine();

        String text = textScreen.getJoinedText();

        String expected = line1 + "\n\n" + line3 + "\n";
        assertThat(text, equalTo(expected));
    }

    @Test
    void scrollOne() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(3, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);

        textScreen.scroll(1);

        assertThat(textScreen.getJoinedText(), equalTo(line2 + "\n" + line3 + "\n\n"));
    }

    @Test
    void scrollMultiple() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(10, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);

        textScreen.scroll(3);

        assertThat(textScreen.getJoinedText(), equalTo("\n\n\n\n\n\n\n\n\n\n"));
    }

    @Test
    void scrollCallsUpdate() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(10, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);
        int expected = new Random().nextInt(100);

        textScreen.scroll(expected);

        assertThat(textScreen.count, equalTo(expected));
    }

    @Test
    void scrollMultipleIncludingBottom() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(3, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);

        textScreen.scroll(3);

        assertThat(textScreen.getJoinedText(), equalTo("\n\n\n"));
    }

    @Test
    void scrollMultipleBeyondBottom() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(2, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);

        textScreen.scroll(3);

        assertThat(textScreen.getJoinedText(), equalTo("\n\n"));
    }

    @Test
    void scrollMultipleSingleRowScreen() {
        String line = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 25));
        textScreen.print(line);

        textScreen.scroll(3);

        assertThat(textScreen.getJoinedText(), equalTo("\n"));
    }

    @Test
    void scrollZero() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(3, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);

        textScreen.scroll(0);

        assertThat(textScreen.getJoinedText(), equalTo(line1 + "\n" + line2 + "\n" + line3 + "\n"));
    }

    @Test
    void scrollNegativeOne() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(3, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);

        textScreen.scroll(-1);

        assertThat(textScreen.getJoinedText(), equalTo("\n" + line1 + "\n" + line2 + "\n"));
    }

    @Test
    void scrollNegativeMultiple() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(3, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);

        textScreen.scroll(-3);

        assertThat(textScreen.getJoinedText(), equalTo("\n\n\n"));
    }

    @Test
    void scrollNegativeMultipleIncludingTop() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(3, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);

        textScreen.scroll(-3);

        assertThat(textScreen.getJoinedText(), equalTo("\n\n\n"));
    }

    @Test
    void scrollNegativeCallsUpdate() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        String line3 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(3, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line3);
        int expected = new Random().nextInt(100);

        textScreen.scroll(0 - expected);

        assertThat(textScreen.count, equalTo(expected));
    }

    @Test
    void scrollNegativeMultipleBeyondTop() {
        String line1 = RandomStringUtils.randomAlphanumeric(25);
        String line2 = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(2, 25));
        textScreen.print(line1);
        textScreen.moveCursorBy(new Extent(1, 0));
        textScreen.print(line2);

        textScreen.scroll(-3);

        assertThat(textScreen.getJoinedText(), equalTo("\n\n"));
    }

    @Test
    void scrollNegativeMultipleSingleRowScreen() {
        String line = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 25));
        textScreen.print(line);

        textScreen.scroll(-3);

        assertThat(textScreen.getJoinedText(), equalTo("\n"));
    }

    private Position randomRowZeroPosition() {
        return new Position(0, new Random().nextInt(50));
    }

    private Position randomPosition() {
        return new Position(new Random().nextInt(50), new Random().nextInt(50));
    }

    private Extent randomExtent() {
        return new Extent(new Random().nextInt(40), new Random().nextInt(40));
    }

    private static class TestTextScreen extends TextScreen {
        private int count;

        TestTextScreen(Extent extent) {
            super(extent);
        }

        @Override
        void update() {
            this.count++;
        }
    }
}
