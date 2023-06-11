package ml.empee.simplemenu;

import ml.empee.simplemenu.handlers.InventoryCloseHandler;
import ml.empee.simplemenu.handlers.InventoryInteractHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Access class of the library
 **/

public final class SimpleMenu extends JavaPlugin implements CommandExecutor {

  @Override
  public void onEnable() {
    init(this);
    getCommand("sm").setExecutor(this);
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    new TestPaginatedGui(((Player) sender)).open();
    return true;
  }

  /**
   * Called when enabling the plugin
   */
  public void init(JavaPlugin plugin) {
    var pm = plugin.getServer().getPluginManager();

    pm.registerEvents(new InventoryCloseHandler(plugin), plugin);
    pm.registerEvents(new InventoryInteractHandler(), plugin);
  }

}
