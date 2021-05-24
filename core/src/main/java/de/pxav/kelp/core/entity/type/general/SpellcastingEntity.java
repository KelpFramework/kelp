package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.util.SpellType;

public interface SpellcastingEntity<T extends SpellcastingEntity<T>> extends IllagerEntity<T> {

  T setSpellType(SpellType spellType);

  SpellType getSpellType();

}
