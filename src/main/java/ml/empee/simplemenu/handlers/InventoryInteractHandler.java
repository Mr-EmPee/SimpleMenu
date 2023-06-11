package ml.empee.simplemenu.handlers;

import ml.empee.simplemenu.model.content.MenuView;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Handle interactions inside menus
 */

public class InventoryInteractHandler implements Listener {

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    if (!(event.getView() instanceof MenuView view)) {
      return;
    }

    var menu = view.getMenu();
    menu.onClick(event);

    if (view.getTopInventory() == event.getClickedInventory()) {
      menu.top().getItem(event.getSlot()).ifPresent(
          i -> i.onClick(event)
      );
    }
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryDrag(InventoryDragEvent event) {
    if (!(event.getView() instanceof MenuView view)) {
      return;
    }

    view.getMenu().onDrag(event);
  }

}
