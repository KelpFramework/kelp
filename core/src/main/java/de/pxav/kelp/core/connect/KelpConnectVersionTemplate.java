package de.pxav.kelp.core.connect;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.connect.connection.IConnectionDecrypter;
import de.pxav.kelp.core.connect.connection.IConnectionEncrypter;
import de.pxav.kelp.core.connect.packet.IPacketDecoder;
import de.pxav.kelp.core.connect.packet.IPacketEncoder;
import de.pxav.kelp.core.connect.packet.PacketRegistry;

import javax.crypto.Cipher;

/**
 * @author Etrayed
 */
@KelpVersionTemplate
public abstract class KelpConnectVersionTemplate {

  public abstract IConnectionEncrypter newEncrypter(Cipher encrypter);

  public abstract IConnectionDecrypter newDecrypter(Cipher decrypter);

  public abstract IPacketEncoder newPacketEncoder(PacketRegistry registry);

  public abstract IPacketDecoder newPacketDecoder(PacketRegistry registry);
}
