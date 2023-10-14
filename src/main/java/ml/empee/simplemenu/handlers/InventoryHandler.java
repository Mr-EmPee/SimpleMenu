package ml.empee.simplemenu.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.model.menus.InventoryMenu;

/**
 * Handle operations of menus
 */

@RequiredArgsConstructor
public class InventoryHandler implements Listener {

  private static final Map<UUID, InventoryMenu> inventories = new HashMap<>();
  private final JavaPlugin plugin;

  public static void register(UUID player, InventoryMenu menu) {
    inventories.put(player, menu);
  }

  @EventHandler
  public void onPluginDisable(PluginDisableEvent event) {
    if (!event.getPlugin().equals(plugin)) {
      return;
    }

    for (Player player : Bukkit.getOnlinePlayers()) {
      if (inventories.get(player.getUniqueId()) == null) {
        continue;
      }

      player.closeInventory();
    }

    inventories.clear();
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryClose(InventoryCloseEvent event) {
    var player = event.getPlayer().getUniqueId();
    InventoryMenu menu = inventories.get(player);
    if (menu == null) {
      return;
    }

    menu.onClose();
    inventories.remove(player);
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    var player = event.getView().getPlayer().getUniqueId();
    InventoryMenu menu = inventories.get(player);
    if (menu == null) {
      return;
    }

    menu.onClick(event);
    if (event.getView().getTopInventory() == event.getClickedInventory()) {
      menu.top().getItem(event.getSlot()).ifPresent(i -> i.onClick(event));
    }
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryDrag(InventoryDragEvent event) {
    var player = event.getView().getPlayer().getUniqueId();
    InventoryMenu menu = inventories.get(player);
    if (menu == null) {
      return;
    }

    menu.onDrag(event);
  }

}
