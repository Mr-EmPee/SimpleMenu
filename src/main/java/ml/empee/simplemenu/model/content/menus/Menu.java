package ml.empee.simplemenu.model.content.menus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.model.content.MenuView;
import ml.empee.simplemenu.model.content.pane.StaticPane;
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
    var event = new InventoryOpenEvent(new MenuView(this, topInventory));
    onOpen(event);
    if (event.isCancelled()) {
      return;
    }

    topInventory.setContents(topPane.getContents());
    player.openInventory(event.getView());
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

}
