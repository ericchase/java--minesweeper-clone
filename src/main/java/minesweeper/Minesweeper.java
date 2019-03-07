package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;

public class Minesweeper {
  public final int SIZE;

  private CellState[][] state;
  private boolean[][] mines;

  public Minesweeper() {
    this(10);
  }

  public Minesweeper(int sideSize) {
    SIZE = sideSize;
    state = new CellState[SIZE][SIZE];
    mines = new boolean[SIZE][SIZE];

    for (CellState[] row : state) {
      Arrays.fill(row, CellState.INITIAL);
    }
    for (boolean[] row : mines) {
      Arrays.fill(row, false);
    }
  }

  public Minesweeper(int sideSize, int mineCount) {
    this(sideSize);
    setRandomMines(mineCount);
  }

  public void setRandomMines(int minesNeeded) throws IndexOutOfBoundsException {
    if (SIZE * SIZE < minesNeeded)
      throw new IndexOutOfBoundsException();

    Random rng = new Random();
    int count = 0;
    while (count < minesNeeded) {
      int i = rng.nextInt(SIZE);
      int j = rng.nextInt(SIZE);

      if (isMineAt(i, j) == false) {
        setMineAt(i, j);
        ++count;
      }
    }
  }

  public void exposeAt(int i, int j) {
    if ((0 <= i && i < SIZE) == false)
      return;
    if ((0 <= j && j < SIZE) == false)
      return;

    if (state[i][j] == CellState.INITIAL) {
      state[i][j] = CellState.EXPOSED;

      if (isEmptyCellAt(i, j)) {
        exposeNeighborsFor(i, j);
      }
    }
  }

  public void exposeNeighborsFor(int i, int j) {
    rangeClosed(i - 1, i + 1).forEach(r -> {
      rangeClosed(j - 1, j + 1).forEach(c -> {
        if ((r != i || c != j))
          exposeAt(r, c);
      });
    });
  }

  public void toggleSealAt(int i, int j) {
    switch (state[i][j]) {
    case INITIAL:
      state[i][j] = CellState.SEALED;
      break;

    case SEALED:
      state[i][j] = CellState.INITIAL;
      break;

    default:
    }
  }

  public void setMineAt(int i, int j) {
    if (mines[i][j] == false) {
      mines[i][j] = true;
    }
  }

  public CellState getCellStateAt(int i, int j) {
    return state[i][j];
  }

  public boolean isEmptyCellAt(int i, int j) {
    return !isMineAt(i, j) && !isAdjacentCellAt(i, j);
  }

  public boolean isAdjacentCellAt(int i, int j) {
    if (mines[i][j])
      return false;
    return getMineCountAround(i, j) > 0;
  }

  public boolean isMineAt(int i, int j) {
    return mines[i][j];
  }

  public boolean isOver() {
    return isLost() || isWon();
  }

  public boolean isInProgress() {
    return isOver() == false;
  }

  public boolean isLost() {
    boolean aMineIsExposed = false;
    for (int row : range(0, SIZE).toArray()) {
      for (int column : range(0, SIZE).toArray()) {
        if (isMineAt(row, column) && getCellStateAt(row, column) == CellState.EXPOSED) {
          aMineIsExposed = true;
        }
      }
    }
    return aMineIsExposed;
  }

  public boolean isWon() {
    boolean allNonMineCellsExposed = true;
    for (int row : range(0, SIZE).toArray()) {
      for (int column : range(0, SIZE).toArray()) {
        if (isMineAt(row, column) && getCellStateAt(row, column) != CellState.SEALED) {
          allNonMineCellsExposed = false;
        }
        if (!isMineAt(row, column) && getCellStateAt(row, column) != CellState.EXPOSED) {
          allNonMineCellsExposed = false;
        }
      }
    }
    return allNonMineCellsExposed;
  }

  public List<Integer> testIfRandom() {
    List<Integer> mineLocations = new ArrayList<>();
    for (int row : range(0, SIZE).toArray()) {
      for (int column : range(0, SIZE).toArray()) {
        if (mines[row][column]) {
          mineLocations.add(row);
          mineLocations.add(column);
        }
      }
    }
    return mineLocations;
  }

  public int getMineCountAround(int i, int j) {
    int count = 0;
    for (int row : rangeClosed(i - 1, i + 1).toArray()) {
      for (int column : rangeClosed(j - 1, j + 1).toArray()) {
        if (isInBounds(row) && isInBounds(column)) {
          if ((row == i && column == j) == false) {
            if (isMineAt(row, column)) {
              count++;
            }
          }
        }
      }
    }
    return count;
  }

  private boolean isInBounds(int index) {
    return (0 <= index && index < SIZE);
  }
}
