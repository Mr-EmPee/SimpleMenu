package ml.empee.simplemenu;

import lombok.Getter;
import ml.empee.ioc.SimpleIoC;
import ml.empee.simplemenu.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Boot class of this plugin.
 **/

public final class SimpleMenu extends JavaPlugin {

  @Getter
  private final SimpleIoC iocContainer = new SimpleIoC(this);

  /**
   * Called when enabling the plugin
   */
  public void onEnable() {
    Logger.setPrefix("SimpleMenu");

    iocContainer.initialize("relocations");
  }

  public void onDisable() {
    iocContainer.removeAllBeans(true);
  }
}
