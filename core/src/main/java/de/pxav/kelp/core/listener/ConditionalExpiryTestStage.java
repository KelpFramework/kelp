package de.pxav.kelp.core.listener;

/**
 * This enum says in which execution stage a conditional expiry
 * of an event should be checked.
 *
 * That means that if you set any conditional expiry ({@code #expireIf(condition)})
 * the conditions are checked two times: before and after the code in the event
 * handler
 *
 * @author pxav
 */
public enum ConditionalExpiryTestStage {

  /**
   * The given condition is checked before the event handler is executed.
   * If the condition is not fulfilled, the listener will be removed and
   * the handler is not executed.
   */
  BEFORE_HANDLER,

  /**
   * The given condition is checked after the event handler has been
   * executed. Even if the condition is not fulfilled, the event handler
   * will be executed at least once. After the handler has been executed,
   * the listener is unregistered.
   */
  AFTER_HANDLER,

  /**
   * The condition is checked both before and after the execution.
   */
  ALWAYS

}
