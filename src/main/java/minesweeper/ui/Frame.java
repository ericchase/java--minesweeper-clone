package minesweeper.ui;

import minesweeper.CellState;
import minesweeper.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class Frame extends JFrame {
  private TileRenderer renderer;

  public class MinesweeperCell extends JButton {
    public int row = 0;
    public int column = 0;

    public MinesweeperCell(int r, int c) {
      row = r;
      column = c;
    }

    @Override
    public void paint(Graphics g) {
      super.paint(g);
      renderer.paint(this, g);
    }
  }

  private final int GRID_SIDE_SIZE = 10;
  private final int CELL_SIDE_SIZE = 50;
  private final int MINE_COUNT = 10;

  private final int WINDOW_X = 100;
  private final int WINDOW_Y = 100;

  private final int WINDOW_WIDTH_FIX = 6;
  private final int WINDOW_HEIGHT_FIX = 29;

  private final String MESSAGE_WIN = "Congratulations! You won the game!";
  private final String MESSAGE_LOSE = "BOOM! You lost!";

  private Minesweeper game;
  private MouseAdapter CellPressedListener = new MouseAdapter() {
    @Override
    public void mouseExited(MouseEvent e) {
      ((MinesweeperCell) e.getSource()).removeMouseListener(CellReleasedListener);
    }

    @Override
    public void mousePressed(MouseEvent e) {
      ((MinesweeperCell) e.getSource()).addMouseListener(CellReleasedListener);
    }
  };

  private MouseAdapter CellReleasedListener = new MouseAdapter() {
    @Override
    public void mouseReleased(MouseEvent e) {
      ((MinesweeperCell) e.getSource()).removeMouseListener(CellReleasedListener);
      handleClick((MinesweeperCell) e.getSource(), e.getButton());
    }
  };

  public Frame(TileRenderer renderer) throws HeadlessException {
    this.renderer = renderer;
    setLayout(new GridLayout(GRID_SIDE_SIZE, GRID_SIDE_SIZE));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLocation(WINDOW_X, WINDOW_Y);
    setPreferredSize(new Dimension(GRID_SIDE_SIZE * CELL_SIDE_SIZE + WINDOW_WIDTH_FIX,
        GRID_SIDE_SIZE * CELL_SIDE_SIZE + WINDOW_HEIGHT_FIX));
    setResizable(false);
    setVisible(true);
  }

  public void newGame() {
    clearBoard();
    setupBoard();
    pack();
  }

  private void clearBoard() {
    game = new Minesweeper(GRID_SIDE_SIZE, MINE_COUNT);
    getContentPane().removeAll();
  }

  private void setupBoard() {
    range(0, GRID_SIDE_SIZE * GRID_SIDE_SIZE).forEachOrdered(index -> {
      MinesweeperCell cell = new MinesweeperCell(index / GRID_SIDE_SIZE, index % GRID_SIDE_SIZE);
      renderer.initialize(cell);
      getContentPane().add(cell);
    });
    Stream.of(getContentPane().getComponents()).parallel().forEach(child -> {
      child.addMouseListener(CellPressedListener);
    });
  }

  private void disableAllCells() {
    Stream.of(getContentPane().getComponents()).parallel().forEach(child -> {
      child.setEnabled(false);
    });
  }

  private void displayAllHiddenMines() {
    Stream.of(getContentPane().getComponents()).parallel().forEach(child -> {
      MinesweeperCell cell = (MinesweeperCell) child;
      if (game.isMineAt(cell.row, cell.column) && game.getCellStateAt(cell.row, cell.column) != CellState.EXPOSED) {
        renderer.showHiddenMine(cell);
      }
    });
  }

  private void displayAllMinesAsSealed() {
    Stream.of(getContentPane().getComponents()).parallel().forEach(child -> {
      MinesweeperCell cell = (MinesweeperCell) child;
      if (game.isMineAt(cell.row, cell.column) && game.getCellStateAt(cell.row, cell.column) == CellState.INITIAL) {
        renderer.showSealed(cell);
      }
    });
  }

  private void displayAndDisableExposedCells() {
    Stream.of(getContentPane().getComponents()).parallel().forEach(child -> {
      MinesweeperCell cell = (MinesweeperCell) child;
      if (game.getCellStateAt(cell.row, cell.column) == CellState.EXPOSED) {
        renderer.setDisabled(cell);
        if (game.isEmptyCellAt(cell.row, cell.column)) {
          renderer.showExposed(cell);
        }
        if (game.isAdjacentCellAt(cell.row, cell.column)) {
          renderer.showExposedWithAdjacentMineCount(cell, game.getMineCountAround(cell.row, cell.column));
        }
      }
    });
  }

  private void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Game Done", JOptionPane.INFORMATION_MESSAGE);
  }

  private void checkGameStatus() {
    if (game.isOver()) {
      disableAllCells();
      if (game.isWon()) {
        displayAllMinesAsSealed();
        showMessage(MESSAGE_WIN);
      } else {
        displayAllHiddenMines();
        showMessage(MESSAGE_LOSE);
      }
      newGame();
    }
  }

  private void handleClick(MinesweeperCell cell, int mouseButton) {
    switch (mouseButton) {
    case MouseEvent.BUTTON1: {
      exposeCell(cell);
      break;
    }
    case MouseEvent.BUTTON3: {
      sealCell(cell);
      break;
    }
    }
    checkGameStatus();
  }

  private void exposeCell(MinesweeperCell cell) {
    if (game.getCellStateAt(cell.row, cell.column) == CellState.INITIAL) {
      game.exposeAt(cell.row, cell.column);
      renderer.setDisabled(cell);
      if (game.isEmptyCellAt(cell.row, cell.column)) {
        displayAndDisableExposedCells();
      }
      if (game.isAdjacentCellAt(cell.row, cell.column)) {
        renderer.showExposedWithAdjacentMineCount(cell, game.getMineCountAround(cell.row, cell.column));
      }
      if (game.isMineAt(cell.row, cell.column)) {
        renderer.showExposedMine(cell);
      }
    }
  }

  private void sealCell(MinesweeperCell cell) {
    if (game.getCellStateAt(cell.row, cell.column) != CellState.EXPOSED) {
      game.toggleSealAt(cell.row, cell.column);
      if (game.getCellStateAt(cell.row, cell.column) == CellState.INITIAL) {
        renderer.showInitial(cell);
      }
      if (game.getCellStateAt(cell.row, cell.column) == CellState.SEALED) {
        renderer.showSealed(cell);
      }
    }
  }
}
