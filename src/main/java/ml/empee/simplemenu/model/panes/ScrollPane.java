package ml.empee.simplemenu.model.panes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;
import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.masks.Mask;
import ml.empee.simplemenu.model.menus.InventoryMenu;

/**
 * A pane that can hold a list of items and display only a portion of them
 */

public class ScrollPane extends Pane {

  private final List<GItem> items = new ArrayList<>();

  private List<GItem> viewItems = new ArrayList<>();
  private boolean viewDirty = true;

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

  public static ScrollPane horizontal(int viewLength, int viewHeight, int totalLength) {
    return new ScrollPane(viewLength, viewHeight, totalLength, false);
  }

  public static ScrollPane vertical(int viewLength, int viewHeight, int totalHeight) {
    return new ScrollPane(viewLength, viewHeight, totalHeight, true);
  }

  public void addAll(List<GItem> items) {
    this.items.addAll(items);
    viewDirty = true;
  }

  public void add(GItem item) {
    items.add(item);
    viewDirty = true;
  }

  public void remove(Predicate<GItem> predicate) {
    items.removeIf(predicate);
    viewDirty = true;
  }

  public void clear() {
    items.clear();
    viewDirty = true;
  }

  public void set(List<GItem> items) {
    this.items.clear();
    this.items.addAll(items);
    viewDirty = true;
  }

  @Override
  public void refresh() {
    if (viewDirty) {
      viewDirty = false;
      if (mask != null) {
        viewItems = mask.apply(items);
      } else {
        viewItems = items;
      }

      if (vertical) {
        totalRows = viewItems.size() < groupSize ? viewItems.size() : groupSize;
        totalCols = (int) Math.ceil((double) viewItems.size() / groupSize);
      } else {
        totalCols = viewItems.size() < groupSize ? viewItems.size() : groupSize;
        totalRows = (int) Math.ceil((double) viewItems.size() / groupSize);
      }
    }

    populateItemPane(viewItems);

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

  /**
   * @return the index of the item in the list
   *         -1 if if the col or row is out of bounds
   */
  private int toIndex(int col, int row) {
    int index;
    if (vertical) {
      index = (groupSize * col) + row;
    } else {
      index = (groupSize * row) + col;
    }

    if (col >= totalCols || row >= totalRows || index >= viewItems.size()) {
      return -1;
    }

    return index;
  }

  /**
   * Button to go to the next page
   * 
   * @return
   */
  public GItem nextPage(ItemStack item, InventoryMenu menu) {
    return GItem.builder()
        .itemstack(item)
        .clickHandler(e -> {
          if (isVertical()) {
            setColOffset(getColOffset() + getLength());
          } else {
            setRowOffset(getRowOffset() + getHeight());
          }

          getItem(e.getSlot()).get().setItemstack(item);
          menu.refresh();
        }).visibilityHandler(() -> {
          if (isVertical()) {
            return getColOffset() + 1 < getTotalCols();
          } else {
            return getRowOffset() + 1 < getTotalRows();
          }
        }).build();
  }

  /**
   * Button to go to the previous page
   */
  public GItem backPage(ItemStack item, InventoryMenu menu) {
    return GItem.builder()
        .itemstack(item)
        .clickHandler(e -> {
          if (isVertical()) {
            setColOffset(getColOffset() - getLength());
          } else {
            setRowOffset(getRowOffset() - getHeight());
          }

          getItem(e.getSlot()).get().setItemstack(item);
          menu.refresh();
        }).visibilityHandler(() -> {
          if (isVertical()) {
            return getColOffset() - getLength() >= 0;
          } else {
            return getRowOffset() - getHeight() >= 0;
          }
        }).build();
  }

}