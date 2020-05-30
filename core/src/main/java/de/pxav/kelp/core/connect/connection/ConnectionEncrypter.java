package de.pxav.kelp.core.connect.connection;

import de.pxav.kelp.core.connect.KelpBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import javax.crypto.Cipher;

/**
 * @author Etrayed
 */
public class ConnectionEncrypter extends MessageToByteEncoder<KelpBuffer> {

  private final Cipher encrypter;

  ConnectionEncrypter(Cipher encrypter) {
    this.encrypter = encrypter;
  }

  @Override
  protected void encode(ChannelHandlerContext context, KelpBuffer input, ByteBuf output) throws Exception {
    byte[] outputArray = input.readAllBytes();

    if(encrypter != null) {
      outputArray = encrypter.doFinal(outputArray);
    }

    output.writeBytes(outputArray);
  }
}
