package de.pxav.kelp.implementation1_8.sound;

import com.google.inject.Inject;
import de.pxav.kelp.core.sound.KelpSound;
import de.pxav.kelp.core.sound.SoundRepository;
import de.pxav.kelp.core.sound.SoundVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Sound;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedSound extends SoundVersionTemplate {

  private SoundRepository soundRepository;

  @Inject
  public VersionedSound(SoundRepository soundRepository) {
    this.soundRepository = soundRepository;
  }

  @Override
  public void defineDefaults() {
    soundRepository.addSound(KelpSound.LEVEL_UP, Sound.LEVEL_UP.toString());
    soundRepository.addSound(KelpSound.ITEM_BREAK, Sound.ITEM_BREAK.toString());

    System.out.println("Successfully defined sound names for 1.8 version");
  }

}
