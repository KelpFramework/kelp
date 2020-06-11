package de.pxav.kelp.core.configuration;

import java.text.MessageFormat;

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

  private String[] replacements = new String[] {};

  public ConfigurationAttribute(String key, Object value, String... replacements) {
    this.key = key;
    this.value = value;
    this.replacements = replacements;
  }

  public ConfigurationAttribute() {}

  public Object getValue() {
    return value;
  }

  public String getKey() {
    return key;
  }

  public String[] getReplacements() {
    return replacements;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public void setReplacements(String[] replacements) {
    this.replacements = replacements;
  }
}
