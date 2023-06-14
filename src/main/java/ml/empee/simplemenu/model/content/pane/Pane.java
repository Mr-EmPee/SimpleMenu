package ml.empee.simplemenu.model.content.pane;

import lombok.Getter;
import ml.empee.simplemenu.model.content.Item;
import ml.empee.simplemenu.model.content.Slot;
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
  private final int length;
  @Getter
  private final int height;

  private final Map<Pane, Slot> subPanes = new HashMap<>();

  protected Item[] paneItems;
  private Item[] currentItems;

  protected Pane(int length, int height) {
    if (length <= 0 || height <= 0) {
      throw new IllegalArgumentException("Length and Height must be greater then 0");
    }

    this.length = length;
    this.height = height;
    this.paneItems = new Item[height * length];
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

  public final Optional<Item> getItem(int slot) {
    return Optional.ofNullable(getItems()[slot]);
  }

  /**
   * Refresh the visible items inside the pane
   */
  public void refresh() {
    currentItems = new Item[paneItems.length];
    for (int i = 0; i < paneItems.length; i++) {
      var item = paneItems[i];
      if (item != null && item.isVisible()) {
        currentItems[i] = item;
      }
    }

    subPanes.keySet().forEach(Pane::refresh);
    subPanes.forEach((pane, offset) -> importItemsFromPane(pane, offset, currentItems));
  }

  public Item[] getItems() {
    if (currentItems == null) {
      refresh();
    }

    return currentItems;
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

  private void importItemsFromPane(Pane pane, Slot offset, Item[] target) {
    var items = pane.getItems();
    for (int col = 0; col < pane.getLength(); col++) {
      for (int row = 0; row < pane.getHeight(); row++) {
        Item item = items[pane.toSlot(col, row)];
        if (item == null) {
          continue;
        }

        target[toSlot(col + offset.getCol(), row + offset.getRow())] = item;
      }
    }
  }


}
