package ml.empee.simplemenu.model.menus;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.handlers.SignHandler;
import ml.empee.simplemenu.utility.ServerVersion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * A sign editor menu
 */

@RequiredArgsConstructor
public class SignMenu implements Menu {

  private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

  @Getter
  protected final Player player;

  @Getter
  protected String[] text = new String[4];

  @Override
  public final void open() {
    SignHandler.registerSign(player.getUniqueId(), this);

    onOpen();

    if (ServerVersion.isGreaterThan(1, 9)) {
      openAfter1_9();
    } else {
      openBefore1_9();
    }
  }

  private void openAfter1_9() {
    Location fakeSignLoc = player.getLocation().add(0, 10, 0);

    PacketContainer spawnSignPacket = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
    spawnSignPacket.getBlockPositionModifier().write(
        0, new BlockPosition(fakeSignLoc.getBlockX(), fakeSignLoc.getBlockY(), fakeSignLoc.getBlockZ())
    );

    //Set default sign text
    //TODO: Customa action for 1.19

    PacketContainer openSignPacket = protocolManager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
    openSignPacket.getBlockPositionModifier().write(
        0, new BlockPosition(fakeSignLoc.getBlockX(), fakeSignLoc.getBlockY(), fakeSignLoc.getBlockZ())
    );

    protocolManager.sendServerPacket(player, spawnSignPacket, false);
    //protocolManager.sendServerPacket(player, editSignPacket, false);
    protocolManager.sendServerPacket(player, openSignPacket, false);
  }

  private void openBefore1_9() {
    Location fakeSignLoc = player.getLocation().add(0, 10, 0);

    PacketContainer spawnSignPacket = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
    spawnSignPacket.getBlockPositionModifier().write(
        0, new BlockPosition(fakeSignLoc.getBlockX(), fakeSignLoc.getBlockY(), fakeSignLoc.getBlockZ())
    );

    //Set default sign text
    PacketContainer editSignPacket = protocolManager.createPacket(PacketType.Play.Server.UPDATE_SIGN);
    editSignPacket.getStringArrays().write(0, text);
    editSignPacket.getBlockPositionModifier().write(
        0, new BlockPosition(fakeSignLoc.getBlockX(), fakeSignLoc.getBlockY(), fakeSignLoc.getBlockZ())
    );

    PacketContainer openSignPacket = protocolManager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
    openSignPacket.getBlockPositionModifier().write(
        0, new BlockPosition(fakeSignLoc.getBlockX(), fakeSignLoc.getBlockY(), fakeSignLoc.getBlockZ())
    );

    protocolManager.sendServerPacket(player, spawnSignPacket, false);
    protocolManager.sendServerPacket(player, editSignPacket, false);
    protocolManager.sendServerPacket(player, openSignPacket, false);
  }

  public void onOpen() {

  }

  public void onClose(String[] text) {

  }

}
