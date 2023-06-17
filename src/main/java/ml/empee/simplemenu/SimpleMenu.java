package ml.empee.simplemenu;

import ml.empee.simplemenu.handlers.InventoryHandler;
import ml.empee.simplemenu.handlers.SignHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Access class of the library
 **/

public final class SimpleMenu {

  private SignHandler signHandler;
  private InventoryHandler inventoryHandler;

  public void init(JavaPlugin plugin) {
    var pm = plugin.getServer().getPluginManager();

    inventoryHandler = new InventoryHandler(plugin);
    pm.registerEvents(inventoryHandler, plugin);
    if (pm.isPluginEnabled("ProtocolLib")) {
      signHandler = new SignHandler(plugin);
      signHandler.registerProtocolLibListeners();
    }
  }

  public void unregister(JavaPlugin plugin) {
    if (signHandler != null) {
      signHandler.unregisterProtoLibListeners();
    }

    HandlerList.unregisterAll(inventoryHandler);
  }
}
