package ml.empee.simplemenu.model.panes;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ml.empee.simplemenu.model.GItem;
import ml.empee.simplemenu.model.Slot;
import ml.empee.simplemenu.model.masks.GapMask;

class ScrollPaneTest extends Assertions {

  private List<GItem> getItems() {
    return List.of(
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty(),
        GItem.empty());
  }

  @Test
  void shouldPopulatePaneHorizontally() {
    var items = getItems();
    var pane = ScrollPane.horizontal(3, 3, Slot.of(0, 0), 6);
    pane.addAll(items);

    var content = pane.content;
    assertEquals(items.get(0), content[0]);
    assertEquals(items.get(1), content[1]);
    assertEquals(items.get(2), content[2]);
    assertEquals(items.get(6), content[3]);
    assertEquals(items.get(7), content[4]);
    assertEquals(items.get(8), content[5]);
    assertEquals(items.get(12), content[6]);
    assertEquals(items.get(13), content[7]);
    assertEquals(items.get(14), content[8]);

    pane.setColOffset(2);
    pane.setRowOffset(1);

    assertEquals(items.get(8), content[0]);
    assertEquals(items.get(9), content[1]);
    assertEquals(items.get(10), content[2]);
    assertEquals(items.get(14), content[3]);
    assertEquals(items.get(15), content[4]);
    assertEquals(items.get(16), content[5]);
    assertEquals(items.get(20), content[6]);
    assertEquals(items.get(21), content[7]);
    assertEquals(items.get(22), content[8]);

    pane.setColOffset(4);
    pane.setRowOffset(3);

    assertEquals(items.get(22), content[0]);
    assertEquals(items.get(23), content[1]);
    assertNull(content[2]);
    assertEquals(items.get(28), content[3]);
    assertEquals(items.get(29), content[4]);
    assertNull(content[5]);
    assertNull(content[6]);
    assertNull(content[7]);
    assertNull(content[8]);
  }

  @Test
  void shouldPopulatePaneVertically() {
    var items = getItems();
    var pane = ScrollPane.vertical(3, 3, Slot.of(0, 0), 6);
    pane.addAll(items);

    var content = pane.content;
    assertEquals(items.get(0), content[0]);
    assertEquals(items.get(6), content[1]);
    assertEquals(items.get(12), content[2]);
    assertEquals(items.get(1), content[3]);
    assertEquals(items.get(7), content[4]);
    assertEquals(items.get(13), content[5]);
    assertEquals(items.get(2), content[6]);
    assertEquals(items.get(8), content[7]);
    assertEquals(items.get(14), content[8]);

    pane.setColOffset(2);
    pane.setRowOffset(1);

    assertEquals(items.get(13), content[0]);
    assertEquals(items.get(19), content[1]);
    assertEquals(items.get(25), content[2]);
    assertEquals(items.get(14), content[3]);
    assertEquals(items.get(20), content[4]);
    assertEquals(items.get(26), content[5]);
    assertEquals(items.get(15), content[6]);
    assertEquals(items.get(21), content[7]);
    assertEquals(items.get(27), content[8]);

    pane.setColOffset(3);
    pane.setRowOffset(4);

    assertEquals(items.get(22), content[0]);
    assertEquals(items.get(28), content[1]);
    assertNull(content[2]);
    assertEquals(items.get(23), content[3]);
    assertEquals(items.get(29), content[4]);
    assertNull(content[5]);
    assertNull(content[6]);
    assertNull(content[7]);
    assertNull(content[8]);
  }

  @Test
  void shouldApplyMaskHorizontally() {
    var items = getItems();
    var pane = ScrollPane.horizontal(3, 3, Slot.of(0, 0), 6);
    pane.addAll(items);

    pane.setMask(GapMask.horizontal("011110", "100001"));

    var content = pane.content;
    assertNull(content[0]);
    assertEquals(items.get(0), content[1]);
    assertEquals(items.get(1), content[2]);
    assertEquals(items.get(4), content[3]);
    assertNull(content[4]);
    assertNull(content[5]);
    assertNull(content[6]);
    assertEquals(items.get(6), content[7]);
    assertEquals(items.get(7), content[8]);
  }

  @Test
  void shouldApplyMaskVertically() {
    var items = getItems();
    var pane = ScrollPane.vertical(3, 3, Slot.of(0, 0), 6);
    pane.addAll(items);

    pane.setMask(GapMask.vertical(
        "01",
                "10",
                "10",
                "10",
                "10",
                "01"
    ));

    var content = pane.content;
    assertNull(content[0]);
    assertEquals(items.get(4), content[1]);
    assertNull(content[2]);
    assertEquals(items.get(0), content[3]);
    assertNull(content[4]);
    assertEquals(items.get(6), content[5]);
    assertEquals(items.get(1), content[6]);
    assertNull(content[7]);
    assertEquals(items.get(7), content[8]);
  }

}
