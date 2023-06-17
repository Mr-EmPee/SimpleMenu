package ml.empee.simplemenu.adapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

/**
 * PlayServerOpenSignEditor
 */

public class AdapterOpenSignEditor extends PacketAdapter {

  public static final PacketType TYPE = PacketType.Play.Server.OPEN_SIGN_EDITOR;

  public AdapterOpenSignEditor() {
    super(new PacketContainer(TYPE), TYPE);
  }

  public AdapterOpenSignEditor(PacketContainer packet) {
    super(packet, TYPE);
  }

  /**
   * @return The sign Location
   */
  public BlockPosition getLocation() {
    return handle.getBlockPositionModifier().read(0);
  }

  /**
   * Set the sign Location.
   */
  public void setLocation(BlockPosition value) {
    handle.getBlockPositionModifier().write(0, value);
  }

}
