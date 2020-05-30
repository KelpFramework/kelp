package de.pxav.kelp.core.connect;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Etrayed
 */
public class KelpBuffer extends ByteBuf {

  private final ByteBuf delegate;

  public KelpBuffer(ByteBuf delegate) {
    this.delegate = delegate;
  }

  public KelpBuffer writeVarInt(int i) {
    while((i & -128) != 0) {
      this.writeByte(i & 127 | 128);

      i >>>= 7;
    }

    this.writeByte(i);

    return this;
  }

  public KelpBuffer writeVarLong(long l) {
    while((l & -128L) != 0L) {
      this.writeByte((int)(l & 127L) | 128);

      l >>>= 7;
    }

    this.writeByte((int) l);

    return this;
  }

  public KelpBuffer writeString(String str) {
    byte[] bytes = str.getBytes(StandardCharsets.UTF_8);

    Preconditions.checkArgument(bytes.length <= Short.MAX_VALUE,
      String.format("max string length exceeded: %d > %d", bytes.length, Short.MAX_VALUE));

    writeVarInt(bytes.length);
    writeBytes(bytes);

    return this;
  }

  public KelpBuffer writeUUID(UUID uuid) {
    writeLong(uuid.getMostSignificantBits());
    writeLong(uuid.getLeastSignificantBits());

    return this;
  }

  public int readVarInt() {
    int result = 0;
    int count = 0;
    byte read;

    do {
      read = this.readByte();
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
      read = this.readByte();
      result |= (long)(read & 127) << count++ * 7;

      if (count > 10) {
        throw new RuntimeException("VarLong too big");
      }
    } while((read & 128) == 128);

    return result;
  }

  public String readString() {
    byte[] bytes = new byte[readVarInt()];

    readBytes(bytes);

    return new String(bytes, StandardCharsets.UTF_8);
  }

  public UUID readUUID() {
    return new UUID(readLong(), readLong());
  }

  @Override
  public int capacity() {
    return delegate.capacity();
  }

  @Override
  public ByteBuf capacity(int i) {
    return delegate.capacity(i);
  }

  @Override
  public int maxCapacity() {
    return delegate.maxCapacity();
  }

  @Override
  public ByteBufAllocator alloc() {
    return delegate.alloc();
  }

  @Override
  @Deprecated
  public ByteOrder order() {
    return delegate.order();
  }

  @Override
  @Deprecated
  public ByteBuf order(ByteOrder byteOrder) {
    return delegate.order(byteOrder);
  }

  @Override
  public ByteBuf unwrap() {
    return delegate.unwrap();
  }

  @Override
  public boolean isDirect() {
    return delegate.isDirect();
  }

  @Override
  public boolean isReadOnly() {
    return delegate.isReadOnly();
  }

  @Override
  public ByteBuf asReadOnly() {
    return delegate.asReadOnly();
  }

  @Override
  public int readerIndex() {
    return delegate.readerIndex();
  }

  @Override
  public ByteBuf readerIndex(int i) {
    return delegate.readerIndex(i);
  }

  @Override
  public int writerIndex() {
    return delegate.writerIndex();
  }

  @Override
  public ByteBuf writerIndex(int i) {
    return delegate.writerIndex(i);
  }

  @Override
  public ByteBuf setIndex(int i, int i1) {
    return delegate.setIndex(i, i1);
  }

  @Override
  public int readableBytes() {
    return delegate.readableBytes();
  }

  @Override
  public int writableBytes() {
    return delegate.writableBytes();
  }

  @Override
  public int maxWritableBytes() {
    return delegate.maxWritableBytes();
  }

  @Override
  public boolean isReadable() {
    return delegate.isReadable();
  }

  @Override
  public boolean isReadable(int i) {
    return delegate.isReadable(i);
  }

  @Override
  public boolean isWritable() {
    return delegate.isWritable();
  }

  @Override
  public boolean isWritable(int i) {
    return delegate.isWritable(i);
  }

  @Override
  public ByteBuf clear() {
    return delegate.clear();
  }

  @Override
  public ByteBuf markReaderIndex() {
    return delegate.markReaderIndex();
  }

  @Override
  public ByteBuf resetReaderIndex() {
    return delegate.resetReaderIndex();
  }

  @Override
  public ByteBuf markWriterIndex() {
    return delegate.markWriterIndex();
  }

  @Override
  public ByteBuf resetWriterIndex() {
    return delegate.resetWriterIndex();
  }

  @Override
  public ByteBuf discardReadBytes() {
    return delegate.discardReadBytes();
  }

  @Override
  public ByteBuf discardSomeReadBytes() {
    return delegate.discardSomeReadBytes();
  }

  @Override
  public ByteBuf ensureWritable(int i) {
    return delegate.ensureWritable(i);
  }

  @Override
  public int ensureWritable(int i, boolean b) {
    return delegate.ensureWritable(i, b);
  }

  @Override
  public boolean getBoolean(int i) {
    return delegate.getBoolean(i);
  }

  @Override
  public byte getByte(int i) {
    return delegate.getByte(i);
  }

  @Override
  public short getUnsignedByte(int i) {
    return delegate.getUnsignedByte(i);
  }

  @Override
  public short getShort(int i) {
    return delegate.getShort(i);
  }

  @Override
  public short getShortLE(int i) {
    return delegate.getShortLE(i);
  }

  @Override
  public int getUnsignedShort(int i) {
    return delegate.getUnsignedShort(i);
  }

  @Override
  public int getUnsignedShortLE(int i) {
    return delegate.getUnsignedShortLE(i);
  }

  @Override
  public int getMedium(int i) {
    return delegate.getMedium(i);
  }

  @Override
  public int getMediumLE(int i) {
    return delegate.getMediumLE(i);
  }

  @Override
  public int getUnsignedMedium(int i) {
    return delegate.getUnsignedMedium(i);
  }

  @Override
  public int getUnsignedMediumLE(int i) {
    return delegate.getUnsignedMediumLE(i);
  }

  @Override
  public int getInt(int i) {
    return delegate.getInt(i);
  }

  @Override
  public int getIntLE(int i) {
    return delegate.getIntLE(i);
  }

  @Override
  public long getUnsignedInt(int i) {
    return delegate.getUnsignedInt(i);
  }

  @Override
  public long getUnsignedIntLE(int i) {
    return delegate.getUnsignedIntLE(i);
  }

  @Override
  public long getLong(int i) {
    return delegate.getLong(i);
  }

  @Override
  public long getLongLE(int i) {
    return delegate.getLongLE(i);
  }

  @Override
  public char getChar(int i) {
    return delegate.getChar(i);
  }

  @Override
  public float getFloat(int i) {
    return delegate.getFloat(i);
  }

  @Override
  public float getFloatLE(int index) {
    return delegate.getFloatLE(index);
  }

  @Override
  public double getDouble(int i) {
    return delegate.getDouble(i);
  }

  @Override
  public double getDoubleLE(int index) {
    return delegate.getDoubleLE(index);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuf byteBuf) {
    return delegate.getBytes(i, byteBuf);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1) {
    return delegate.getBytes(i, byteBuf, i1);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1, int i2) {
    return delegate.getBytes(i, byteBuf, i1, i2);
  }

  @Override
  public ByteBuf getBytes(int i, byte[] bytes) {
    return delegate.getBytes(i, bytes);
  }

  @Override
  public ByteBuf getBytes(int i, byte[] bytes, int i1, int i2) {
    return delegate.getBytes(i, bytes, i1, i2);
  }

  @Override
  public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
    return delegate.getBytes(i, byteBuffer);
  }

  @Override
  public ByteBuf getBytes(int i, OutputStream outputStream, int i1) throws IOException {
    return delegate.getBytes(i, outputStream, i1);
  }

  @Override
  public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i1) throws IOException {
    return delegate.getBytes(i, gatheringByteChannel, i1);
  }

  @Override
  public int getBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
    return delegate.getBytes(i, fileChannel, l, i1);
  }

  @Override
  public CharSequence getCharSequence(int i, int i1, Charset charset) {
    return delegate.getCharSequence(i, i1, charset);
  }

  @Override
  public ByteBuf setBoolean(int i, boolean b) {
    return delegate.setBoolean(i, b);
  }

  @Override
  public ByteBuf setByte(int i, int i1) {
    return delegate.setByte(i, i1);
  }

  @Override
  public ByteBuf setShort(int i, int i1) {
    return delegate.setShort(i, i1);
  }

  @Override
  public ByteBuf setShortLE(int i, int i1) {
    return delegate.setShortLE(i, i1);
  }

  @Override
  public ByteBuf setMedium(int i, int i1) {
    return delegate.setMedium(i, i1);
  }

  @Override
  public ByteBuf setMediumLE(int i, int i1) {
    return delegate.setMediumLE(i, i1);
  }

  @Override
  public ByteBuf setInt(int i, int i1) {
    return delegate.setInt(i, i1);
  }

  @Override
  public ByteBuf setIntLE(int i, int i1) {
    return delegate.setIntLE(i, i1);
  }

  @Override
  public ByteBuf setLong(int i, long l) {
    return delegate.setLong(i, l);
  }

  @Override
  public ByteBuf setLongLE(int i, long l) {
    return delegate.setLongLE(i, l);
  }

  @Override
  public ByteBuf setChar(int i, int i1) {
    return delegate.setChar(i, i1);
  }

  @Override
  public ByteBuf setFloat(int i, float v) {
    return delegate.setFloat(i, v);
  }

  @Override
  public ByteBuf setFloatLE(int index, float value) {
    return delegate.setFloatLE(index, value);
  }

  @Override
  public ByteBuf setDouble(int i, double v) {
    return delegate.setDouble(i, v);
  }

  @Override
  public ByteBuf setDoubleLE(int index, double value) {
    return delegate.setDoubleLE(index, value);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuf byteBuf) {
    return delegate.setBytes(i, byteBuf);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1) {
    return delegate.setBytes(i, byteBuf, i1);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1, int i2) {
    return delegate.setBytes(i, byteBuf, i1, i2);
  }

  @Override
  public ByteBuf setBytes(int i, byte[] bytes) {
    return delegate.setBytes(i, bytes);
  }

  @Override
  public ByteBuf setBytes(int i, byte[] bytes, int i1, int i2) {
    return delegate.setBytes(i, bytes, i1, i2);
  }

  @Override
  public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
    return delegate.setBytes(i, byteBuffer);
  }

  @Override
  public int setBytes(int i, InputStream inputStream, int i1) throws IOException {
    return delegate.setBytes(i, inputStream, i1);
  }

  @Override
  public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i1) throws IOException {
    return delegate.setBytes(i, scatteringByteChannel, i1);
  }

  @Override
  public int setBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
    return delegate.setBytes(i, fileChannel, l, i1);
  }

  @Override
  public ByteBuf setZero(int i, int i1) {
    return delegate.setZero(i, i1);
  }

  @Override
  public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
    return delegate.setCharSequence(i, charSequence, charset);
  }

  @Override
  public boolean readBoolean() {
    return delegate.readBoolean();
  }

  @Override
  public byte readByte() {
    return delegate.readByte();
  }

  @Override
  public short readUnsignedByte() {
    return delegate.readUnsignedByte();
  }

  @Override
  public short readShort() {
    return delegate.readShort();
  }

  @Override
  public short readShortLE() {
    return delegate.readShortLE();
  }

  @Override
  public int readUnsignedShort() {
    return delegate.readUnsignedShort();
  }

  @Override
  public int readUnsignedShortLE() {
    return delegate.readUnsignedShortLE();
  }

  @Override
  public int readMedium() {
    return delegate.readMedium();
  }

  @Override
  public int readMediumLE() {
    return delegate.readMediumLE();
  }

  @Override
  public int readUnsignedMedium() {
    return delegate.readUnsignedMedium();
  }

  @Override
  public int readUnsignedMediumLE() {
    return delegate.readUnsignedMediumLE();
  }

  @Override
  public int readInt() {
    return delegate.readInt();
  }

  @Override
  public int readIntLE() {
    return delegate.readIntLE();
  }

  @Override
  public long readUnsignedInt() {
    return delegate.readUnsignedInt();
  }

  @Override
  public long readUnsignedIntLE() {
    return delegate.readUnsignedIntLE();
  }

  @Override
  public long readLong() {
    return delegate.readLong();
  }

  @Override
  public long readLongLE() {
    return delegate.readLongLE();
  }

  @Override
  public char readChar() {
    return delegate.readChar();
  }

  @Override
  public float readFloat() {
    return delegate.readFloat();
  }

  @Override
  public float readFloatLE() {
    return delegate.readFloatLE();
  }

  @Override
  public double readDouble() {
    return delegate.readDouble();
  }

  @Override
  public double readDoubleLE() {
    return delegate.readDoubleLE();
  }

  @Override
  public ByteBuf readBytes(int i) {
    return delegate.readBytes(i);
  }

  @Override
  public ByteBuf readSlice(int i) {
    return delegate.readSlice(i);
  }

  @Override
  public ByteBuf readRetainedSlice(int i) {
    return delegate.readRetainedSlice(i);
  }

  @Override
  public ByteBuf readBytes(ByteBuf byteBuf) {
    return delegate.readBytes(byteBuf);
  }

  @Override
  public ByteBuf readBytes(ByteBuf byteBuf, int i) {
    return delegate.readBytes(byteBuf, i);
  }

  @Override
  public ByteBuf readBytes(ByteBuf byteBuf, int i, int i1) {
    return delegate.readBytes(byteBuf, i, i1);
  }

  @Override
  public ByteBuf readBytes(byte[] bytes) {
    return delegate.readBytes(bytes);
  }

  @Override
  public ByteBuf readBytes(byte[] bytes, int i, int i1) {
    return delegate.readBytes(bytes, i, i1);
  }

  @Override
  public ByteBuf readBytes(ByteBuffer byteBuffer) {
    return delegate.readBytes(byteBuffer);
  }

  @Override
  public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
    return delegate.readBytes(outputStream, i);
  }

  @Override
  public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
    return delegate.readBytes(gatheringByteChannel, i);
  }

  @Override
  public CharSequence readCharSequence(int i, Charset charset) {
    return delegate.readCharSequence(i, charset);
  }

  @Override
  public int readBytes(FileChannel fileChannel, long l, int i) throws IOException {
    return delegate.readBytes(fileChannel, l, i);
  }

  @Override
  public ByteBuf skipBytes(int i) {
    return delegate.skipBytes(i);
  }

  @Override
  public ByteBuf writeBoolean(boolean b) {
    return delegate.writeBoolean(b);
  }

  @Override
  public ByteBuf writeByte(int i) {
    return delegate.writeByte(i);
  }

  @Override
  public ByteBuf writeShort(int i) {
    return delegate.writeShort(i);
  }

  @Override
  public ByteBuf writeShortLE(int i) {
    return delegate.writeShortLE(i);
  }

  @Override
  public ByteBuf writeMedium(int i) {
    return delegate.writeMedium(i);
  }

  @Override
  public ByteBuf writeMediumLE(int i) {
    return delegate.writeMediumLE(i);
  }

  @Override
  public ByteBuf writeInt(int i) {
    return delegate.writeInt(i);
  }

  @Override
  public ByteBuf writeIntLE(int i) {
    return delegate.writeIntLE(i);
  }

  @Override
  public ByteBuf writeLong(long l) {
    return delegate.writeLong(l);
  }

  @Override
  public ByteBuf writeLongLE(long l) {
    return delegate.writeLongLE(l);
  }

  @Override
  public ByteBuf writeChar(int i) {
    return delegate.writeChar(i);
  }

  @Override
  public ByteBuf writeFloat(float v) {
    return delegate.writeFloat(v);
  }

  @Override
  public ByteBuf writeFloatLE(float value) {
    return delegate.writeFloatLE(value);
  }

  @Override
  public ByteBuf writeDouble(double v) {
    return delegate.writeDouble(v);
  }

  @Override
  public ByteBuf writeDoubleLE(double value) {
    return delegate.writeDoubleLE(value);
  }

  @Override
  public ByteBuf writeBytes(ByteBuf byteBuf) {
    return delegate.writeBytes(byteBuf);
  }

  @Override
  public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
    return delegate.writeBytes(byteBuf, i);
  }

  @Override
  public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i1) {
    return delegate.writeBytes(byteBuf, i, i1);
  }

  @Override
  public ByteBuf writeBytes(byte[] bytes) {
    return delegate.writeBytes(bytes);
  }

  @Override
  public ByteBuf writeBytes(byte[] bytes, int i, int i1) {
    return delegate.writeBytes(bytes, i, i1);
  }

  @Override
  public ByteBuf writeBytes(ByteBuffer byteBuffer) {
    return delegate.writeBytes(byteBuffer);
  }

  @Override
  public int writeBytes(InputStream inputStream, int i) throws IOException {
    return delegate.writeBytes(inputStream, i);
  }

  @Override
  public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
    return delegate.writeBytes(scatteringByteChannel, i);
  }

  @Override
  public int writeBytes(FileChannel fileChannel, long l, int i) throws IOException {
    return delegate.writeBytes(fileChannel, l, i);
  }

  @Override
  public ByteBuf writeZero(int i) {
    return delegate.writeZero(i);
  }

  @Override
  public int writeCharSequence(CharSequence charSequence, Charset charset) {
    return delegate.writeCharSequence(charSequence, charset);
  }

  @Override
  public int indexOf(int i, int i1, byte b) {
    return delegate.indexOf(i, i1, b);
  }

  @Override
  public int bytesBefore(byte b) {
    return delegate.bytesBefore(b);
  }

  @Override
  public int bytesBefore(int i, byte b) {
    return delegate.bytesBefore(i, b);
  }

  @Override
  public int bytesBefore(int i, int i1, byte b) {
    return delegate.bytesBefore(i, i1, b);
  }

  @Override
  public int forEachByte(ByteProcessor byteProcessor) {
    return delegate.forEachByte(byteProcessor);
  }

  @Override
  public int forEachByte(int i, int i1, ByteProcessor byteProcessor) {
    return delegate.forEachByte(i, i1, byteProcessor);
  }

  @Override
  public int forEachByteDesc(ByteProcessor byteProcessor) {
    return delegate.forEachByteDesc(byteProcessor);
  }

  @Override
  public int forEachByteDesc(int i, int i1, ByteProcessor byteProcessor) {
    return delegate.forEachByteDesc(i, i1, byteProcessor);
  }

  @Override
  public ByteBuf copy() {
    return delegate.copy();
  }

  @Override
  public ByteBuf copy(int i, int i1) {
    return delegate.copy(i, i1);
  }

  @Override
  public ByteBuf slice() {
    return delegate.slice();
  }

  @Override
  public ByteBuf retainedSlice() {
    return delegate.retainedSlice();
  }

  @Override
  public ByteBuf slice(int i, int i1) {
    return delegate.slice(i, i1);
  }

  @Override
  public ByteBuf retainedSlice(int i, int i1) {
    return delegate.retainedSlice(i, i1);
  }

  @Override
  public ByteBuf duplicate() {
    return delegate.duplicate();
  }

  @Override
  public ByteBuf retainedDuplicate() {
    return delegate.retainedDuplicate();
  }

  @Override
  public int nioBufferCount() {
    return delegate.nioBufferCount();
  }

  @Override
  public ByteBuffer nioBuffer() {
    return delegate.nioBuffer();
  }

  @Override
  public ByteBuffer nioBuffer(int i, int i1) {
    return delegate.nioBuffer(i, i1);
  }

  @Override
  public ByteBuffer internalNioBuffer(int i, int i1) {
    return delegate.internalNioBuffer(i, i1);
  }

  @Override
  public ByteBuffer[] nioBuffers() {
    return delegate.nioBuffers();
  }

  @Override
  public ByteBuffer[] nioBuffers(int i, int i1) {
    return delegate.nioBuffers(i, i1);
  }

  @Override
  public boolean hasArray() {
    return delegate.hasArray();
  }

  @Override
  public byte[] array() {
    return delegate.array();
  }

  @Override
  public int arrayOffset() {
    return delegate.arrayOffset();
  }

  @Override
  public boolean hasMemoryAddress() {
    return delegate.hasMemoryAddress();
  }

  @Override
  public long memoryAddress() {
    return delegate.memoryAddress();
  }

  @Override
  public String toString(Charset charset) {
    return delegate.toString(charset);
  }

  @Override
  public String toString(int i, int i1, Charset charset) {
    return delegate.toString(i, i1, charset);
  }

  @Override
  public int hashCode() {
    return delegate.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return delegate.equals(o);
  }

  @Override
  public int compareTo(ByteBuf byteBuf) {
    return delegate.compareTo(byteBuf);
  }

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public ByteBuf retain(int i) {
    return delegate.retain(i);
  }

  @Override
  public ByteBuf retain() {
    return delegate.retain();
  }

  @Override
  public ByteBuf touch() {
    return delegate.touch();
  }

  @Override
  public ByteBuf touch(Object o) {
    return delegate.touch(o);
  }

  @Override
  public int refCnt() {
    return delegate.refCnt();
  }

  @Override
  public boolean release() {
    return delegate.release();
  }

  @Override
  public boolean release(int i) {
    return delegate.release(i);
  }
}
