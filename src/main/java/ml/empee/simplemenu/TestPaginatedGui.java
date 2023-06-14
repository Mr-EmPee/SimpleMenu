package ml.empee.simplemenu;

import ml.empee.simplemenu.model.content.Item;
import ml.empee.simplemenu.model.content.menus.ChestMenu;
import ml.empee.simplemenu.model.content.pane.ScrollPane;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

class TestPaginatedGui extends ChestMenu {

  public TestPaginatedGui(Player player) {
    super(player, "pagination", 5);
  }

  @Override
  public void onOpen(InventoryOpenEvent event) {
    top().addPane(0, 0, setupPagination());
  }

  private ScrollPane setupPagination() {
    ScrollPane pane = new ScrollPane(9, 3);

    //pane.setVertical();
    pane.setRows(List.of(
        Item.of(Material.COAL),
        Item.of(Material.COAL, 2),
        Item.of(Material.COAL, 3),
        Item.of(Material.COAL, 4),
        Item.of(Material.COAL, 5),
        Item.of(Material.COAL, 6),
        Item.of(Material.COAL, 7),
        Item.of(Material.COAL, 8),
        Item.of(Material.COAL, 9),
        Item.of(Material.COAL, 10),
        Item.of(Material.COAL, 11),
        Item.of(Material.COAL, 12),
        Item.of(Material.COAL, 13),
        Item.of(Material.COAL, 14),
        Item.of(Material.COAL, 15),
        Item.of(Material.COAL, 16),
        Item.of(Material.COAL, 17),
        Item.of(Material.COAL, 18),
        Item.of(Material.COAL, 19),
        Item.of(Material.COAL, 20),
        Item.of(Material.COAL, 21),
        Item.of(Material.COAL, 22),
        Item.of(Material.COAL, 23),
        Item.of(Material.COAL, 24),
        Item.of(Material.COAL, 25),
        Item.of(Material.COAL, 26),
        Item.of(Material.COAL, 27),
        Item.of(Material.COAL, 28),
        Item.of(Material.COAL, 29),
        Item.of(Material.COAL, 30),
        Item.of(Material.COAL, 31),
        Item.of(Material.COAL, 32),
        Item.of(Material.COAL, 33),
        Item.of(Material.COAL, 34),
        Item.of(Material.COAL, 35),
        Item.of(Material.COAL, 36)
    ));

    pane.applyMask(
        "111",
        "101"
    );

    top().setItem(0, 4, Item.builder()
        .itemstack(new ItemStack(Material.ARROW))
        .clickHandler(e -> {
          pane.previousRow();
          refresh();
        }).visibilityHandler(pane::hasPreviousRow).build()
    );

    top().setItem(8, 4, Item.builder()
        .itemstack(new ItemStack(Material.ARROW))
        .clickHandler(e -> {
          pane.nextRow();
          refresh();
        }).visibilityHandler(pane::hasNextRow).build()
    );

    return pane;
  }

}
