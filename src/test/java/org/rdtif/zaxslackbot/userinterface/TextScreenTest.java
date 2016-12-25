package org.rdtif.zaxslackbot.userinterface;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TextScreenTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private static final Extent TINY_EXTENT = new Extent(1, 1);

    @Test
    public  void getCursorPositionNeverReturnsNull() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);

        Position position = textScreen.getCursorPosition();

        assertThat(position, notNullValue());
    }

    @Test
    public  void cursorPositionDefaultsToZeroZero() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);

        Position position = textScreen.getCursorPosition();

        assertThat(position, equalTo(new Position(0, 0)));
    }

    @Test
    public void moveCursorBy() {
        TestTextScreen textScreen = new TestTextScreen(new Extent(50, 50));
        Extent extent = randomExtent(40, 40);

        textScreen.moveCursorBy(extent);

        assertThat(textScreen.getCursorPosition().getRow(), equalTo(extent.getRows()));
        assertThat(textScreen.getCursorPosition().getColumn(), equalTo(extent.getColumns()));
    }

    @Test
    public void moveCursorUpByTooManyRows() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        int rows = 0 - new Random().nextInt(100) + 1;
        Extent extent = new Extent(rows, 0);

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Attempt to move cursor to invalid position " + new Position(extent.getRows(), extent.getColumns()));

        textScreen.moveCursorBy(extent);
    }

    @Test
    public void moveCursorDownByTooManyRows() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        Extent extent = new Extent(new Random().nextInt(100) + 1, 0);

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Attempt to move cursor to invalid position " + new Position(extent.getRows(), extent.getColumns()));

        textScreen.moveCursorBy(extent);
    }

    @Test
    public void moveCursorLeftByTooManyColumns() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        int columns = 0 - new Random().nextInt(100) + 1;
        Extent extent = new Extent(0, columns);

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Attempt to move cursor to invalid position " + new Position(extent.getRows(), extent.getColumns()));

        textScreen.moveCursorBy(extent);
    }

    @Test
    public void moveCursorRightByTooManyColumns() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        Extent extent = new Extent(0, new Random().nextInt(100) + 1);

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Attempt to move cursor to invalid position " + new Position(extent.getRows(), extent.getColumns()));

        textScreen.moveCursorBy(extent);
    }

    @Test
    public void moveCursorTo() {
        TestTextScreen textScreen = new TestTextScreen(new Extent(50, 50));
        Position position = randomPosition(50, 50);

        textScreen.moveCursorTo(position);

        assertThat(textScreen.getCursorPosition().getRow(), equalTo(position.getRow()));
        assertThat(textScreen.getCursorPosition().getColumn(), equalTo(position.getColumn()));
    }

    @Test
    public void moveCursorToRowTooLow() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        int row = 0 - new Random().nextInt(100) + 1;
        Position position = new Position(row, 0);

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Attempt to move cursor to invalid position " + position);

        textScreen.moveCursorTo(position);
    }

    @Test
    public void moveCursorToRowTooHigh() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        Position position = new Position(new Random().nextInt(100) + 1, 0);

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Attempt to move cursor to invalid position " + position);

        textScreen.moveCursorTo(position);
    }

    @Test
    public void moveCursorToColumnTooLow() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        int column = 0 - new Random().nextInt(100) + 1;
        Position position = new Position(0, column);

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Attempt to move cursor to invalid position " + position);

        textScreen.moveCursorTo(position);
    }

    @Test
    public void moveCursorToColumnTooHigh() {
        TestTextScreen textScreen = new TestTextScreen(TINY_EXTENT);
        Position position = new Position(0, new Random().nextInt(100) + 1);

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Attempt to move cursor to invalid position " + position);

        textScreen.moveCursorTo(position);
    }

    @Test
    public void getJoinedTextNeverReturnNull() {
        String text = new TestTextScreen(new Extent(1,1)).getJoinedText();
        assertThat(text, notNullValue());
    }

    @Test
    public void getJoinedTextContainsPrintedLine() {
        String line = RandomStringUtils.randomAlphanumeric(25);
        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 25));
        textScreen.print(line);

        String text = textScreen.getJoinedText();

        assertThat(text, equalTo(line + "\n"));
    }

    @Test
    public void getJoinedTextContainsAllPrintedLines() {
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
    public void getJoinedTextSeparatesLinesWithNewline() {
        String text = new TestTextScreen(new Extent(3, 1)).getJoinedText();
        assertThat(text, equalTo("\n\n\n"));
    }

    @Test
    public void printStringInMiddleOfLine() {
        String original = RandomStringUtils.randomAlphanumeric(100);
        String newPortion = "~" + RandomStringUtils.randomAlphanumeric(25) + "~";
        Position position = randomPosition(50);
        String expected = original.replaceFirst(original.substring(position.getColumn(), position.getColumn() + 27), newPortion) + "\n";

        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 100));
        textScreen.print(original);
        textScreen.moveCursorTo(position);

        textScreen.print(newPortion);

        assertThat(textScreen.getJoinedText(), equalTo(expected));
    }

    @Test
    public void printStringInMiddleOfLineNewPortionLonger() {
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
    public void printStringPositionedBeyondCurrentLengthOfOutput() {
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
    public void preventPrintFromExceedingColumnWidth() {
        String line = RandomStringUtils.randomAlphanumeric(50);
        TestTextScreen textScreen = new TestTextScreen(new Extent(1, 25));

        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Attempt to print a string beyond the edge of screen");

        textScreen.print(line);
    }

    private Position randomPosition(int columnLimit) {
        return new Position(0, new Random().nextInt(columnLimit));
    }
    private Position randomPosition(int rowLimit, int columnLimit) {
        return new Position(new Random().nextInt(rowLimit), new Random().nextInt(columnLimit));
    }

    private Extent randomExtent(int rowLimit, int columnLimit) {
        return new Extent(new Random().nextInt(rowLimit), new Random().nextInt(columnLimit));
    }

    private static class TestTextScreen extends TextScreen {
        TestTextScreen(Extent extent) {
            super(extent);
        }

        @Override
        void update() {}
    }
}
