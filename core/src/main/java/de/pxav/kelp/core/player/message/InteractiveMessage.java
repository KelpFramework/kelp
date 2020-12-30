package de.pxav.kelp.core.player.message;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An interactive message is a chat message on which
 * players can click and trigger a specific event.
 *
 * @author pxav
 */
public class InteractiveMessage {

  // all components (different interactions) of the message.
  private Collection<MessageComponent> components;

  private InteractiveMessage() {
    // initialize the component list
    this.components = new ArrayList<>();
  }

  /**
   * Factory method for the interactive message. Use this method
   * if you want to create a new message as the normal constructor
   * is private.
   *
   * @return A new instance of an interactive message.
   */
  public static InteractiveMessage create() {
    return new InteractiveMessage();
  }

  /**
   * Add a new component to the message. This does not mean that you create
   * a new line, but a new segment of events.
   *
   * Example: If you have a message to accept a party, you create a component
   * for the prefix and the normal text ([PARTY] Click here to...) and a component,
   * which is clickable and accepts the party (...accept the party.). Unlike in the
   * normal spigot api, you can create unlimited components with unlimited color codes.
   *
   * @param messageComponent The component to add.
   * @return The current message object (used for fluent builder structure).
   */
  public InteractiveMessage addComponent(MessageComponent messageComponent) {
    this.components.add(messageComponent);
    return this;
  }

  /**
   * Returns a full {@link Collection} of all components of the interactive message.
   *
   * @return All components of the message.
   */
  public Collection<MessageComponent> getComponents() {
    return components;
  }

}
