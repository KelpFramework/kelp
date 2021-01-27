package de.pxav.kelp.core.sidebar.component;

import java.util.Map;

/**
 * This superclass is inherited by every sidebar component.
 * It contains a {@link #render()} method converting the information
 * provided in your component to the final content that is
 * displayed in the sidebar.
 *
 * @author pxav
 */
public abstract class SidebarComponent {

  /**
   * Renders all the information provided in the component
   * to a map containing the final information to be rendered
   * to the sidebar (the lines where the text should be placed and
   * the text to write there).
   *
   * @return A map, where the key is the absolute line where
   *         the component should be placed in the sidebar and
   *         the value is the actual text for that line.
   */
  public abstract Map<Integer, String> render();

}
