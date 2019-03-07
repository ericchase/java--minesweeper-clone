package minesweeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperTest {
  public Minesweeper game;

  @Test
  public void canary() {
    assertTrue(true);
  }

  @Test
  public void reverseCanary() {
    assertThrows(AssertionFailedError.class, () -> assertTrue(false));
  }

  @BeforeEach
  public void beforeEach() {
    game = new Minesweeper();
  }

  @Test
  public void exposeFirstCell() {
    game.exposeAt(0, 0);

    assertEquals(CellState.EXPOSED, game.getCellStateAt(0, 0));
  }

  @Test
  public void exposeSecondCell() {
    game.exposeAt(0, 0);
    game.exposeAt(0, 1);

    assertEquals(CellState.EXPOSED, game.getCellStateAt(0, 1));
  }

  @Test
  public void exposeACellTwice() {
    game.exposeAt(1, 3);
    game.exposeAt(1, 3);

    assertEquals(CellState.EXPOSED, game.getCellStateAt(1, 3));
  }

  @Test
  public void exposeCellAtTheMaxRowSize() {
    game.exposeAt(game.SIZE, 3);
    assertTrue(true);
  }

  @Test
  public void exposeCellAtTheMaxColumnSize() {
    game.exposeAt(2, game.SIZE);
    assertTrue(true);
  }

  @Test
  public void exposeCellOutOfRowRange() {
    game.exposeAt(-1, 4);
    assertTrue(true);
  }

  @Test
  public void exposeCellOutOfColumnRange() {
    game.exposeAt(3, -1);
    assertTrue(true);
  }

  public class MinesweeperWithExposeNeighborsReplaced extends Minesweeper {
    int rowGiven = -99;
    int columnGiven = -99;

    public void exposeNeighborsFor(int row, int column) {
      rowGiven = row;
      columnGiven = column;
    }
  }

  @Test
  public void exposeAtCallsExposeNeighbor() {
    MinesweeperWithExposeNeighborsReplaced game = new MinesweeperWithExposeNeighborsReplaced();

    game.exposeAt(2, 4);

    assertEquals(2, game.rowGiven);
    assertEquals(4, game.columnGiven);
  }

  @Test
  public void exposeNeighborIfCellAlreadyExposed() {
    MinesweeperWithExposeNeighborsReplaced game = new MinesweeperWithExposeNeighborsReplaced();

    game.exposeAt(2, 4);
    game.rowGiven = -1;
    game.columnGiven = -1;
    game.exposeAt(2, 4);

    assertEquals(-1, game.rowGiven);
    assertEquals(-1, game.columnGiven);
  }

  public class MinesweeperWithExposeAtReplaced extends Minesweeper {
    List<Integer> rowsAndColumns = new ArrayList<>();

    public void exposeAt(int row, int column) {
      rowsAndColumns.add(row);
      rowsAndColumns.add(column);
    }
  }

  @Test
  public void exposeNeighborsCallsExposeAt() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.exposeNeighborsFor(1, 2);

    assertEquals(Arrays.asList(0, 1, 0, 2, 0, 3, 1, 1, 1, 3, 2, 1, 2, 2, 2, 3), game.rowsAndColumns);
  }

  @Test
  public void exposeNeighborTopLeft() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.exposeNeighborsFor(0, 0);

    assertEquals(Arrays.asList(-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1), game.rowsAndColumns);
  }

  @Test
  public void exposeNeighborTopRight() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.exposeNeighborsFor(game.SIZE - 1, game.SIZE - 1);

    assertEquals(Arrays.asList(8, 8, 8, 9, 8, 10, 9, 8, 9, 10, 10, 8, 10, 9, 10, 10), game.rowsAndColumns);
  }

  @Test
  public void exposeNeighborBottomLeft() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.exposeNeighborsFor(0, 0);

    assertEquals(Arrays.asList(-1, -1, -1, 0, -1, 1, 0, -1, 0, 1, 1, -1, 1, 0, 1, 1), game.rowsAndColumns);
  }

  @Test
  public void exposeNeighborBottomRight() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.exposeNeighborsFor(game.SIZE - 1, 0);

    assertEquals(Arrays.asList(8, -1, 8, 0, 8, 1, 9, -1, 9, 1, 10, -1, 10, 0, 10, 1), game.rowsAndColumns);
  }

  @Test
  public void exposeNeighborTopEdge() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.exposeNeighborsFor(5, game.SIZE - 1);

    assertEquals(Arrays.asList(4, 8, 4, 9, 4, 10, 5, 8, 5, 10, 6, 8, 6, 9, 6, 10), game.rowsAndColumns);
  }

  @Test
  public void exposeNeighborBottomEdge() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.exposeNeighborsFor(4, 0);

    assertEquals(Arrays.asList(3, -1, 3, 0, 3, 1, 4, -1, 4, 1, 5, -1, 5, 0, 5, 1), game.rowsAndColumns);
  }

  @Test
  public void exposeNeighborLeftEdge() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.exposeNeighborsFor(0, 3);

    assertEquals(Arrays.asList(-1, 2, -1, 3, -1, 4, 0, 2, 0, 4, 1, 2, 1, 3, 1, 4), game.rowsAndColumns);
  }

  @Test
  public void exposeNeighborRightEdge() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.exposeNeighborsFor(game.SIZE - 1, 3);

    assertEquals(Arrays.asList(8, 2, 8, 3, 8, 4, 9, 2, 9, 4, 10, 2, 10, 3, 10, 4), game.rowsAndColumns);
  }

  @Test
  public void sealCell() {
    game.toggleSealAt(5, 6);

    assertEquals(CellState.SEALED, game.getCellStateAt(5, 6));
  }

  @Test
  public void unsealCell() {
    game.toggleSealAt(2, 3);
    game.toggleSealAt(2, 3);

    assertEquals(CellState.INITIAL, game.getCellStateAt(2, 3));
  }

  @Test
  public void sealCellOutOfRowRange() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      game.toggleSealAt(game.SIZE + 4, 5);
    });
  }

  @Test
  public void sealCellOutOfColumnRange() {
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
      game.toggleSealAt(2, game.SIZE + 2);
    });
  }

  @Test
  public void sealingAnExposedCell() {
    game.exposeAt(2, 3);
    game.toggleSealAt(2, 3);

    assertEquals(CellState.EXPOSED, game.getCellStateAt(2, 3));
  }

  @Test
  public void exposingASealedCell() {
    game.toggleSealAt(1, 3);
    game.exposeAt(1, 3);

    assertEquals(CellState.SEALED, game.getCellStateAt(1, 3));
  }

  @Test
  public void exposingASealedCellDoesNotExposeNeighbors() {
    MinesweeperWithExposeNeighborsReplaced game = new MinesweeperWithExposeNeighborsReplaced();

    game.rowGiven = -1;
    game.columnGiven = -1;
    game.toggleSealAt(2, 3);
    game.exposeAt(2, 3);

    assertEquals(-1, game.rowGiven);
    assertEquals(-1, game.columnGiven);
  }

  @Test
  public void setAndCheckMineAtALocation() {
    game.setMineAt(3, 2);

    assertTrue(game.isMineAt(3, 2));
  }

  @Test
  public void setMineAtLocationAndCheckIfNeighborIsAnAdjacentCell() {
    game.setMineAt(4, 2);

    assertTrue(game.isAdjacentCellAt(4, 3));
  }

  @Test
  public void a_mined_cell_is_not_an_adjacent_cell() {
    game.setMineAt(3, 6);

    assertFalse(game.isAdjacentCellAt(3, 6));
  }

  @Test
  public void a_mined_cell_next_to_a_mined_cell_is_not_an_adjacent_cell() {
    game.setMineAt(3, 6);
    game.setMineAt(4, 6);

    assertFalse(game.isAdjacentCellAt(3, 6));
  }

  @Test
  public void exposing_a_mined_cell_does_not_expose_any_neighbors() {
    MinesweeperWithExposeNeighborsReplaced game = new MinesweeperWithExposeNeighborsReplaced();

    game.setMineAt(1, 1);
    game.exposeAt(1, 1);

    assertEquals(-99, game.rowGiven);
    assertEquals(-99, game.columnGiven);
  }

  @Test
  public void exposing_an_adjacent_cell_does_not_expose_any_neighbors() {
    MinesweeperWithExposeNeighborsReplaced game = new MinesweeperWithExposeNeighborsReplaced();
    game.setMineAt(1, 1);
    game.exposeAt(1, 2);

    assertEquals(-99, game.rowGiven);
    assertEquals(-99, game.columnGiven);
  }

  @Test
  public void checkIfGameIsInProgressWhenNoMineIsSealed() {
    game.setMineAt(0, 0);

    assertTrue(game.isInProgress());
  }

  @Test
  public void checkIfGameIsNotInProgressWhenAllMinesAreSealedAndNonMinesAreExposed() {
    game.setMineAt(0, 0);
    game.toggleSealAt(0, 0);
    game.exposeAt(2, 2);

    assertFalse(game.isInProgress());
  }

  @Test
  public void checkIfGameIsInProgressWhenAllNonMinesAreExposed() {
    game.setRandomMines(100);

    assertTrue(game.isInProgress());
  }

  @Test
  public void checkIfGameIsInProgressWhenAtLeastOneNonMineIsUnExposedOrSealed() {
    MinesweeperWithExposeAtReplaced game = new MinesweeperWithExposeAtReplaced();
    game.setMineAt(0, 0);
    game.exposeNeighborsFor(2, 3);
    game.toggleSealAt(4, 5);

    assertTrue(game.isInProgress());
  }

  @Test
  public void checkIfGameIsNotOverWhenIsLostAndIsWonAreFalse() {
    game.setRandomMines(10);

    assertFalse(game.isOver());
  }

  @Test
  public void checkIfGameIsNotOverWhenIsLostIsTrue() {
    game.setMineAt(0, 0);
    game.exposeAt(0, 0);

    assertTrue(game.isOver());
  }

  @Test
  public void checkIfGameIsNotOverWhenIsWonIsTrue() {
    game.setMineAt(0, 0);
    game.toggleSealAt(0, 0);
    game.exposeAt(2, 2);

    assertTrue(game.isOver());
  }

  @Test
  public void checkIfMineIsPlaced() {
    game.setMineAt(2, 3);

    assertTrue(game.isMineAt(2, 3));
  }

  @Test
  public void checkIfMineIsPlacedOverAnotherMine() {
    game.setMineAt(4, 5);
    game.setMineAt(4, 5);

    assertTrue(game.isMineAt(4, 5));
  }

  @Test
  public void checkIfMineIsNotRemovedAfterExposing() {
    game.setMineAt(4, 5);
    game.exposeAt(4, 5);

    assertTrue(game.isMineAt(4, 5));
  }

  @Test
  public void checkIfGameIsLostWhenAMineCellIsExposed() {
    game.setMineAt(2, 3);
    game.exposeAt(2, 3);

    assertTrue(game.isLost());
  }

  @Test
  public void checkIfGameIsNotLostWhenNoMineCellsAreExposed() {
    game.setMineAt(2, 3);

    assertFalse(game.isLost());
  }

  @Test
  public void checkIfGameIsWonIfAllMinesAreSealedAndOtherCellsExposed() {
    game.setMineAt(2, 3);
    game.exposeAt(5, 5);
    game.toggleSealAt(2, 3);

    assertTrue(game.isWon());
  }

  public class MinesweeperWithSetMineAtReplaced extends Minesweeper {
    int numberOfMines = 0;

    @Override
    public void setMineAt(int i, int j) {
      super.setMineAt(i, j);
      ++numberOfMines;
    }
  }

  @Test
  public void testForPlacingTenMinesWhenMinesweeperIsCreated() {
    MinesweeperWithSetMineAtReplaced game = new MinesweeperWithSetMineAtReplaced();
    game.setRandomMines(10);

    assertEquals(10, game.numberOfMines);
  }

  @Test
  public void testForPlacingMaxMinesWhenMinesweeperIsCreated() {
    MinesweeperWithSetMineAtReplaced game = new MinesweeperWithSetMineAtReplaced();
    game.setRandomMines(100);

    assertEquals(100, game.numberOfMines);
  }

  @Test
  public void testForPlacingMoreMinesThanSpaces() {
    assertThrows(IndexOutOfBoundsException.class, () -> game.setRandomMines(101));
  }

  @Test
  public void testThatTheLocationsOfTheMinesAreRandom() {
    game.setRandomMines(10);

    assertNotEquals(Arrays.asList(0, 0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8, 0, 9), game.testIfRandom());
    assertNotEquals(Arrays.asList(0, 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8, 0, 9, 0), game.testIfRandom());
  }

  @Test
  public void testMinesweeperConstructorForCoverageSake() {
    game = new Minesweeper(10, 10);

    assertTrue(true);
  }

  @Test
  public void testCellStateValuesForCoverageSake() {
    assertEquals(CellState.INITIAL, CellState.valueOf("INITIAL"));
  }
}
