package de.pxav.kelp.core.scheduler.synchronize;

import de.pxav.kelp.core.KelpPlugin;
import org.bukkit.Bukkit;

import java.util.concurrent.CountDownLatch;

/**
 * This class is used to synchronize actions between your async
 * scheduler threads and the server main thread. If you have an
 * action you have to execute on the main thread, because it is
 * not thread-safe, you do not have to make your entire scheduler
 * sync, but simply use this class to
 *  - either block your thread and wait for the operation on the
 *    main thread to complete
 *  - or start an action which runs parallel to the action on
 *    your async thread and your async thread can continue
 *
 * @author pxav
 */
public class ServerMainThread {

  /**
   * This subclass is used to start an action on the main thread
   * and wait for it to complete, while the scheduler thread is
   * blocked. This can be useful if you want to retrieve values
   * from the main thread and calculate with them on your async
   * thread.
   *
   * @author pxav
   */
  public static class WaitForCompletion {

    // this latch is released when the operation on the main
    // thread is allowed to start
    private CountDownLatch startSignal;

    // this latch is released when the operation on the
    // main thread has completed and the async thread may continue
    private CountDownLatch doneSignal;

    private Retrievable retrievable;

    // the result returned by the retrieve-method of Retrievable
    private Object result;

    private WaitForCompletion(Retrievable retrievable) {
      this.retrievable = retrievable;
      this.result = null;
      this.startSignal = new CountDownLatch(1);
      this.doneSignal = new CountDownLatch(1);
    }

    /**
     * Creates a new instance of the class and performs the operations
     * on the main thread
     *
     * @param retrievable The code calculating and returning the result.
     * @return The result of the calculation
     */
    public static Object result(Retrievable retrievable) {
      WaitForCompletion task = new WaitForCompletion(retrievable);
      return task.catchResult();
    }

    /**
     * Starts the operation on the main thread and blocks the async thread.
     *
     * @return The result from the calculation on the main thread.
     */
    private Object catchResult() {

      // start the operation on the main thread, which is still blocked
      // as startSignal has not been released yet.
      Bukkit.getScheduler().scheduleSyncDelayedTask(KelpPlugin.getPlugin(KelpPlugin.class), () -> {
        try {
          // wait for the start signal to be fired
          this.startSignal.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        // retrieve the result from the operation and release
        // the latch showing that the operation has completed
        this.result = this.retrievable.retrieve();
        this.doneSignal.countDown();
      });

      // release start signal so that the operation on the main thread can begin
      this.startSignal.countDown();

      // wait for the operation on the main thread to complete
      // and then return the result
      try {
        this.doneSignal.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return this.result;
    }

  }

  /**
   * This subclass is used to start actions from your async scheduler
   * threads on the server main thread, while your async thread is not
   * blocked and continues as normal.
   *
   * @author pxav
   */
  public static class RunParallel {

    /**
     * Immediately executes the given task on the server main thread
     * using the synchronous bukkit scheduler executor.
     *
     * @param runnable The code to execute on the main thread
     */
    public static void run(Runnable runnable) {

      // if we are already on the main thread, directly execute the operation instead of creating a new scheduler.
      if (Bukkit.isPrimaryThread()) {
        runnable.run();
        return;
      }

      Bukkit.getScheduler().scheduleSyncDelayedTask(KelpPlugin.getPlugin(KelpPlugin.class), runnable);
    }

  }

}
