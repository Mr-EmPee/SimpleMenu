package ml.empee.simplemenu.model.masks;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MaskTest extends Assertions {

  @Test
  void shouldInflateItems() {
    var mask = GapMask.of("101");
    var items = List.of("a", "b", "c", "d", "e");

    var result = mask.apply(items);

    assertIterableEquals(Arrays.asList("a", null, "b", "c", null, "d", "e"), result);
  }

  @Test
  void shouldBuildMask() {
    var mask = GapMask.vertical(
        "011",
        "100",
        "011");

    assertEquals("010101101", mask.getMask());

    mask = GapMask.horizontal(
        "011",
        "100",
        "011");

    assertEquals("011100011", mask.getMask());
  }

}
