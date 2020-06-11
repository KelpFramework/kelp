package de.pxav.kelp.core.connect;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Etrayed
 */
public class KelpBuffer {

  private final ByteBuf delegate;

  public KelpBuffer(ByteBuf delegate) {
    this.delegate = delegate;
  }

  public byte[] readAllBytes() {
    byte[] bytes = new byte[delegate.readableBytes()];

    delegate.readBytes(bytes);

    return bytes;
  }

  public KelpBuffer writeVarInt(int i) {
    while((i & -128) != 0) {
      delegate.writeByte(i & 127 | 128);

      i >>>= 7;
    }

    delegate.writeByte(i);

    return this;
  }

  public KelpBuffer writeVarLong(long l) {
    while((l & -128L) != 0L) {
      delegate.writeByte((int)(l & 127L) | 128);

      l >>>= 7;
    }

    delegate.writeByte((int) l);

    return this;
  }

  public KelpBuffer writeString(String str) {
    byte[] bytes = str.getBytes(StandardCharsets.UTF_8);

    Preconditions.checkArgument(bytes.length <= Short.MAX_VALUE,
      String.format("max string length exceeded: %d > %d", bytes.length, Short.MAX_VALUE));

    writeVarInt(bytes.length);

    delegate.writeBytes(bytes);

    return this;
  }

  public KelpBuffer writeUUID(UUID uuid) {
    delegate.writeLong(uuid.getMostSignificantBits());
    delegate.writeLong(uuid.getLeastSignificantBits());

    return this;
  }

  public int readVarInt() {
    int result = 0;
    int count = 0;
    byte read;

    do {
      read = this.delegate.readByte();
      result |= (read & 127) << count++ * 7;

      if (count > 5) {
        throw new RuntimeException("VarInt too big");
      }
    } while((read & 128) == 128);

    return result;
  }

  public long readVarLong() {
    long result = 0L;
    int count = 0;
    byte read;

    do {
      read = this.delegate.readByte();
      result |= (long)(read & 127) << count++ * 7;

      if (count > 10) {
        throw new RuntimeException("VarLong too big");
      }
    } while((read & 128) == 128);

    return result;
  }

  public String readString() {
    byte[] bytes = new byte[readVarInt()];

    delegate.readBytes(bytes);

    return new String(bytes, StandardCharsets.UTF_8);
  }

  public UUID readUUID() {
    return new UUID(delegate.readLong(), delegate.readLong());
  }

  public ByteBuf delegate() {
    return delegate;
  }
}
