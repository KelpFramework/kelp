package de.pxav.kelp.core.configuration.validate;

public interface ConfigValidator {

  default String validate(Object object) {
    if (object instanceof Integer) {
      return ((IntegerValidator)this).validate((int) object);
    }
    return null;
  }

}
