package org.rdtif.zaxslackbot.userinterface;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Random;

class PositionTest {
    @Test
    void constructorValuesAreUsed() {
        int row = new Random().nextInt();
        int column = new Random().nextInt();
        Position position = new Position(row, column);

        assertThat(position.getRow(), equalTo(row));
        assertThat(position.getColumn(), equalTo(column));
    }

    @Test
    void translateByNeverReturnsNull() {
        Position position = new Position(0, 0).translateBy(new Extent(0, 0));

        assertThat(position, notNullValue());
    }

    @Test
    void translateByReturnsCorrectPosition() {
        int rows = new Random().nextInt();
        int columns = new Random().nextInt();
        Position position = new Position(0, 0).translateBy(new Extent(rows, columns));

        assertThat(position.getRow(), equalTo(rows));
        assertThat(position.getColumn(), equalTo(columns));
    }

    @Test
    void translateByReturnsCorrectPositionAccountingForCurrentPosition() {
        int rows = new Random().nextInt();
        int columns = new Random().nextInt();
        int row = new Random().nextInt();
        int column = new Random().nextInt();
        Position position = new Position(row, column).translateBy(new Extent(rows, columns));

        assertThat(position.getRow(), equalTo(row + rows));
        assertThat(position.getColumn(), equalTo(column + columns));
    }

    @Test
    void toStringReturn() {
        Position position = randomPosition();
        String expected = "(" + position.getRow() + ", " + position.getColumn() + ")";

        String string = position.toString();

        assertThat(string, equalTo(expected));
    }

    @Test
    void equalsForSelfReturnsTrue() {
        Position position = randomPosition();

        //noinspection EqualsWithItself
        assertThat(position.equals(position), equalTo(true));
    }

    @Test
    void equalsForSameRowColumnReturnsTrue() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn());

        assertThat(position1.equals(position2), equalTo(true));
    }

    @Test
    void equalsIsSymmetric() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn());

        assertThat(position1.equals(position2), equalTo(true));
        assertThat(position2.equals(position1), equalTo(true));
    }

    @Test
    void equalsForSameRowDifferentColumnReturnsFalse() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn() + 1);

        assertThat(position1.equals(position2), equalTo(false));
    }

    @Test
    void equalsForDifferentRowSameColumnReturnsFalse() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow() + 1, position1.getColumn());

        assertThat(position1.equals(position2), equalTo(false));
    }

    @Test
    void equalsForDifferentRowDifferentColumnReturnsFalse() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow() + 1, position1.getColumn() + 1);

        assertThat(position1.equals(position2), equalTo(false));
    }

    @Test
    void equalsForNullReturnsFalse() {
        Position position = randomPosition();

        //noinspection ObjectEqualsNull
        assertThat(position.equals(null), equalTo(false));
    }

    @Test
    void equalsForOtherObjectReturnsFalse() {
        Position position = randomPosition();

        //noinspection EqualsBetweenInconvertibleTypes
        assertThat(position.equals(new RuntimeException()), equalTo(false));
    }

    @Test
    void hashCodesAreReflexive() {
        Position position = randomPosition();

        //noinspection EqualsWithItself
        assertThat(position.hashCode(), equalTo(position.hashCode()));
    }

    @Test
    void hashCodesForSameRowColumnAreEqual() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn());

        assertThat(position1.hashCode(), equalTo(position2.hashCode()));
    }

    @Test
    void hashCodesForSameRowDifferentColumnAreDifferent() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow(), position1.getColumn() + 1);

        assertThat(position1.hashCode(), not(equalTo(position2.hashCode())));
    }

    @Test
    void hashCodesForDifferentRowSameColumnAreDifferent() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow() + 1, position1.getColumn());

        assertThat(position1.hashCode(), not(equalTo(position2.hashCode())));

    }

    @Test
    void hashCodesForDifferentRowDifferentColumnAreDifferent() {
        Position position1 = randomPosition();
        Position position2 = new Position(position1.getRow() + 1, position1.getColumn() + 1);

        assertThat(position1.hashCode(), not(equalTo(position2.hashCode())));
    }

    private Position randomPosition() {
        return new Position(new Random().nextInt(), new Random().nextInt());
    }

}
