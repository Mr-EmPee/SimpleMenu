package ml.empee.simplemenu.model.panes;

import ml.empee.simplemenu.model.GItem;

/**
 * Section that allows you to put items by specifying their position
 */

public class StaticPane extends Pane {

  public StaticPane(int length, int height) {
    super(length, height);
  }

  public void setItem(int col, int row, GItem item) {
    paneItems[toSlot(col, row)] = item;
  }

  public void fillRow(int row, GItem item) {
    for (int i = 0; i < getLength(); i++) {
      setItem(i, row, item);
    }
  }

  public void fillCol(int col, GItem item) {
    for (int i = 0; i < getHeight(); i++) {
      setItem(col, i, item);
    }
  }

  public void fill(GItem item) {
    for (int i = 0; i < getLength(); i++) {
      for (int j = 0; j < getHeight(); j++) {
        setItem(i, j, item);
      }
    }
  }

  public void fill(GItem item, String... mask) {
    if (getLength() == 0 && getHeight() == 0) {
      return;
    }

  }

}
