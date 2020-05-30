package de.pxav.kelp.core.connect.connection;

import de.pxav.kelp.core.connect.KelpBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import javax.crypto.Cipher;
import java.util.List;

/**
 * @author Etrayed
 */
public class ConnectionDecrypter extends ByteToMessageDecoder {

  private final Cipher decrypter;

  ConnectionDecrypter(Cipher decrypter) {
    this.decrypter = decrypter;
  }

  @Override
  protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> list) throws Exception {
    KelpBuffer buffer = new KelpBuffer(byteBuf);

    if(decrypter != null) {
      byte[] bufferAsArray = new byte[byteBuf.readableBytes()];

      byteBuf.readBytes(bufferAsArray);

      buffer = new KelpBuffer(Unpooled.wrappedBuffer(decrypter.doFinal(bufferAsArray)));
    }

    list.add(buffer);
  }
}
