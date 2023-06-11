package ml.empee.simplemenu.model.content.menus;

import ml.empee.simplemenu.model.content.pane.StaticPane;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Menu that is of Chest GUI type
 */

public class ChestMenu extends Menu {

  public ChestMenu(Player player, String title, int rows) {
    super(
        player, title, Bukkit.createInventory(player, rows * 9),
        new StaticPane(9, rows)
    );
  }

}
