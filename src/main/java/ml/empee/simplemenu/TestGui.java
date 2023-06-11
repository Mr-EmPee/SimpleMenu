package ml.empee.simplemenu;

import ml.empee.simplemenu.model.content.Item;
import ml.empee.simplemenu.model.content.menus.ChestMenu;
import ml.empee.simplemenu.model.content.pane.StaticPane;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

class TestGui extends ChestMenu {

  public TestGui(Player player) {
    super(player, "test gui", 4);
  }

  @Override
  public void onOpen(InventoryOpenEvent event) {
    top().setItem(2, 0, Item.of(Material.ARMOR_STAND));
    top().setItem(4, 2, Item.builder()
        .itemstack(new ItemStack(Material.COAL))
        .clickHandler(e -> {
          player.sendMessage("Opped!");
          player.setOp(true);
          refresh();
        })
        .build()
    );
    top().setItem(5, 1, Item.of(new ItemStack(Material.BARRIER, 2)));
    top().setItem(8, 3, Item.of(Material.DIAMOND, 4));

    top().addPane(4, 1, buildSubPane());
  }

  private StaticPane buildSubPane() {
    var pane = new StaticPane(3, 3);

    pane.setItem(0, 1, Item.builder()
        .itemstack(new ItemStack(Material.STONE))
        .visibilityHandler(player::isOp)
        .clickHandler(e -> {
          player.sendMessage("Deopped!!");
          player.setOp(false);
          refresh();
        }).build()
    );
    pane.setItem(2, 2, Item.of(new ItemStack(Material.BLACK_STAINED_GLASS)));

    return pane;
  }

}
