package ml.empee.simplemenu.model.content;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.model.content.menus.Menu;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

/**
 * An inventory view that contains a menu
 */

@RequiredArgsConstructor
public class MenuView extends InventoryView {

  @Getter
  private final Menu menu;
  private final Inventory topInventory;

  @Override
  public @NotNull Inventory getTopInventory() {
    return topInventory;
  }

  @Override
  public @NotNull Inventory getBottomInventory() {
    return menu.getPlayer().getInventory();
  }

  @Override
  public @NotNull HumanEntity getPlayer() {
    return menu.getPlayer();
  }

  @Override
  public @NotNull InventoryType getType() {
    return topInventory.getType();
  }

  @Override
  public @NotNull String getTitle() {
    return menu.getTitle();
  }
}
