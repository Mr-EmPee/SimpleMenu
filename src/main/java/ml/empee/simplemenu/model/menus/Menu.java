package ml.empee.simplemenu.model.menus;

import org.bukkit.entity.Player;

/**
 * Represent a menu
 */

public interface Menu {

  Player getPlayer();

  void open();

}
