package ml.empee.simplemenu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An item that is contained inside a Menu
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class GItem {

  private final UUID id = UUID.randomUUID();
  private ItemStack itemstack;
  private Consumer<InventoryClickEvent> clickHandler;
  private Supplier<Boolean> visibilityHandler;

  public static GItem empty() {
    return of(Material.AIR);
  }

  public static GItem of(ItemStack item) {
    return of(item, null, null);
  }

  public static GItem of(Material item) {
    return of(new ItemStack(item), null, null);
  }

  public static GItem of(Material item, int amount) {
    return of(new ItemStack(item, amount), null, null);
  }

  public ItemStack getItemStack() {
    if (itemstack == null) {
      return new ItemStack(Material.AIR);
    }

    return itemstack.clone();
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
