package ml.empee.simplemenu.model.panes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.Setter;
import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.masks.Mask;

/**
 * A pane that can hold a list of items and display only a portion of them
 */

public class ScrollPane extends Pane {

  private final List<GItem> items = new ArrayList<>();

  private final boolean vertical;
  private final int groupSize;

  @Setter
  @Getter
  private Mask mask;

  @Setter
  @Getter
  private int colOffset;

  @Setter
  @Getter
  private int rowOffset;

  private ScrollPane(int viewLength, int viewHeight, int groupSize, boolean vertical) {
    super(viewLength, viewHeight);

    this.vertical = vertical;
    this.groupSize = groupSize;
  }

  public static ScrollPane horizontal(int viewLength, int viewHeight, int totalHeight) {
    return new ScrollPane(viewLength, viewHeight, totalHeight, false);
  }

  public static ScrollPane vertical(int viewLength, int viewHeight, int totalLength) {
    return new ScrollPane(viewLength, viewHeight, totalLength, true);
  }

  public void addAll(List<GItem> items) {
    this.items.addAll(items);
  }

  public void add(GItem item) {
    items.add(item);
  }

  public void remove(Predicate<GItem> predicate) {
    items.removeIf(predicate);
  }

  @Override
  public void refresh() {
    var items = getDecoratedItems();

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < length; col++) {
        int itemIndex = toIndex(col + colOffset, row + rowOffset);
        if (itemIndex == -1 || itemIndex >= items.size()) {
          paneItems[toSlot(col, row)] = null;
          continue;
        }

        paneItems[toSlot(col, row)] = items.get(itemIndex);
      }
    }

    super.refresh();
  }

  private List<GItem> getDecoratedItems() {
    if (mask == null) {
      return items;
    }

    return mask.apply(items);
  }

  /**
   * @return the index of the item in the list
   * -1 if the column or row is out of bounds
   * may still return an index out of bounds
   */
  private int toIndex(int col, int row) {
    if (vertical) {
      if (row >= groupSize) {
        return -1;
      }
      
      return (groupSize * col) + row;
    }

    if (col >= groupSize) {
      return -1;
    }

    return (groupSize * row) + col;
  }

}