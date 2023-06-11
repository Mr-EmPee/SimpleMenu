package ml.empee.simplemenu.handlers;

import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.model.content.MenuView;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Handle closing operations of menus
 */

@RequiredArgsConstructor
public class InventoryCloseHandler implements Listener {

  private final JavaPlugin plugin;

  @EventHandler
  public void onPluginDisable(PluginDisableEvent event) {
    if (!event.getPlugin().equals(plugin)) {
      return;
    }

    for (Player player : Bukkit.getOnlinePlayers()) {
      if (!(player.getOpenInventory() instanceof MenuView)) {
        continue;
      }

      player.closeInventory();
    }
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onInventoryClose(InventoryCloseEvent event) {
    if (!(event.getView() instanceof MenuView view)) {
      return;
    }

    view.getMenu().onClose(event);
  }

}
