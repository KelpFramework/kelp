package de.pxav.kelp.core.application;

import com.google.inject.Injector;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpApplication {

  private KelpInformation information;
  private Injector injector;

  public void init(KelpInformation information, Injector injector) {
    this.information = information;
    this.injector = injector;
  }

  public void onEnable() {}

  public void onLoad() {}

  public void onDisable() {}

  public KelpInformation getInformation() {
    return information;
  }

  public <T> T getInstance(Class<T> clazz) {
    return injector.getInstance(clazz);
  }

  public Injector getInjector() {
    return injector;
  }

}
