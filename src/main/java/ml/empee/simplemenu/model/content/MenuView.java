package ml.empee.simplemenu.model.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.model.content.menus.Menu;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

/**
 * An inventory view that contains a menu
 */

@RequiredArgsConstructor
public class MenuView extends InventoryView {

  @Getter
  private final Menu menu;
  private final Inventory topInventory;

  @Override
  public Inventory getTopInventory() {
    return topInventory;
  }

  @Override
  public Inventory getBottomInventory() {
    return menu.getPlayer().getInventory();
  }

  @Override
  public HumanEntity getPlayer() {
    return menu.getPlayer();
  }

  @Override
  public InventoryType getType() {
    return topInventory.getType();
  }

  @Override
  public String getTitle() {
    return menu.getTitle();
  }
}
