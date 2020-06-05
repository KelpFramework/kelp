package de.pxav.kelp.core.connect;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.connect.packet.IPacketDecoder;
import de.pxav.kelp.core.connect.packet.IPacketEncoder;
import de.pxav.kelp.core.connect.packet.PacketRegistry;

import javax.crypto.Cipher;

/**
 * @author Etrayed
 */
@KelpVersionTemplate
public abstract class KelpConnectVersionTemplate {

  public abstract IPacketEncoder newPacketEncoder(PacketRegistry registry, Cipher encrypter);

  public abstract IPacketDecoder newPacketDecoder(PacketRegistry registry, Cipher decrypter);
}
