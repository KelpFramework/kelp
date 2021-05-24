package de.pxav.kelp.core.entity.util.potion;

/**
 * Describes whether a potion effect type is considered as positive,
 * negative or mixed for the player who consumed it. This can help to
 * determine which effects to get from lucky blocks for example.
 *
 * @author pxav
 */
public enum KelpEffectRating {

  /**
   * The effect is positive for the player. Examples would be
   * <ul>
   *   <li>Strength</li>
   *   <li>Regeneration</li>
   *   <li>Absorption</li>
   *   <li>Speed</li>
   *   <li>Jump Boost</li>
   *   <li>Instant healing</li>
   *   <li>...</li>
   * </ul>
   */
  POSITIVE,

  /**
   * The effect is negative for the player. Examples would be
   * <ul>
   *   <li>Instant damage</li>
   *   <li>Levitation</li>
   *   <li>Poison</li>
   *   <li>Nausea</li>
   *   <li>...</li>
   * </ul>
   */
  NEGATIVE,

  /**
   * The effect is positive, but comes at the cost of also having some
   * negative effects. An example would be {@code Turtle master}, which reduces
   * taken damage, but also slows you down.
   */
  MIXED

}
