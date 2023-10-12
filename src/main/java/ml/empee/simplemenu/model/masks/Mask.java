package ml.empee.simplemenu.model.masks;

import java.util.List;

/**
 * Mask that allows you to specify the position of the items
 */

public interface Mask {

  /**
   * Given an ordered list of items, it returns a list of items with the specified position
   */
  <K> List<K> apply(List<K> items);

}
