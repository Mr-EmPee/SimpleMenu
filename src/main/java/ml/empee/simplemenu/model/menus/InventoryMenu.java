package ml.empee.simplemenu.model.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class InventoryMenu implements Menu {

  @Getter
  protected final Player player;

  private final Map<Pane, Slot> panes = new HashMap<>();

  private final int rows;

  private Inventory inventory;

  private ItemStack[] content;

  protected String title = "";

  public void addPane(int col, int row, Pane pane) {
    panes.put(pane, Slot.of(col, row));
  }

  public void update() {
    if (inventory == null) {
      throw new IllegalStateException("Cannot update a menu that is not open");
    }

    content = null;
    inventory.setContents(getContent());
  }

  /**
   * Overwrites the menu items with the items from the pane
   */
  public void update(Pane pane) {
    var offset = panes.get(pane);
    if (inventory == null || content == null || offset == null) {
      throw new IllegalStateException("Menu must be open and must contain the pane");
    }

    for (int col = 0; col < pane.getLength(); col++) {
      for (int row = 0; row < pane.getHeight(); row++) {
        var item = pane.getItem(col, row).orElse(null);
        if (item != null && item.isVisible()) {
          int index = ((row + offset.getRow()) * 9) + col + offset.getCol();
          content[index] = item.getItemStack();
        }
      }
    }

    inventory.setContents(content);
  }

  protected ItemStack[] getContent() {
    if (content != null) {
      return content;
    }

    content = new ItemStack[9 * rows];

    for (int col = 0; col < 9; col++) {
      for (int row = 0; row < rows; row++) {
        content[(row * 9) + col] = getItem(col, row).getItemStack();
      }
    }

    return content;
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

  public final void open() {
    onOpen();

    if (inventory == null) {
      inventory = player.getServer().createInventory(null, 9 * rows, title);
    }

    inventory.setContents(getContent());
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
