package de.pxav.kelp.configuration;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Injector;
import de.pxav.kelp.configuration.type.ConfigurationType;

import java.util.Collection;

/**
 * <p>
 *    This is the template class for all configuration classes.
 *    If you want to create a new configuration your class has to
 *    extend this class which provides methods to query or update
 *    your config entries.
 * </p>
 *
 * @author pxav
 */
public abstract class KelpConfiguration {

  // google guice injector to fetch instances of classes
  @Inject private Injector injector;

  // this list contains all your configuration attributes
  public Collection<ConfigurationAttribute> defaultValues = Lists.newArrayList();

  /**
   * Define the default values for your configuration attributes.
   * In this method you can simply add entries to the {@code defaultValues}
   * collection to define which keys and values should be written to the file
   * when it's created for the first time.
   *
   * You also define which keys you want to have at all. You cannot
   * add/remove keys later.
   */
  public abstract void defineDefaults();

  /**
   * Save the changes. If you have modified the {@code defaultValues} collection
   * using the {@code #update} method for example you might want to save your
   * changes and write them to the corresponding file to be available after
   * a server restart as well.
   */
  public void save() {
    Configuration annotation = this.getClass().getAnnotation(Configuration.class);
    ConfigurationType configurationType = injector.getInstance(annotation.type());
    configurationType.saveAttributes(defaultValues, this.getClass());
  }

  /**
   * Iterates the {@code defaultValues} collection and searches for
   * an attribute that has the desired key.
   *
   * @param key The key you want to search for.
   * @return Returns the {@code ConfigurationAttribute} with the given key.
   *         If this does not exist {@code null} will be returned.
   * @see ConfigurationAttribute
   */
  public ConfigurationAttribute getByKey(String key) {
    return defaultValues
            .stream()
            .filter(attribute -> attribute.getKey().equalsIgnoreCase(key))
            .findFirst()
            .orElse(null);
  }

  /**
   * Get the value which is assigned to the given key
   * from the cache. This method only works if the
   * value really is an integer.
   *
   * @param key The key of the value you want to get.
   * @return The value object converted to an integer.
   */
  public int getIntValue(String key) {
    ConfigurationAttribute attribute = this.getByKey(key);
    return (int) attribute.getValue();
  }

  /**
   * Get the value which is assigned to the given key
   * from the cache. This method only works if the
   * value really is a string.
   *
   * @param key The key of the value you want to get.
   * @return The value object converted to a string.
   */
  public String getStringValue(String key) {
    ConfigurationAttribute attribute = this.getByKey(key);
    return (String) attribute.getValue();
  }

  /**
   * Get the value which is assigned to the given key
   * from the cache. This method only works if the
   * value really is a boolean.
   *
   * @param key The key of the value you want to get.
   * @return The value object converted to a boolean.
   */
  public boolean getBooleanValue(String key) {
    ConfigurationAttribute attribute = this.getByKey(key);
    return (boolean) attribute.getValue();
  }

  /**
   * Get the value which is assigned to the given key
   * from the cache. This method only works if the
   * value really is a float.
   *
   * @param key The key of the value you want to get.
   * @return The value object converted to a float.
   */
  public float getFloatValue(String key) {
    ConfigurationAttribute attribute = this.getByKey(key);
    return (float) attribute.getValue();
  }

  /**
   * Get the value which is assigned to the given key
   * from the cache. This method only works if the
   * value really is a double.
   *
   * @param key The key of the value you want to get.
   * @return The value object converted to a double.
   */
  public double getDoubleValue(String key) {
    ConfigurationAttribute attribute = this.getByKey(key);
    return (double) attribute.getValue();
  }

  /**
   * Get the value which is assigned to the given key
   * from the cache. This method only works if the
   * value really is a long.
   *
   * @param key The key of the value you want to get.
   * @return The value object converted to a long.
   */
  public long getLongValue(String key) {
    ConfigurationAttribute attribute = this.getByKey(key);
    return (long) attribute.getValue();
  }

  /**
   * Updates the attribute value of the given key.
   * This method only updates the value within the cache.
   * If you want to save your changes to the file you
   * have to execute {@code #save()} afterwards or directly
   * use {@code #updateAndSave(key, value)}
   *
   * @param key The key of the attribute you want to update.
   * @param value The new value for the given key.
   */
  public void update(String key, Object value) {
    Preconditions.checkNotNull(getByKey(key));
    defaultValues.remove(getByKey(key));
    defaultValues.add(new ConfigurationAttribute(key, value));
  }

  /**
   * Updates the attribute value of the given key.
   * This method also saves the changes to the corresponding file.
   * If you don't want this to happen, but only update within
   * the cache you might want to use {@code #update(key, value)}.
   *
   * @param key The key of the attribute you want to update.
   * @param value The new value for the given key.
   */
  public void updateAndSave(String key, Object value) {
    update(key, value);
    save();
  }

}
