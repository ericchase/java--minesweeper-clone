package minesweeper.ui;

import javax.swing.*;
import java.awt.*;

public class UiGeneric {
  public static void main(String[] args) {
    (new Frame(new GenericRenderer())).newGame();
  }
}

class GenericRenderer implements TileRenderer {
  @Override
  public void initialize(JButton button) {
    button.setForeground(new Color(100, 100, 100));
    button.setFont(new Font("Arial", Font.PLAIN, 30));
    button.setFocusPainted(false);
    button.setMargin(new Insets(0, 0, 0, 0));
  }

  @Override
  public void setDisabled(JButton button) {
    button.setEnabled(false);
  }

  @Override
  public void showExposed(JButton button) {

  }

  @Override
  public void showExposedWithAdjacentMineCount(JButton button, int mineCount) {
    button.setText(Integer.toString(mineCount));
  }

  @Override
  public void showInitial(JButton button) {
    button.setText("");
  }

  @Override
  public void showHiddenMine(JButton button) {
    button.setText("X");
  }

  @Override
  public void showSealed(JButton button) {
    button.setText("S");
  }

  @Override
  public void showExposedMine(JButton button) {
    button.setText("!");
  }

  @Override
  public void paint(JButton cell, Graphics g) {
  }
}
