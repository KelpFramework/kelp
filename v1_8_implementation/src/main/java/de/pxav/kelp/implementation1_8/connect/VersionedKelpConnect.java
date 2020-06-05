package de.pxav.kelp.implementation1_8.connect;

import com.google.inject.Singleton;
import de.pxav.kelp.core.connect.KelpConnectVersionTemplate;
import de.pxav.kelp.core.connect.packet.IPacketDecoder;
import de.pxav.kelp.core.connect.packet.IPacketEncoder;
import de.pxav.kelp.core.connect.packet.PacketRegistry;
import de.pxav.kelp.core.version.Versioned;
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
  public IPacketEncoder newPacketEncoder(PacketRegistry registry, Cipher encrypter) {
    return new PacketEncoder(registry, encrypter);
  }

  @Override
  public IPacketDecoder newPacketDecoder(PacketRegistry registry, Cipher decrypter) {
    return new PacketDecoder(registry, decrypter);
  }
}
