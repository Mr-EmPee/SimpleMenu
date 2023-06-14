package ml.empee.simplemenu.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for applying masks to matrix of items
 */

@RequiredArgsConstructor
public class Mask {

  private final String[] mask;

  /**
   * Apply the mask to the given item matrix
   */
  public GItem[][] applyMask(@NonNull GItem[][] columns, boolean vertical) {
    List<List<GItem>> items = new ArrayList<>(
        Arrays.stream(columns)
            .map(l -> (List<GItem>) new ArrayList(Arrays.asList(l)))
            .toList()
    );

    items.forEach(c -> c.removeIf(Objects::isNull));

    if (items.size() == 0) {
      return columns;
    }

    if (vertical) {
      applyMaskVertically(items, mask);
    } else {
      applyMaskHorizontally(items, mask);
    }

    return items.stream()
        .map(c -> c.toArray(new GItem[items.get(0).size()]))
        .toList().toArray(new GItem[0][0]);
  }

  private static void applyMaskHorizontally(List<List<GItem>> items, String[] mask) {
    for (int row = 0; row < items.get(0).size(); row++) {
      for (int col = 0; col < items.size(); col++) {
        String maskLine = mask[row % mask.length];
        if (maskLine.charAt(col % maskLine.length()) != '0') {
          continue;
        }

        shiftRight(items, col, row);
      }
    }
  }

  private void applyMaskVertically(List<List<GItem>> items, String[] mask) {
    for (int col = 0; col < items.size(); col++) {
      List<GItem> column = items.get(col);
      for (int row = 0; row < column.size(); row++) {
        String maskLine = mask[row % mask.length];
        if (maskLine.charAt(col % maskLine.length()) != '0') {
          continue;
        }

        shiftDown(items, col, row);
      }
    }
  }

  private static void shiftDown(List<List<GItem>> items, int targetCol, int targetRow) {
    int col = items.size() - 1;
    if (col == 0 || items.get(col).size() == items.get(col - 1).size()) {
      items.add(new ArrayList<>());
      col += 1;
    }

    items.get(col).add(null);
    int row = items.get(col).size() - 1;
    while (col != targetCol || row != targetRow) {
      if (row == 0) {
        col -= 1;
        row = items.get(col).size() - 1;

        GItem item = items.get(col).get(row);
        items.get(col + 1).set(0, item);
      } else {
        row -= 1;

        GItem item = items.get(col).get(row);
        items.get(col).set(row + 1, item);
      }
    }

    items.get(targetCol).set(targetRow, null);
  }

  private static void shiftRight(List<List<GItem>> items, int targetCol, int targetRow) {
    int col = findSmallestCol(items);
    items.get(col).add(null);

    int row = items.get(col).size() - 1;
    while (col != targetCol || row != targetRow) {
      if (col == 0) {
        col = items.size() - 1;
        row -= 1;

        GItem item = items.get(col).get(row);
        items.get(0).set(row + 1, item);
      } else {
        col -= 1;

        GItem item = items.get(col).get(row);
        items.get(col + 1).set(row, item);
      }
    }

    items.get(targetCol).set(targetRow, null);
  }

  /**
   * @return the col index with the smallest number of rows, 0 if not found
   */
  private static int findSmallestCol(List<List<GItem>> items) {
    int col = items.size() - 1;
    while (col > 0) {
      if (items.get(col).size() < items.get(col - 1).size()) {
        break;
      }

      col -= 1;
    }

    return col;
  }

}
