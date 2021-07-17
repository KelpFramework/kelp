package de.pxav.kelp.core.configuration;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.common.KelpFileUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.Map;

public abstract class KelpConfiguration {

  protected Map<String, ConfigurationValue> configValues = Maps.newConcurrentMap();

  protected abstract File configFile();

  protected abstract String resourceTemplatePath();

  public void load() {
    System.out.println("loading config file template " + resourceTemplatePath());
    File configFile = configFile();
    File configFolder = configFile.getParentFile();

    if (!configFolder.exists()) {
      configFolder.mkdirs();
    }

    // copy template file from resources folder if no config has existed before.
    if (configFile.exists()) {
      System.out.println("Config file already exists, checking for updates...");
      YamlConfiguration existingConfig = YamlConfiguration.loadConfiguration(configFile);

      InputStream resource = KelpFileUtils.getResource(resourceTemplatePath());
      if (resource == null) {
        System.out.println("config template does not exist, skipping..");
        return;
      }

      InputStreamReader inputStreamReader = new InputStreamReader(resource);
      YamlConfiguration templateConfig = YamlConfiguration.loadConfiguration(inputStreamReader);

      ConfigurationPatcher patcher = ConfigurationPatcher.create(templateConfig, existingConfig);

      if (!patcher.patchNecessary()) {
        System.out.println("no patch necessary");
        return;
      }

      patcher.backupValues();
      patcher.patch(resourceTemplatePath(), configFile);

    } else {
      System.out.println("config file does not exist, creating new one...");
      KelpFileUtils.saveResource(resourceTemplatePath(), configFile);
    }
  }

  public Object getValue(String key) {
    return configValues.get(key);
  }

  public String getStringValue(String key) {
    return String.valueOf(configValues.get(key).value());
  }

  public int getIntValue(String key) {
    return Integer.parseInt(getStringValue(key));
  }

}
