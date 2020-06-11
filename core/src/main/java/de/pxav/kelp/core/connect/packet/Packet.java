package de.pxav.kelp.core.connect.packet;

import de.pxav.kelp.core.connect.KelpBuffer;

/**
 * @author Etrayed
 */
public interface Packet {

  void store(KelpBuffer buffer);

  void take(KelpBuffer buffer);
}
