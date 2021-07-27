package de.pxav.kelp.core.configuration.parse;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.common.KelpFileUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigurationPatcher {

  private final YamlConfiguration templateConfig;
  private final YamlConfiguration existingConfig;

  private Map<String, Object> valueBackup;

  public static ConfigurationPatcher create(YamlConfiguration templateFile, YamlConfiguration patchedFile) {
    return new ConfigurationPatcher(templateFile, patchedFile);
  }

  private ConfigurationPatcher(YamlConfiguration templateConfig, YamlConfiguration existingConfig) {
    this.templateConfig = templateConfig;
    this.existingConfig = existingConfig;
    this.valueBackup = Maps.newHashMap();
  }

  public boolean patchNecessary() {
    Set<String> templateKeys = templateConfig.getKeys(true);

    if (existingConfig.getKeys(true).containsAll(templateKeys)) {
      System.out.println("Files are equal. No patching required!");
      return false;
    }

    return true;
  }

  public void backupValues() {
    this.valueBackup = existingConfig.getValues(true);
  }

  public void patch(String resourcePath, File outputFile) {
    KelpFileUtils.saveResource(resourcePath, outputFile);

    List<String> patchedContents = ConfigurationParser
      .create(outputFile, this.valueBackup)
      .parseContents();

    ConfigurationDumper.create(outputFile).dump(patchedContents);

  }

}
