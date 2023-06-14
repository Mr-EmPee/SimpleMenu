package ml.empee.simplemenu.model.menus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import ml.empee.simplemenu.InventoryHandler;
import ml.empee.simplemenu.model.pane.StaticPane;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

/**
 * A menu
 */

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class Menu {

  @Getter
  protected final Player player;
  @Getter
  private final String title;

  private final Inventory topInventory;

  private final StaticPane topPane;

  //TODO: Bottom pane (Player inv)

  public final StaticPane top() {
    return topPane;
  }

  public void refresh() {
    topPane.refresh();
    topInventory.setContents(topPane.getContents());
    player.updateInventory();
  }

  public final void open() {
    InventoryHandler.registerInventory(player.getUniqueId(), Meta.of(this, topInventory));
    player.openInventory(topInventory);

    topInventory.setContents(topPane.getContents());
    player.updateInventory();
  }

  public void onOpen(InventoryOpenEvent event) {

  }

  public void onClose(InventoryCloseEvent event) {

  }

  public void onClick(InventoryClickEvent event) {
    event.setCancelled(true);
  }

  public void onDrag(InventoryDragEvent event) {
    event.setCancelled(true);
  }

  /**
   * Contains metadata of a menu
   */
  @Value
  @RequiredArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
  public static class Meta {
    Menu menu;
    Inventory topInventory;
  }

}
