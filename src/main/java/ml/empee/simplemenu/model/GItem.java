package ml.empee.simplemenu.model;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * An item that is contained inside a Menu
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GItem {

  private final UUID id = UUID.randomUUID();

  private ItemStack itemStack;
  private Supplier<ItemStack> itemStackHandler;

  private Consumer<InventoryClickEvent> clickHandler;
  private Supplier<Boolean> visibilityHandler;

  public static GItem empty() {
    return of(Material.AIR, 1);
  }

  public static GItem of(Material item, int amount) {
    return GItem.builder()
        .itemStackHandler(() -> new ItemStack(item, amount))
        .build();
  }

  public static GItem of(ItemStack item) {
    return GItem.builder()
        .itemStackHandler(() -> item)
        .build();
  }

  /**
   * @return the ItemStack that represents this item or null
   */
  public ItemStack getItemStack() {
    if (itemStack != null) {
      return itemStack;
    }
    
    return itemStackHandler.get();
  }

  public void onClick(InventoryClickEvent event) {
    if (clickHandler == null) {
      return;
    }

    clickHandler.accept(event);
  }

  public boolean isVisible() {
    if (visibilityHandler == null) {
      return true;
    }

    return visibilityHandler.get();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GItem)) {
      return false;
    }

    return Objects.equals(id, ((GItem) obj).id);
  }
}
