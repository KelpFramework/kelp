package de.pxav.kelp.implementation1_8.connect;

import com.google.inject.Singleton;
import de.pxav.kelp.core.connect.KelpConnectVersionTemplate;
import de.pxav.kelp.core.connect.connection.IConnectionDecrypter;
import de.pxav.kelp.core.connect.connection.IConnectionEncrypter;
import de.pxav.kelp.core.connect.packet.IPacketDecoder;
import de.pxav.kelp.core.connect.packet.IPacketEncoder;
import de.pxav.kelp.core.connect.packet.PacketRegistry;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.implementation1_8.connect.connection.ConnectionDecrypter;
import de.pxav.kelp.implementation1_8.connect.connection.ConnectionEncrypter;
import de.pxav.kelp.implementation1_8.connect.packet.PacketDecoder;
import de.pxav.kelp.implementation1_8.connect.packet.PacketEncoder;

import javax.crypto.Cipher;

/**
 * @author Etrayed
 */
@Versioned
@Singleton
public class VersionedKelpConnect extends KelpConnectVersionTemplate {

  @Override
  public IConnectionEncrypter newEncrypter(Cipher encrypter) {
    return new ConnectionEncrypter(encrypter);
  }

  @Override
  public IConnectionDecrypter newDecrypter(Cipher decrypter) {
    return new ConnectionDecrypter(decrypter);
  }

  @Override
  public IPacketEncoder newPacketEncoder(PacketRegistry registry) {
    return new PacketEncoder(registry);
  }

  @Override
  public IPacketDecoder newPacketDecoder(PacketRegistry registry) {
    return new PacketDecoder(registry);
  }
}
