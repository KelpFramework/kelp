package de.pxav.kelp.core.scheduler.synchronize;

import de.pxav.kelp.core.KelpPlugin;
import org.bukkit.Bukkit;

import java.util.concurrent.CountDownLatch;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ServerMainThread {

  public static class WaitForCompletion {

    private CountDownLatch startSignal;
    private CountDownLatch doneSignal;
    private Retrievable retrievable;
    private Object result;

    private WaitForCompletion(Retrievable retrievable) {
      this.retrievable = retrievable;
      this.result = null;
      this.startSignal = new CountDownLatch(1);
      this.doneSignal = new CountDownLatch(1);
    }

    public static Object result(Retrievable retrievable) {
      WaitForCompletion task = new WaitForCompletion(retrievable);
      return task.catchResult();
    }

    private Object catchResult() {
      Bukkit.getScheduler().scheduleSyncDelayedTask(KelpPlugin.getPlugin(KelpPlugin.class), () -> {
        try {
          this.startSignal.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        this.result = this.retrievable.retrieve();
        this.doneSignal.countDown();
      });

      this.startSignal.countDown();
      try {
        this.doneSignal.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return this.result;
    }

  }

}
