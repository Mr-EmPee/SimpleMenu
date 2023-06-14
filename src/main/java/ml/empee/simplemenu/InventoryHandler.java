package ml.empee.simplemenu;

import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.model.menus.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handle operations of menus
 */

@RequiredArgsConstructor
public class InventoryHandler implements Listener {

  private static final Map<UUID, Menu> inventories = new HashMap<>();
  private final JavaPlugin plugin;

  public static void registerInventory(UUID owner, Menu view) {
    inventories.put(owner, view);
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
    Menu menu = inventories.get(event.getPlayer().getUniqueId());
    if (menu == null) {
      return;
    }

    menu.onClose(event);
    inventories.remove(event.getPlayer().getUniqueId());
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryOpen(InventoryOpenEvent event) {
    Menu menu = inventories.get(event.getPlayer().getUniqueId());
    if (menu == null) {
      return;
    }

    menu.onOpen(event);
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    Menu menu = inventories.get(event.getWhoClicked().getUniqueId());
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
    Menu menu = inventories.get(event.getWhoClicked().getUniqueId());
    if (menu == null) {
      return;
    }

    menu.onDrag(event);
  }

}
