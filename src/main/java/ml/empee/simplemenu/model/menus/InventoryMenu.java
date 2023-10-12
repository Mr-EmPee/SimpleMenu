package ml.empee.simplemenu.model.menus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.handlers.InventoryHandler;
import ml.empee.simplemenu.model.panes.StaticPane;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

/**
 * A menu
 */

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class InventoryMenu implements Menu {

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
    onOpen();

    topInventory.setContents(topPane.getContents());
    InventoryHandler.registerInventory(player.openInventory(topInventory), this);
  }

  public void onOpen() {

  }

  public void onClose(InventoryCloseEvent event) {

  }

  public void onClick(InventoryClickEvent event) {
    event.setCancelled(true);
  }

  public void onDrag(InventoryDragEvent event) {
    event.setCancelled(true);
  }

}
