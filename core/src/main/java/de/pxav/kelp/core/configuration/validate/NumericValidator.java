package de.pxav.kelp.core.configuration.validate;

@FunctionalInterface
public interface NumericValidator extends ConfigValidator {

  // possible to handle multiple number types?

  static NumericValidator nonNegative() {
    return value1 -> {
      if (value1 < 0) {
        return "Value may not be negative!";
      }
      return null;
    };
  }

  String validate(double value);

}
