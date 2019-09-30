package de.pxav.kelp.configuration.type;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Injector;

import de.pxav.kelp.configuration.*;

import java.io.*;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class PropertiesConfigurationType implements ConfigurationType {

  private final Injector injector;
  private final KelpFileUtils fileUtils;

  @Inject
  public PropertiesConfigurationType(Injector injector,
                                     KelpFileUtils fileUtils) {
    this.injector = injector;
    this.fileUtils = fileUtils;
  }

  /**
   * The appendix of a properties file name.
   * @return The corresponding file extension.
   */
  @Override
  public String fileExtension() {
    return ".properties";
  }

  /**
   * This method stores all attributes given in the collection
   * into the file which corresponds to the configuration.
   *
   * @param attributes The attributes you want to save.
   * @param configurationClass The class of the configuration
   *                          (has to inherit from {@code KelpConfiguration})
   * @see KelpConfiguration
   */
  @Override
  public void saveAttributes(Collection<ConfigurationAttribute> attributes,
                             Class<? extends KelpConfiguration> configurationClass) {
    Configuration configurationAnnotation = configurationClass.getAnnotation(Configuration.class);

    File file = new File(configurationAnnotation.path(), configurationAnnotation.name() + this.fileExtension());

    try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {

      // load a properties file at given path
      // and put all attributes in it.
      Properties properties = new Properties();
      attributes.forEach(current -> {
        properties.setProperty(current.getKey(), current.getValue().toString());
      });

      // finally save the file
      properties.store(fileOutputStream, null);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Reads the file and loads all entries into the configuration's cache
   * which is located in the corresponding class.
   *
   * This method also converts the strings saved in the properties file
   * to the desired java data types which are taken from the default values
   * of the entry. That means: If your default value is an integer,
   * the value written in the config will be converted to an integer if possible as well.
   *
   * @param configurationClass The class of your configuration.
   * @return A collection of the attributes that have been loaded.
   */
  @Override
  public Collection<ConfigurationAttribute> loadAttributes(Class<? extends KelpConfiguration> configurationClass) {
    Configuration configurationAnnotation = configurationClass.getAnnotation(Configuration.class);
    KelpConfiguration instance = injector.getInstance(configurationClass);

    File file = new File(configurationAnnotation.path(), configurationAnnotation.name() + this.fileExtension());

    // check if the file exists
    if (fileUtils.createIfNotExists(file)) {
      // if not, create a new one
      // and write the given default values into it.
      instance.defineDefaults();
      saveAttributes(instance.defaultValues, configurationClass);
      return instance.defaultValues;
    }

    try(FileInputStream fileInputStream = new FileInputStream(file)) {

      // load properties file at given path
      Properties properties = new Properties();
      properties.load(fileInputStream);

      // the following code paragraph saves which keys of the
      // default values expect which data type and saves the
      // result in a map.
      Map<String, Class<?>> valueTypes = Maps.newHashMap();
      instance.defineDefaults();
      instance.defaultValues.forEach(current -> {
        if (current.getValue() instanceof Integer)
          valueTypes.put(current.getKey(), int.class);
        else if (current.getValue() instanceof String)
          valueTypes.put(current.getKey(), String.class);
        else if (current.getValue() instanceof Boolean)
          valueTypes.put(current.getKey(), boolean.class);
        else if (current.getValue() instanceof Float)
          valueTypes.put(current.getKey(), float.class);
        else if (current.getValue() instanceof Double)
          valueTypes.put(current.getKey(), double.class);
        else if (current.getValue() instanceof Long)
          valueTypes.put(current.getKey(), long.class);
        else if (current.getValue() instanceof Character)
          valueTypes.put(current.getKey(), char.class);
      });
      instance.defaultValues.clear();

      // iterate all properties in the file
      // and convert the data type if needed.
      properties.forEach((key, value) -> {
        ConfigurationAttribute configurationAttribute = new ConfigurationAttribute();
        configurationAttribute.setKey(key.toString());
        Class<?> valueType = valueTypes.get(key.toString());
        if (valueType == String.class)
           configurationAttribute.setValue(value.toString());
        if (valueType == boolean.class)
           configurationAttribute.setValue(Boolean.parseBoolean(value.toString()));
        if (valueType == int.class)
           configurationAttribute.setValue(Integer.parseInt(value.toString()));
        if (valueType == float.class)
           configurationAttribute.setValue(Float.parseFloat(value.toString()));
        if (valueType == long.class)
          configurationAttribute.setValue(Long.parseLong(value.toString()));
        if (valueType == double.class)
          configurationAttribute.setValue(Double.parseDouble(value.toString()));
        if (valueType == char.class)
          configurationAttribute.setValue(value.toString().charAt(0));

        // directly commit the new entry to the configuration class.
        instance.defaultValues.add(configurationAttribute);
      });

    } catch (IOException e) {
      e.printStackTrace();
    }

    // finally return the results.
    return instance.defaultValues;
  }

}
