package ml.empee.simplemenu.handlers;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import lombok.RequiredArgsConstructor;
import ml.empee.simplemenu.model.menus.SignMenu;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handle sign operations
 */

@RequiredArgsConstructor
public class SignHandler {

  private static final Map<UUID, SignMenu> signs = new HashMap<>();

  private final PacketListener packetListener = new PacketListener();
  private final JavaPlugin plugin;

  public void registerProtocolLibListeners() {
    ProtocolLibrary.getProtocolManager().addPacketListener(packetListener);
  }

  public void unregisterProtoLibListeners() {
    ProtocolLibrary.getProtocolManager().removePacketListener(packetListener);
  }

  public void onSignClose(SignMenu menu, String[] outputText) {
    menu.onClose(outputText);
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
      onSignClose(sign, event.getPacket().getStringArrays().read(0));
    }
  }

}
