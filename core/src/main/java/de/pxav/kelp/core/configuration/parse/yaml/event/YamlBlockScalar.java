package de.pxav.kelp.core.configuration.parse.yaml.event;

import de.pxav.kelp.core.configuration.parse.event.ConfigPointer;
import de.pxav.kelp.core.configuration.parse.event.ParseNode;

public class YamlBlockScalar extends ParseNode {

  public YamlBlockScalar(ConfigPointer start, ConfigPointer end) {
    super(start, end);
  }

}
