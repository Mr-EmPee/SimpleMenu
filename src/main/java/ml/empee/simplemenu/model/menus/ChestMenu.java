package ml.empee.simplemenu.model.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ml.empee.simplemenu.model.panes.StaticPane;

/**
 * Menu that is of Chest GUI type
 */

public class ChestMenu extends InventoryMenu {

  public ChestMenu(Player player, int rows, String title) {
    super(
        player, title, Bukkit.createInventory(player, rows * 9, title), new StaticPane(9, rows)
    );
  }

}
