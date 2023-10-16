package ml.empee.simplemenu.model.panes;

import lombok.Getter;
import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.Slot;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A section of a menu
 */

public abstract class Pane {

  @Getter
  protected final int length;
  @Getter
  protected final int height;

  private final Map<Pane, Slot> subPanes = new HashMap<>();

  protected GItem[] paneItems;
  private GItem[] viewItems;

  protected Pane(int length, int height) {
    if (length <= 0 || height <= 0) {
      throw new IllegalArgumentException("Length and Height must be greater then 0");
    }

    this.length = length;
    this.height = height;
    this.paneItems = new GItem[height * length];
  }

  public Map<Pane, Slot> getSubPanes() {
    return Collections.unmodifiableMap(subPanes);
  }

  protected int toSlot(int col, int row) {
    if (col < 0 || row < 0 || col >= length || row >= height) {
      throw new IllegalArgumentException("Trying to use slot " + col + ":" + row + " that is outside of the pane!");
    }

    return (length * row) + col;
  }

  /**
   * @param col relative to pane (starting from 0)
   * @param row relative to pane (starting from 0)
   */
  public void addPane(int col, int row, Pane pane) {
    subPanes.put(pane, Slot.of(col, row));
  }

  public final Optional<GItem> getItem(int slot) {
    return Optional.ofNullable(getItems()[slot]);
  }

  /**
   * Refresh the visible items inside the pane
   */
  public void refresh() {
    viewItems = new GItem[paneItems.length];
    subPanes.forEach((pane, offset) -> {
      pane.refresh();

      var items = pane.getItems();
      for (int col = 0; col < pane.getLength(); col++) {
        for (int row = 0; row < pane.getHeight(); row++) {
          GItem item = items[pane.toSlot(col, row)];
          if (item == null) {
            continue;
          }

          viewItems[toSlot(col + offset.getCol(), row + offset.getRow())] = item;
        }
      }
    });

    for (int i = 0; i < paneItems.length; i++) {
      var item = paneItems[i];
      if (item != null && item.isVisible()) {
        viewItems[i] = item;
      }
    }
  }

  public GItem[] getItems() {
    if (viewItems == null) {
      refresh();
    }

    return viewItems;
  }

  /**
   * @return the contents of the pane
   */
  public ItemStack[] getContents() {
    var items = getItems();
    ItemStack[] itemStacks = new ItemStack[items.length];
    for (int i = 0; i < items.length; i++) {
      if (items[i] == null) {
        continue;
      }

      itemStacks[i] = items[i].getItemStack();
    }

    return itemStacks;
  }

}
