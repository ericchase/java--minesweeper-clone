package minesweeper.ui;

import javax.swing.*;
import java.awt.*;

public interface TileRenderer {
  void initialize(JButton cell);

  void setDisabled(JButton cell);

  void showExposed(JButton cell);

  void showExposedWithAdjacentMineCount(JButton cell, int mineCount);

  void showInitial(JButton cell);

  void showHiddenMine(JButton cell);

  void showSealed(JButton cell);

  void showExposedMine(JButton cell);

  void paint(JButton cell, Graphics g);
}
