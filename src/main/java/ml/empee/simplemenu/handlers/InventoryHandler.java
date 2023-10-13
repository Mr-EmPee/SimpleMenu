package ml.empee.simplemenu.handlers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.model.menus.InventoryMenu;

/**
 * Handle operations of menus
 */

@RequiredArgsConstructor
public class InventoryHandler implements Listener {

  private static final Map<InventoryView, InventoryMenu> inventories = new HashMap<>();
  private final JavaPlugin plugin;

  public static void register(InventoryView view, InventoryMenu menu) {
    inventories.put(view, menu);
  }

  @EventHandler
  public void onPluginDisable(PluginDisableEvent event) {
    if (!event.getPlugin().equals(plugin)) {
      return;
    }

    for (Player player : Bukkit.getOnlinePlayers()) {
      if (inventories.get(player.getOpenInventory()) == null) {
        continue;
      }

      player.closeInventory();
    }

    inventories.clear();
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryClose(InventoryCloseEvent event) {
    InventoryMenu menu = inventories.get(event.getView());
    if (menu == null) {
      return;
    }

    menu.onClose();
    inventories.remove(event.getView());
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    InventoryMenu menu = inventories.get(event.getView());
    if (menu == null) {
      return;
    }

    menu.onClick(event);

    if (event.getView().getTopInventory() == event.getClickedInventory()) {
      menu.top().getItem(event.getSlot()).ifPresent(
          i -> i.onClick(event)
      );
    }
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryDrag(InventoryDragEvent event) {
    InventoryMenu menu = inventories.get(event.getView());
    if (menu == null) {
      return;
    }

    menu.onDrag(event);
  }

}
