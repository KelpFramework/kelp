package de.pxav.kelp.testing.packet;

import de.pxav.kelp.core.connect.KelpBuffer;
import de.pxav.kelp.core.connect.packet.Packet;

/**
 * @author Etrayed
 */
public class PingPacket implements Packet {

  private long timestamp;

  public PingPacket() {
    this.timestamp = System.currentTimeMillis();
  }

  public long getTimestamp() {
    return timestamp;
  }

  @Override
  public void store(KelpBuffer buffer) {
    buffer.writeLong(timestamp);
  }

  @Override
  public void take(KelpBuffer buffer) {
    this.timestamp = buffer.readLong();
  }
}
