package ml.empee.simplemenu.adapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

public class AdapterUpdateSign extends PacketAdapter {

  public static final PacketType TYPE = PacketType.Play.Client.UPDATE_SIGN;

  public AdapterUpdateSign() {
    super(new PacketContainer(TYPE), TYPE);
    handle.getModifier().writeDefaults();
  }

  public AdapterUpdateSign(PacketContainer packet) {
    super(packet, TYPE);
  }

  /**
   * @return The current sign Location
   */
  public BlockPosition getLocation() {
    return handle.getBlockPositionModifier().read(0);
  }

  /**
   * Retrieve this sign's lines of text.
   */
  public String[] getLines() {
    return handle.getStringArrays().read(0);
  }

}
