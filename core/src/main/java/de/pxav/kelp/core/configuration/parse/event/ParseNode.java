package de.pxav.kelp.core.configuration.parse.event;

import java.util.List;

public abstract class ParseNode {

  private List<String> comments;
  private ConfigPointer start;
  private ConfigPointer end;

  public ParseNode(ConfigPointer start, ConfigPointer end) {
    this.start = start;
    this.end = end;
  }

  public ConfigPointer getStart() {
    return start;
  }

  public ConfigPointer getEnd() {
    return end;
  }

  public List<String> getComments() {
    return comments;
  }

}
