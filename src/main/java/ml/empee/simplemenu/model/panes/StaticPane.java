package ml.empee.simplemenu.model.panes;

import java.util.ArrayList;
import java.util.List;

import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.masks.Mask;

/**
 * Section that allows you to put items by specifying their position
 */

public class StaticPane extends Pane {

  public StaticPane(int length, int height) {
    super(length, height);
  }

  public void setItem(int col, int row, GItem item) {
    content[getSlot(col, row)] = item;
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

  /**
   * Fills the pane with the given item, applying the mask
   */
  public void fill(GItem item, Mask mask) {
    if (getLength() == 0 && getHeight() == 0) {
      return;
    }

    List<GItem> items = new ArrayList<>();
    for (int i = 0; i < content.length; i++) {
      items.add(item);
    }

    items = mask.apply(items);
    for (int i = 0; i < content.length; i++) {
      content[i] = items.get(i);
    }
  }

}
