package ml.empee.simplemenu.model.content.pane;

import ml.empee.simplemenu.model.content.Item;

/**
 * Section that allows you to put items by specifying their position
 */

public class StaticPane extends Pane {

  public StaticPane(int length, int height) {
    super(length, height);
  }

  public void setItem(int col, int row, Item item) {
    paneItems[toSlot(col, row)] = item;
  }

}
