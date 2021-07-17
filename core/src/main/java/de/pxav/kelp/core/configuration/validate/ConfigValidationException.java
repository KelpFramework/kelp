package de.pxav.kelp.core.configuration.validate;

public class ConfigValidationException extends Exception {

  private String key;

  public ConfigValidationException(String key, String message) {
    super(message);
    this.key = key;
  }

  public void logValidationError() {
    // todo: connect with logger system introduced in 0.4.1
    System.out.println("Config value validation of " + this.key + " failed: " + getMessage());
  }

}
