package ml.empee.simplemenu.model.panes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import lombok.Getter;
import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.masks.Mask;

/**
 * A pane that can hold a list of items and display only a portion of them
 */

public class ScrollPane extends Pane {

  private final List<GItem> items = new ArrayList<>();

  private List<GItem> maskedItems = new ArrayList<>();

  @Getter
  private final boolean vertical;
  private final int groupSize;

  @Getter
  private int totalCols;

  @Getter
  private int totalRows;

  @Getter
  private Mask mask;

  @Getter
  private int colOffset;

  @Getter
  private int rowOffset;

  private ScrollPane(int viewLength, int viewHeight, int groupSize, boolean vertical) {
    super(viewLength, viewHeight);

    this.vertical = vertical;
    this.groupSize = groupSize;
  }

  public static ScrollPane horizontal(int viewLength, int viewHeight, int totalLength) {
    return new ScrollPane(viewLength, viewHeight, totalLength, false);
  }

  public static ScrollPane vertical(int viewLength, int viewHeight, int totalHeight) {
    return new ScrollPane(viewLength, viewHeight, totalHeight, true);
  }

  public void addAll(List<GItem> items) {
    this.items.addAll(items);
    update();
  }

  public void add(GItem item) {
    items.add(item);
    update();
  }

  public void remove(Predicate<GItem> predicate) {
    items.removeIf(predicate);
    update();
  }

  public void clear() {
    items.clear();
    update();
  }

  public void set(List<GItem> items) {
    this.items.clear();
    this.items.addAll(items);
    update();
  }

  public void setColOffset(int colOffset) {
    if (colOffset < 0 || colOffset >= totalCols) {
      throw new IllegalArgumentException("Col offset out of bounds");
    }

    this.colOffset = colOffset;
    update();
  }

  public void setRowOffset(int rowOffset) {
    if (rowOffset < 0 || rowOffset >= totalRows) {
      throw new IllegalArgumentException("Col offset out of bounds");
    }

    this.rowOffset = rowOffset;
    update();
  }

  public void setMask(Mask mask) {
    this.mask = mask;
    update();
  }

  private void update() {
    updateMaskedItems();
    updateTotalColsAndRows();
    updatePaneView(maskedItems);
  }

  private void updateMaskedItems() {
    if (mask != null) {
      maskedItems = mask.apply(items);
    } else {
      maskedItems = items;
    }
  }

  private void updateTotalColsAndRows() {
    if (vertical) {
      totalRows = maskedItems.size() < groupSize ? maskedItems.size() : groupSize;
      totalCols = (int) Math.ceil((double) maskedItems.size() / groupSize);
    } else {
      totalCols = maskedItems.size() < groupSize ? maskedItems.size() : groupSize;
      totalRows = (int) Math.ceil((double) maskedItems.size() / groupSize);
    }
  }

  private void updatePaneView(List<GItem> items) {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < length; col++) {
        int itemIndex = getIndex(col + colOffset, row + rowOffset);
        if (itemIndex == -1) {
          content[getSlot(col, row)] = null;
          continue;
        }

        content[getSlot(col, row)] = items.get(itemIndex);
      }
    }
  }

  /**
   * @return the index of the item in the list
   *         -1 if if the col or row is out of bounds
   */
  private int getIndex(int col, int row) {
    int index = vertical ? (groupSize * col) + row : (groupSize * row) + col;
    if (col >= totalCols || row >= totalRows || index >= maskedItems.size()) {
      return -1;
    }

    return index;
  }

  public int getPage() {
    if (getTotalCols() == 0 || getTotalRows() == 0) {
      return 0;
    }

    return isVertical() ? getColOffset() / getTotalCols() : getRowOffset() / getTotalRows();
  }

  public void nextPage() {
    if (isVertical()) {
      setColOffset(getColOffset() + getLength());
    } else {
      setRowOffset(getRowOffset() + getHeight());
    }
  }

  public void nextCol() {
    setColOffset(getColOffset() + 1);
  }

  public void nextRow() {
    setRowOffset(getRowOffset() + 1);
  }

  public void previousPage() {
    if (isVertical()) {
      setColOffset(getColOffset() - getLength());
    } else {
      setRowOffset(getRowOffset() - getHeight());
    }
  }

  public void previousCol() {
    setColOffset(getColOffset() - 1);
  }

  public void previousRow() {
    setRowOffset(getRowOffset() - 1);
  }

  public boolean hasNext() {
    if (isVertical()) {
      return getColOffset() + 1 < getTotalCols();
    } else {
      return getRowOffset() + 1 < getTotalRows();
    }
  }

  public boolean hasPrevious() {
    if (isVertical()) {
      return getColOffset() - 1 >= 0;
    } else {
      return getRowOffset() - 1 >= 0;
    }
  }

}