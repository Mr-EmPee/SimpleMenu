package ml.empee.simplemenu.model.menus;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedRegistrable;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ml.empee.simplemenu.handlers.SignHandler;
import ml.empee.simplemenu.utility.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A sign editor menu
 */

@RequiredArgsConstructor
public class SignMenu implements Menu {

  private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

  @Getter
  protected final Player player;

  @Getter
  @Setter
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
    Location fakeSignLoc = player.getLocation().subtract(0, 5, 0);

    PacketContainer spawnSignPacket = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
    spawnSignPacket.getBlockData().write(0, WrappedBlockData.createData(Material.valueOf("OAK_SIGN")));
    spawnSignPacket.getBlockPositionModifier().write(
        0, new BlockPosition(fakeSignLoc.getBlockX(), fakeSignLoc.getBlockY(), fakeSignLoc.getBlockZ())
    );

    //Set default sign text
    PacketContainer signDataPacket = protocolManager.createPacket(PacketType.Play.Server.TILE_ENTITY_DATA);
    NbtCompound signNBT = (NbtCompound) signDataPacket.getNbtModifier().read(0);
    signDataPacket.getBlockPositionModifier().write(
        0, new BlockPosition(fakeSignLoc.getBlockX(), fakeSignLoc.getBlockY(), fakeSignLoc.getBlockZ())
    );

    for (int line = 0; line < 4; line++) {
      signNBT.put("Text" + (line + 1), "{\"text\":\"" + (text[line] == null ? "" : text[line]) + "\"}");
    }

    signNBT.put("x", fakeSignLoc.getBlockX());
    signNBT.put("y", fakeSignLoc.getBlockY());
    signNBT.put("z", fakeSignLoc.getBlockZ());
    signNBT.put("id", "minecraft:sign");

    signDataPacket.getBlockEntityTypeModifier().write(0, WrappedRegistrable.blockEntityType("sign"));
    signDataPacket.getNbtModifier().write(0, signNBT);


    PacketContainer openSignPacket = protocolManager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
    openSignPacket.getBlockPositionModifier().write(
        0, new BlockPosition(fakeSignLoc.getBlockX(), fakeSignLoc.getBlockY(), fakeSignLoc.getBlockZ())
    );

    protocolManager.sendServerPacket(player, spawnSignPacket, false);
    protocolManager.sendServerPacket(player, signDataPacket, false);
    protocolManager.sendServerPacket(player, openSignPacket, false);
  }

  private void openBefore1_9() {
    Location fakeSignLoc = player.getLocation().subtract(0, 5, 0);

    PacketContainer spawnSignPacket = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
    spawnSignPacket.getBlockData().write(0, WrappedBlockData.createData(Material.valueOf("SIGN_POST")));
    spawnSignPacket.getBlockPositionModifier().write(
        0, new BlockPosition(fakeSignLoc.getBlockX(), fakeSignLoc.getBlockY(), fakeSignLoc.getBlockZ())
    );

    //Set default sign text
    PacketContainer editSignPacket = protocolManager.createPacket(PacketType.Play.Server.UPDATE_SIGN);
    WrappedChatComponent[] text = new WrappedChatComponent[4];
    for (int i = 0; i < 4; i++) {
      text[i] = WrappedChatComponent.fromLegacyText(this.text[i]);
    }

    editSignPacket.getChatComponentArrays().write(0, text);
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

  public void onClose() {
  }

}
