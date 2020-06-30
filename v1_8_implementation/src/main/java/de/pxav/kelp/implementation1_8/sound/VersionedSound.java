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
    long started = System.currentTimeMillis();

    soundRepository.addSound(KelpSound.LEVEL_UP, Sound.LEVEL_UP.toString());
    soundRepository.addSound(KelpSound.ITEM_BREAK, Sound.ITEM_BREAK.toString());

    long elapsed = System.currentTimeMillis() - started;
    System.out.println("[VERSION-1.8] Successfully defined sound names (took " + elapsed + "ms)");
  }

}
