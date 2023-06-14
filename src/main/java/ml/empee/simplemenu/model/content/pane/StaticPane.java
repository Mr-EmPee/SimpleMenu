package ml.empee.simplemenu.model.content.pane;

import ml.empee.simplemenu.model.content.GItem;

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

}
