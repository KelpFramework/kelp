package de.pxav.kelp.core.configuration;

public interface ConfigurationValue {

  default String[] comments() {
    return new String[] {};
  }

  Object value();

}
