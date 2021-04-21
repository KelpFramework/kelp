package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.type.general.BreedableAnimalEntity;
import de.pxav.kelp.core.entity.type.general.TameableAnimal;
import de.pxav.kelp.core.entity.util.PandaGene;

public interface PandaEntity extends TameableAnimal<PandaEntity>, BreedableAnimalEntity<PandaEntity> {

  PandaEntity setMainGene(PandaGene prominentGene);

  PandaEntity setHiddenGene(PandaGene hiddenGene);

  PandaGene getMainGene();

  PandaGene getHiddenGene();

}
