package de.pxav.kelp.core.player.message;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class description goes here.
 * Message syntax: Open our website [%s] fohpuh [%s]
 *
 * @author pxav
 */
public class InteractiveMessage {

  private Collection<MessageComponent> components;

  private InteractiveMessage() {
    this.components = new ArrayList<>();
  }

  public static InteractiveMessage create() {
    return new InteractiveMessage();
  }

  public InteractiveMessage addComponent(MessageComponent messageComponent) {
    this.components.add(messageComponent);
    return this;
  }

  public Collection<MessageComponent> getComponents() {
    return components;
  }

}
