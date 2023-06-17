package ml.empee.simplemenu.model.pane;

import lombok.Getter;
import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.Mask;

import java.util.List;

/**
 * You can think of this pane as window that look upon
 * a matrix of items.
 * <br><br>
 * The length and height is the size of the window, but the matrix can be much bugger then that.
 * This class offers methods to control which subset of the items display at the window and other
 * useful method like a way to apply a mask to the matrix.
 */

public class ScrollPane extends Pane {

  private GItem[][] columns;

  @Getter
  private int totalCols;

  @Getter
  private int totalRows;

  @Getter
  private boolean vertical;

  @Getter
  private int currentCol = 0;

  @Getter
  private int currentRow = 0;

  public ScrollPane(int length, int height) {
    super(length, height);
  }

  public void setVertical() {
    vertical = true;
  }

  public void applyMask(String... mask) {
    if (columns == null) {
      throw new IllegalStateException("Set the items before applying a mask!");
    }

    Mask maskFormatter = new Mask(mask);
    columns = maskFormatter.applyMask(columns, vertical);
    totalCols = columns.length;
    totalRows = columns[0].length;
  }

  public void setRows(List<GItem> items, int maxCols) {
    setItems(items, maxCols, (int) Math.ceil(items.size() / (double) maxCols));
  }

  public void setRows(List<GItem> items) {
    setRows(items, getLength());
  }

  public void setCols(List<GItem> items, int maxRows) {
    setItems(items, (int) Math.ceil(items.size() / (double) maxRows), maxRows);
  }

  public void setCols(List<GItem> items) {
    setCols(items, getHeight());
  }

  /**
   * Populate the pane
   *
   * @param totalCols max columns that the pane will have
   * @param totalRows max rows that the pane will have
   */
  public void setItems(List<GItem> items, int totalCols, int totalRows) {
    if (totalCols * totalRows < items.size()) {
      throw new IllegalArgumentException("Items doesn't fit into the pane");
    }

    this.totalCols = totalCols;
    this.totalRows = totalRows;
    this.columns = new GItem[totalCols][totalRows];

    if (vertical) {
      populateItemsVertically(items);
    } else {
      populateItemsHorizontally(items);
    }
  }

  private void populateItemsVertically(List<GItem> items) {
    int index = 0;
    for (int col = 0; col < totalCols; col++) {
      for (int row = 0; row < totalRows; row++) {
        if (index < items.size()) {
          columns[col][row] = items.get(index);
        }

        index += 1;
      }
    }
  }

  private void populateItemsHorizontally(List<GItem> items) {
    int index = 0;
    for (int row = 0; row < totalRows; row++) {
      for (int col = 0; col < totalCols; col++) {
        if (index < items.size()) {
          columns[col][row] = items.get(index);
        }

        index += 1;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void refresh() {
    int index = 0;
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getLength(); col++) {
        if (currentCol + col >= totalCols || currentRow + row >= totalRows) {
          paneItems[index] = null;
        } else {
          paneItems[index] = columns[currentCol + col][currentRow + row];
        }

        index += 1;
      }
    }

    super.refresh();
  }

  public boolean hasNextCol() {
    return currentCol + getLength() < totalCols;
  }

  public boolean hasNextRow() {
    return currentRow + getHeight() < totalRows;
  }

  public boolean hasPreviousCol() {
    return currentCol > 0;
  }

  public boolean hasPreviousRow() {
    return currentRow > 0;
  }

  public void nextCol(int offset) {
    if (offset <= 0) {
      throw new IllegalArgumentException("Unable to skip 0 or less cols");
    }

    setCol(currentCol + offset);
  }

  public void nextRow(int offset) {
    if (offset <= 0) {
      throw new IllegalArgumentException("Unable to skip 0 or less rows");
    }

    setRow(currentRow + offset);
  }

  public void setCol(int col) {
    this.currentCol = col;
  }

  public void setRow(int row) {
    this.currentRow = row;
  }

  public void nextCol() {
    nextCol(1);
  }

  public void nextRow() {
    nextRow(1);
  }

  public void previousCol(int offset) {
    if (offset <= 0) {
      throw new IllegalArgumentException("Unable to skip 0 or less cols");
    }

    currentCol -= offset;
  }

  public void previousRow(int offset) {
    if (offset <= 0) {
      throw new IllegalArgumentException("Unable to skip 0 or less rows");
    }

    currentRow -= offset;
  }

  public void previousCol() {
    previousCol(1);
  }

  public void previousRow() {
    previousRow(1);
  }
}
