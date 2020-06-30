package de.pxav.kelp.testing.config;

import com.google.inject.Singleton;
import de.pxav.kelp.core.configuration.Configuration;
import de.pxav.kelp.core.configuration.KelpConfiguration;
import de.pxav.kelp.core.configuration.type.PropertiesConfigurationType;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
@Configuration(
  path = "kelp_plugins//testing_module",
  name = "debugCommands",
  type = PropertiesConfigurationType.class
)
public class DebugCommandConfig extends KelpConfiguration {

  @Override
  public void defineDefaults() {
    add("general.prefix", "§8[§2KelpDebugger§8]");
    add("permission.kmaterial", "debugger.kmaterial");
    add("msg.noPermission", "{0} §cYou do not have enough permissions to do that.", getPrefix());
    add("msg.noPlayer", "{0} You have to be a player for this action.", getPrefix());
    add("msg.materialHelp", "{0} §8§m-----------------------------" +
      "\n {0} §a/kmaterial give kelp <KelpMaterial>"
        + "\n{0} §a/kmaterial give bukkit <BukkitMaterial>"
      , getPrefix());
    add("msg.givenKelpMaterial", "{0} §7Given KelpItem with KelpMaterial §a%material% §7to you.", getPrefix());
    add("msg.givenBukkitMaterial", "{0} §7Given item with bukkit material §a%material% §7to you.", getPrefix());
  }

  private String getPrefix() {
    return getStringValue("general.prefix");
  }

}
