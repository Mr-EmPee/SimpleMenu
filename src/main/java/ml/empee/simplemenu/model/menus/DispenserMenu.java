package ml.empee.simplemenu.model.menus;

import lombok.experimental.Delegate;
import ml.empee.simplemenu.model.panes.StaticPane;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public abstract class DispenserMenu extends GridMenu {

  private final StaticPane top;

  public DispenserMenu(Player player) {
    super(player, 3, 3);

    top = new StaticPane(3, 3);
    addPane(0, 0, top);
  }

  protected StaticPane top() {
    return top;
  }

  protected abstract String title();

  @Override
  protected final Inventory buildInventory() {
    return Bukkit.createInventory(null, InventoryType.DISPENSER, title());
  }
}
