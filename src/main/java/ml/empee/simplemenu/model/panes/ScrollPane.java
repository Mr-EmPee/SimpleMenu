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

  private List<GItem> itemsCache = new ArrayList<>();
  private boolean cacheDirty = true;

  @Getter
  private final boolean vertical;
  private final int groupSize;

  @Getter
  private int totalCols;

  @Getter
  private int totalRows;

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
    cacheDirty = true;
  }

  public void add(GItem item) {
    items.add(item);
    cacheDirty = true;
  }

  public void remove(Predicate<GItem> predicate) {
    items.removeIf(predicate);
    cacheDirty = true;
  }

  @Override
  public void refresh() {
    if (cacheDirty) {
      itemsCache = getDecoratedItemsView();
      cacheDirty = false;

      if (vertical) {
        totalRows = groupSize;
        totalCols = (int) Math.ceil((double) itemsCache.size() / groupSize);
      } else {
        totalCols = groupSize;
        totalRows = (int) Math.ceil((double) itemsCache.size() / groupSize);
      }
    }

    populateItemPane(itemsCache);

    super.refresh();
  }

  private void populateItemPane(List<GItem> items) {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < length; col++) {
        int itemIndex = toIndex(col + colOffset, row + rowOffset);
        if (itemIndex == -1) {
          paneItems[toSlot(col, row)] = null;
          continue;
        }

        paneItems[toSlot(col, row)] = items.get(itemIndex);
      }
    }
  }

  private List<GItem> getDecoratedItemsView() {
    if (mask == null) {
      return items;
    }

    return mask.apply(items);
  }

  /**
   * @return the index of the item in the list
   *         -1 if if the col or row is out of bounds
   */
  private int toIndex(int col, int row) {
    if (col >= totalCols || row >= totalRows) {
      return -1;
    }

    if (vertical) {
      return (groupSize * col) + row;
    }

    return (groupSize * row) + col;
  }

}