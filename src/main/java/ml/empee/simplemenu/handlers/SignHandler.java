package ml.empee.simplemenu.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import ml.empee.simplemenu.model.menus.SignMenu;
import ml.empee.simplemenu.utility.ServerVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handle sign operations
 */

//TODO: Refactoring!

public class SignHandler {

  private static final Map<UUID, SignMenu> signs = new HashMap<>();

  private final PacketListener packetListener;
  private final JavaPlugin plugin;

  public SignHandler(JavaPlugin plugin) {
    this.plugin = plugin;
    packetListener = new PacketListener();
  }

  public void registerProtocolLibListeners() {
    ProtocolLibrary.getProtocolManager().addPacketListener(packetListener);
  }

  public void unregisterProtoLibListeners() {
    ProtocolLibrary.getProtocolManager().removePacketListener(packetListener);
  }

  public void onSignClose(SignMenu menu, String[] outputText) {
    replaceFakeSignWithOriginalBlock(menu);

    menu.setText(outputText);
    menu.onClose();
  }

  private static void replaceFakeSignWithOriginalBlock(SignMenu menu) {
    Player player = menu.getPlayer();
    PacketContainer destroySignPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_CHANGE);
    destroySignPacket.getBlockData().write(0, WrappedBlockData.createData(player.getLocation().getBlock().getType()));
    destroySignPacket.getBlockPositionModifier().write(
        0, new BlockPosition(player.getLocation().toVector())
    );

    ProtocolLibrary.getProtocolManager().sendServerPacket(player, destroySignPacket, false);
  }

  public static void registerSign(UUID player, SignMenu menu) {
    signs.put(player, menu);
  }

  private class PacketListener extends PacketAdapter {

    public PacketListener() {
      super(SignHandler.this.plugin, PacketType.Play.Client.UPDATE_SIGN);
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
      SignMenu sign = signs.remove(event.getPlayer().getUniqueId());
      if (sign == null) {
        return;
      }

      event.setCancelled(true);

      String[] result;
      if (ServerVersion.isGreaterThan(1, 9)) {
        result = event.getPacket().getStringArrays().read(0);
      } else {
        result = new String[4];
        WrappedChatComponent[] text = event.getPacket().getChatComponentArrays().read(0);
        for (int i = 0; i < result.length; i++) {
          result[i] = text[i].getJson();
        }
      }

      Bukkit.getScheduler().runTask(plugin, () -> onSignClose(sign, result));
    }
  }

}
