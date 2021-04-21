package de.pxav.kelp.core.entity.type;

public interface PigZombie extends ZombieEntity {

  int getAnger();

  void setAnger(int anger);

  void setAngry(boolean angry);

  boolean isAngry();

  boolean isConverting();

  int getConversionTime();

  void setConversionTime(int conversionTime);

}
