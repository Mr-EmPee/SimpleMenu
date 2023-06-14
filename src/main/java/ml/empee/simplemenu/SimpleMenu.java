package ml.empee.simplemenu;

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

    pm.registerEvents(new InventoryHandler(plugin), plugin);
  }

}
