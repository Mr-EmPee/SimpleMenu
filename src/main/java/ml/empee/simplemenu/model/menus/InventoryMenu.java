package ml.empee.simplemenu.model.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import lombok.Getter;
import ml.empee.simplemenu.handlers.InventoryHandler;
import ml.empee.simplemenu.model.panes.StaticPane;

/**
 * A menu
 */

public class InventoryMenu implements Menu {

  @Getter
  protected final Player player;

  private final Inventory topInventory;

  private final StaticPane topPane;

  public InventoryMenu(Player player, int rows, String title) {
    this.player = player;
    this.topInventory = player.getServer().createInventory(null, 9 * rows, title);
    this.topPane = new StaticPane(9, rows);
  }

  public final StaticPane top() {
    return topPane;
  }

  public void refresh() {
    topPane.refresh();
    topInventory.setContents(topPane.getContents());
    player.updateInventory();
  }

  public final void open() {
    onOpen();

    topInventory.setContents(topPane.getContents());
    player.openInventory(topInventory);
    
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
