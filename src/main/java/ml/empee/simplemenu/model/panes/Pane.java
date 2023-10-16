package ml.empee.simplemenu.model.panes;

import java.util.Optional;

import lombok.Getter;
import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.Slot;

/**
 * A section of a menu
 */

public abstract class Pane {

  @Getter
  protected final int length;
  @Getter
  protected final int height;
  @Getter
  protected final Slot offset;

  protected GItem[] content;

  protected Pane(int length, int height, Slot offset) {
    if (length <= 0 || height <= 0) {
      throw new IllegalArgumentException("Length and Height must be greater then 0");
    }

    this.offset = offset;
    this.length = length;
    this.height = height;

    this.content = new GItem[height * length];
  }

  /**
   * The slot index of the given coordinates, or -1 if the coordinates are out of bounds
   */
  public int getSlot(int col, int row) {
    if (col < 0 || row < 0 || col >= length || row >= height) {
      return -1;
    }

    return (length * row) + col;
  }

  public final Optional<GItem> getItem(int col, int row) {
    return Optional.ofNullable(content[getSlot(col, row)]);
  }

  public GItem getItem(int index) {
    return content[index];
  }

}
