package ml.empee.simplemenu.model.menus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.handlers.InventoryHandler;
import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.Slot;
import ml.empee.simplemenu.model.panes.Pane;

/**
 * A menu
 */

// TODO: Handle bottom inventory

@RequiredArgsConstructor
public abstract class GridMenu implements Menu {

  private final Map<Pane, Slot> panes = new LinkedHashMap<>();

  @Getter
  protected final Player player;

  @Getter
  private final int length;

  @Getter
  private final int height;

  @Getter
  private Inventory inventory;
  private ItemStack[] content;

  public void addPane(int col, int row, Pane pane) {
    panes.put(pane, Slot.of(col, row));
  }

  protected abstract Inventory buildInventory();

  public void update() {
    if (inventory == null) {
      throw new IllegalStateException("Cannot update a menu that is not open");
    }

    updateContent();
    inventory.setContents(content);
  }

  /**
   * Overwrites the menu items with the items from the pane
   */
  public void update(Pane pane) {
    var offset = panes.get(pane);
    if (inventory == null || content == null || offset == null) {
      throw new IllegalStateException("Menu must be open and must contain the pane");
    }

    updateContent(pane, offset);
    inventory.setContents(content);
  }

  private void updateContent(Pane pane, Slot offset) {
    for (int col = 0; col < pane.getLength(); col++) {
      for (int row = 0; row < pane.getHeight(); row++) {
        var item = pane.getItem(col, row).orElse(null);
        if (item != null && item.isVisible()) {
          int index = ((row + offset.getRow()) * 9) + col + offset.getCol();
          content[index] = item.getItemStack();
        }
      }
    }
  }

  private void updateContent() {
    inventory = buildInventory();
    content = new ItemStack[inventory.getSize()];

    for (int col = 0; col < length; col++) {
      for (int row = 0; row < height; row++) {
        content[(row * 9) + col] = getItem(col, row).getItemStack();
      }
    }
  }

  /**
   * Get the item at the specified slot
   */
  public GItem getItem(int col, int row) {
    for (var entry : panes.entrySet()) {
      var pane = entry.getKey();
      var offset = entry.getValue();

      int slot = pane.getSlot(col - offset.getCol(), row - offset.getRow());
      if (slot != -1) {
        var item = pane.getItem(slot);
        if (item != null && item.isVisible()) {
          return item;
        }
      }
    }

    return GItem.empty();
  }

  public Optional<ItemStack> getContent(int col, int row) {
    return Optional.ofNullable(inventory.getItem((row * length) + col));
  }

  public final void open() {
    onOpen();

    updateContent();
    inventory.setContents(content);
    player.openInventory(inventory);

    InventoryHandler.register(player.getUniqueId(), this);
  }

  public void onOpen() {
  }

  public void onClose() {
  }

  public void onClick(InventoryClickEvent event) {
    event.setCancelled(true);
  }

  public void onDrag(InventoryDragEvent event) {
    event.setCancelled(true);
  }

}
