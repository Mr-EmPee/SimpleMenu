package ml.empee.simplemenu.model.menus;

import lombok.experimental.Delegate;
import ml.empee.simplemenu.model.panes.StaticPane;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class ChestMenu extends GridMenu {

  private final StaticPane top;

  public ChestMenu(Player player, int height) {
    super(player, 9, height);

    top = new StaticPane(9, height);
    addPane(0, 0, top);
  }

  protected StaticPane top() {
    return top;
  }

  public abstract String title();

  protected final Inventory buildInventory() {
    return Bukkit.createInventory(null, 9 * getHeight(), title());
  }
}
