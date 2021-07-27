package de.pxav.kelp.core.configuration;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.common.KelpFileUtils;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.List;
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

    YamlConfiguration existingConfig = YamlConfiguration.loadConfiguration(configFile);

    // copy template file from resources folder if no config has existed before.
    if (configFile.exists()) {
      checkPatcher(configFile, existingConfig);
    } else {
      System.out.println("config file does not exist, creating new one...");
      KelpFileUtils.saveResource(resourceTemplatePath(), configFile);
    }

    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);

    yamlConfiguration.getValues(true).forEach((key, value) -> {
      // keys that introduce a new section are represented
      // as 'MemorySection', but they don't contain any relevant
      // values so we don't have to save them
      if (!(value instanceof MemorySection)) {
        this.configValues.put(key, () -> value);
      }
    });

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

  public double getDoubleValue(String key) {
    return Double.parseDouble(getStringValue(key));
  }

  public float getFloatValue(String key) {
    return Float.parseFloat(getStringValue(key));
  }

  public long getLongValue(String key) {
    return Long.parseLong(getStringValue(key));
  }

  public char getCharValue(String key) {
    return getStringValue(key).charAt(0);
  }

  public byte getByteValue(String key) {
    return Byte.parseByte(getStringValue(key));
  }

  public List<String> getStringList(String key) {
    return (List<String>) this.configValues.get(key);
  }

  public List<?> getList(String key) {
    return (List<?>) this.configValues.get(key);
  }

  public List<Integer> getIntList(String key) {
    return (List<Integer>) this.configValues.get(key);
  }

  private void checkPatcher(File configFile, YamlConfiguration existingConfig) {
    System.out.println("Config file already exists, checking for updates...");


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
  }

}
