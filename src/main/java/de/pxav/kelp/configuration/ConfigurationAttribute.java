package de.pxav.kelp.configuration;

/**
 * A Configuration attribute basically represents
 * one single pair of a key and a value that belong
 * together.
 *
 * Examples would be:
 * "prefix": "[Kelp]"
 * "developmentMode": true
 *
 * @author pxav
 */
public class ConfigurationAttribute {

  // the key is a unique identifier which can be used to access the value.
  // it is static and cannot changed during runtime.
  private String key;

  // the value does not need to be unique and has a dynamic value, which means
  // that it can be changed at any time.
  private Object value;

  public ConfigurationAttribute(String key, Object value) {
    this.key = key;
    this.value = value;
  }

  public ConfigurationAttribute() {}

  public Object getValue() {
    return value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setValue(Object value) {
    this.value = value;
  }

}
