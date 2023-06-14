package ml.empee.simplemenu;

import ml.empee.simplemenu.handlers.InventoryCloseHandler;
import ml.empee.simplemenu.handlers.InventoryInteractHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Access class of the library
 **/

public final class SimpleMenu {

  /**
   * Called when enabling the plugin
   */
  public void init(JavaPlugin plugin) {
    var pm = plugin.getServer().getPluginManager();

    pm.registerEvents(new InventoryCloseHandler(plugin), plugin);
    pm.registerEvents(new InventoryInteractHandler(), plugin);
  }

}
