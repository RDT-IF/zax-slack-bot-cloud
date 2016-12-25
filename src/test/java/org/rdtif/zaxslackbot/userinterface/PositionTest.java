package org.rdtif.zaxslackbot.userinterface;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Random;

import org.junit.Test;

public class PositionTest {
    @Test
    public void constructorValuesAreUsed() {
        int row = new Random().nextInt();
        int column = new Random().nextInt();
        Position position = new Position(row, column);

        assertThat(position.getRow(), equalTo(row));
        assertThat(position.getColumn(), equalTo(column));
    }

    @Test
    public void translateByNeverReturnsNull() {
        Position position = new Position(0, 0).translateBy(new Extent(0, 0));

        assertThat(position, notNullValue());
    }

    @Test
    public void translateByReturnsCorrectPosition() {
        int rows = new Random().nextInt();
        int columns = new Random().nextInt();
        Position position = new Position(0, 0).translateBy(new Extent(rows, columns));

        assertThat(position.getRow(), equalTo(rows));
        assertThat(position.getColumn(), equalTo(columns));
    }

    @Test
    public void translateByReturnsCorrectPositionAccountingForCurrentPosition() {
        int rows = new Random().nextInt();
        int columns = new Random().nextInt();
        int row = new Random().nextInt();
        int column = new Random().nextInt();
        Position position = new Position(row, column).translateBy(new Extent(rows, columns));

        assertThat(position.getRow(), equalTo(row + rows));
        assertThat(position.getColumn(), equalTo(column + columns));
    }

    @Test
    public void toStringReturn() {
        Position position = randomPosition();
        String expected = "(" + position.getRow() + ", " + position.getColumn() + ")";

        String string = position.toString();

        assertThat(string, equalTo(expected));
    }

    @Test
    public void equalsForSelfReturnsTrue() {
        Position position = randomPosition();

        //noinspection EqualsWithItself
        assertThat(position.equals(position), equalTo(true));
    }

    @Test
    public void equalsForSameRowColumnReturnsTrue() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn());

        assertThat(position1.equals(position2), equalTo(true));
    }

    @Test
    public void equalsIsSymmetric() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn());

        assertThat(position1.equals(position2), equalTo(true));
        assertThat(position2.equals(position1), equalTo(true));
    }

    @Test
    public void equalsForSameRowDifferentColumnReturnsFalse() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn() + 1);

        assertThat(position1.equals(position2), equalTo(false));
    }

    @Test
    public void equalsForDifferentRowSameColumnReturnsFalse() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow() + 1, position1.getColumn());

        assertThat(position1.equals(position2), equalTo(false));
    }

    @Test
    public void equalsForDifferentRowDifferentColumnReturnsFalse() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow() + 1, position1.getColumn() + 1);

        assertThat(position1.equals(position2), equalTo(false));
    }

    @Test
    public void equalsForNullReturnsFalse() {
        Position position = randomPosition();

        //noinspection ObjectEqualsNull
        assertThat(position.equals(null), equalTo(false));
    }

    @Test
    public void equalsForOtherObjectReturnsFalse() {
        Position position = randomPosition();

        //noinspection EqualsBetweenInconvertibleTypes
        assertThat(position.equals(new RuntimeException()), equalTo(false));
    }

    @Test
    public void hashCodesAreReflexive() {
        Position position = randomPosition();

        //noinspection EqualsWithItself
        assertThat(position.hashCode(), equalTo(position.hashCode()));
    }

    @Test
    public void hashCodesForSameRowColumnAreEqual() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn());

        assertThat(position1.hashCode(), equalTo(position2.hashCode()));
    }

    @Test
    public void hashCodesForSameRowDifferentColumnAreDifferent() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn() + 1);

        assertThat(position1.hashCode(), not(equalTo(position2.hashCode())));
    }

    @Test
    public void hashCodesForDifferentRowSameColumnAreDifferent() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow() + 1, position1.getColumn());

        assertThat(position1.hashCode(), not(equalTo(position2.hashCode())));

    }

    @Test
    public void hashCodesForDifferentRowDifferentColumnAreDifferent() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow() + 1, position1.getColumn() + 1);

        assertThat(position1.hashCode(), not(equalTo(position2.hashCode())));
    }

    private Position randomPosition() {
        return new Position(new Random().nextInt(), new Random().nextInt());
    }

}
