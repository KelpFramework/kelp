package de.pxav.kelp.core.configuration.validate;

@FunctionalInterface
public interface IntegerValidator extends ConfigValidator {

  static IntegerValidator nonNegative() {
    return value -> {
      if (value < 0) {
        return "Value may not be negative!";
      }
      return null;
    };
  }

  static IntegerValidator failIfGreaterThan(int than) {
    return value -> {
      if (value > than) {
        return
          "Configuration value '" + value + "' is too big. " +
            "Should not be greater than " + than
        ;
      }
      return null;
    };
  }

  static IntegerValidator passIfGreaterThan(int than) {
    return value -> {
      if (!(value > than)) {
        return
          "Configuration value '" + value + "' is too small. " +
            "Should not be smaller than " + than
        ;
      }
      return null;
    };
  }

  String validate(int value);

}
