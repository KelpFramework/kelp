package de.pxav.kelp.core.application;

import com.google.inject.Injector;

/**
 * This class represents a kelp application.
 * When you want to create a new Kelp application you have
 * to make your main class inherit from this class and
 * annotate it with {@code NewKelpApplication}.
 *
 * It provides essential methods which are executed
 * during the lifetime of the server (on load, enable, ...).
 * You can simply override them and put your code in it,
 * but none of them is required. So just choose the methods
 * you need.
 *
 * @author pxav
 * @see NewKelpApplication
 */
public class KelpApplication {

  private KelpInformation information;
  private Injector injector;

  public void init(KelpInformation information, Injector injector) {
    this.information = information;
    this.injector = injector;
  }

  /**
   * This method is called when the application is loaded,
   */
  public void onLoad() {}

  /**
   * This method is called when the application is enabled
   */
  public void onEnable() {}

  /**
   * This method is called when the application is disabled
   */
  public void onDisable() {}

  /**
   * Returns a {@code KelpInformation} object containing
   * important information about the plugin (like name, version, ...)
   *
   * @return The final {@code KelpInformation} object.
   * @see KelpInformation
   */
  public KelpInformation getInformation() {
    return information;
  }

  /**
   * Retrieves an instance of the given class
   * from the guice injector.
   *
   * @param clazz The class you want to get an instance of.
   * @param <T> Type
   * @return An instance of the desired class.
   */
  public <T> T getInstance(Class<T> clazz) {
    return injector.getInstance(clazz);
  }

  /**
   * @return The guice injector.
   * @see Injector
   * @see de.pxav.kelp.core.application.inject.SimpleBinderModule
   * @see de.pxav.kelp.core.application.inject.VersionBinderModule
   */
  public Injector getInjector() {
    return injector;
  }

}
