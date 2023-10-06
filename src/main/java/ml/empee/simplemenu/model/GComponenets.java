package ml.empee.simplemenu.model;

import org.bukkit.inventory.ItemStack;

import lombok.experimental.UtilityClass;
import ml.empee.simplemenu.model.menus.InventoryMenu;
import ml.empee.simplemenu.model.pane.ScrollPane;

/**
 * Provide pre-made components for menus
 */

@UtilityClass
public class GComponenets {

  /**
   * Button to go to the next page
   * 
   * @return
   */
  public static GItem nextPage(ItemStack item, InventoryMenu menu, ScrollPane pane) {
    return GItem.builder()
        .itemstack(item)
        .clickHandler(e -> {
          if (pane.hasVerticalFilling()) {
            pane.setCol(pane.getCurrentCol() + pane.getLength());
          } else {
            pane.setRow(pane.getCurrentRow() + pane.getHeight());
          }

          pane.getItem(e.getSlot()).get().setItemstack(item);
          menu.refresh();
        })
        .visibilityHandler(() -> pane.hasVerticalFilling() ? pane.hasNextCol() : pane.hasNextRow())
        .build();
  }

  /**
   * Button to go to the previous page
   */
  public static GItem backPage(ItemStack item, InventoryMenu menu, ScrollPane pane) {
    return GItem.builder()
        .itemstack(item)
        .clickHandler(e -> {
          if (pane.hasVerticalFilling()) {
            pane.setCol(pane.getCurrentCol() - pane.getLength());
          } else {
            pane.setRow(pane.getCurrentRow() - pane.getHeight());
          }

          pane.getItem(e.getSlot()).get().setItemstack(item);
          menu.refresh();
        })
        .visibilityHandler(() -> pane.hasVerticalFilling() ? pane.hasPreviousCol() : pane.hasPreviousRow())
        .build();
  }

}
