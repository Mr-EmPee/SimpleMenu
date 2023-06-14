package ml.empee.simplemenu.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Slot
 */

@Value
@RequiredArgsConstructor(staticName = "of")
public class Slot {
  int col;
  int row;
}
