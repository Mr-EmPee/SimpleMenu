package ml.empee.simplemenu;

import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.menus.ChestMenu;
import ml.empee.simplemenu.model.pane.ScrollPane;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

class TestVerticalScrollGui extends ChestMenu {

  public TestVerticalScrollGui(Player player) {
    super(player, "pagination", 5);
  }

  @Override
  public void onOpen(InventoryOpenEvent event) {
    top().addPane(0, 0, setupPagination());
  }

  private ScrollPane setupPagination() {
    ScrollPane pane = new ScrollPane(9, 4);

    pane.setVertical();
    pane.setRows(List.of(
        GItem.of(Material.COAL),
        GItem.of(Material.COAL, 2),
        GItem.of(Material.COAL, 3),
        GItem.of(Material.COAL, 4),
        GItem.of(Material.COAL, 5),
        GItem.of(Material.COAL, 6),
        GItem.of(Material.COAL, 7),
        GItem.of(Material.COAL, 8),
        GItem.of(Material.COAL, 9),
        GItem.of(Material.COAL, 10),
        GItem.of(Material.COAL, 11),
        GItem.of(Material.COAL, 12),
        GItem.of(Material.COAL, 13),
        GItem.of(Material.COAL, 14),
        GItem.of(Material.COAL, 15),
        GItem.of(Material.COAL, 16),
        GItem.of(Material.COAL, 17),
        GItem.of(Material.COAL, 18),
        GItem.of(Material.COAL, 19),
        GItem.of(Material.COAL, 20),
        GItem.of(Material.COAL, 21),
        GItem.of(Material.COAL, 22),
        GItem.of(Material.COAL, 23),
        GItem.of(Material.COAL, 24),
        GItem.of(Material.COAL, 25),
        GItem.of(Material.COAL, 26),
        GItem.of(Material.COAL, 27),
        GItem.of(Material.COAL, 28),
        GItem.of(Material.COAL, 29),
        GItem.of(Material.COAL, 30),
        GItem.of(Material.COAL, 31),
        GItem.of(Material.COAL, 32),
        GItem.of(Material.COAL, 33),
        GItem.of(Material.COAL, 34),
        GItem.of(Material.COAL, 35),
        GItem.of(Material.COAL, 36)
    ));

    pane.applyMask(
        "1110",
        "1010",
        "1010",
        "1011"
    );

    top().setItem(0, 4, GItem.builder()
        .itemstack(new ItemStack(Material.ARROW))
        .clickHandler(e -> {
          pane.previousRow();
          refresh();
        }).visibilityHandler(pane::hasPreviousRow).build()
    );

    top().setItem(8, 4, GItem.builder()
        .itemstack(new ItemStack(Material.ARROW))
        .clickHandler(e -> {
          pane.nextRow();
          refresh();
        }).visibilityHandler(pane::hasNextRow).build()
    );

    return pane;
  }

}
