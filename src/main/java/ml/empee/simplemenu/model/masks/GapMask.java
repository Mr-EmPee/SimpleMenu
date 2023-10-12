package ml.empee.simplemenu.model.masks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;

/**
 * A mask that can be applied to a list of items to create a gap between them
 */

@RequiredArgsConstructor(staticName = "of")
public class GapMask implements Mask {

  private final String mask;

  /**
   * Creates a horizontal mask from a list of strings
   */
  public static GapMask horizontal(String... mask) {
    return new GapMask(String.join("", mask));
  }

  /**
   * Creates a vertical mask from a list of strings
   */
  public static GapMask vertical(String... mask) {
    StringBuilder builder = new StringBuilder();

    int maxLength = Stream.of(mask).mapToInt(String::length).max().orElse(0);
    for (int col = 0; col < maxLength; col++) {
      for (int row = 0; row < mask.length; row++) {
        if (col >= mask[row].length()) {
          break;
        }

        builder.append(mask[row].charAt(col));
      }
    }

    return new GapMask(builder.toString());
  }

  /**
   * @{inheritDoc}
   */
  public <K> List<K> apply(List<K> items) {
    var result = new ArrayList<K>();

    int itemIndex = 0;
    int maskIndex = 0;
    while (itemIndex < items.size()) {
      if (mask.charAt(maskIndex) == '1') {
        result.add(items.get(itemIndex));
        itemIndex++;
      } else {
        result.add(null);
      }

      maskIndex = (maskIndex + 1) % mask.length();
    }

    return result;
  }

  String getMask() {
    return mask;
  }
}
