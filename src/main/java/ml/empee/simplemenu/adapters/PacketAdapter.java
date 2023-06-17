package ml.empee.simplemenu.adapters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import org.bukkit.entity.Player;

//Wraps a packet

class PacketAdapter {

  // The packet we will be modifying
  protected PacketContainer handle;

  /**
   * Constructs a new strongly typed wrapper for the given packet.
   *
   * @param handle - handle to the raw packet data.
   * @param type   - the packet type.
   */
  protected PacketAdapter(PacketContainer handle, PacketType type) {
    // Make sure we're given a valid packet
    if (handle == null) {
      throw new IllegalArgumentException("Packet handle cannot be NULL.");
    }
    if (!Objects.equal(handle.getType(), type)) {
      throw new IllegalArgumentException(handle.getHandle() + " is not a packet of type " + type);
    }

    this.handle = handle;
  }

  /**
   * Retrieve a handle to the raw packet data.
   *
   * @return Raw packet data.
   */
  public PacketContainer getHandle() {
    return handle;
  }

  /**
   * Send the current packet to the given receiver.
   *
   * @param receiver - the receiver.
   * @throws RuntimeException If the packet cannot be sent.
   */
  public void sendPacket(Player receiver) {
    ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, getHandle());
  }

  /**
   * Send the current packet to all online players.
   */
  public void broadcastPacket() {
    ProtocolLibrary.getProtocolManager().broadcastServerPacket(getHandle());
  }

}
