package ml.empee.simplemenu.model.menus;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.bukkit.inventory.Inventory;

/**
 * Menu view
 */

@Value
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PACKAGE)
public class MenuView {

  Menu menu;
  Inventory topInventory;

}
