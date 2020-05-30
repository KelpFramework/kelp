package de.pxav.kelp.core.connect.packet;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author Etrayed
 */
public class PacketRegistry {

  private final BiMap<Integer, Class<? extends Packet>> idClassMap;

  public PacketRegistry() {
    this.idClassMap = HashBiMap.create();
  }

  public void register(int id, Class<? extends Packet> packetClass) {
    Preconditions.checkArgument(id <= Short.MAX_VALUE, "id may not be bigger than " + Short.MAX_VALUE);
    Preconditions.checkArgument(!isRegistered(id), String.format("id %d is already taken by %s", id,
      idClassMap.get(id).getCanonicalName()));
    Preconditions.checkArgument(!isRegistered(packetClass), String.format("class %s is already registered with id %d",
      packetClass.getCanonicalName(), idClassMap.inverse().get(packetClass)));

    idClassMap.put(id, packetClass);
  }

  public boolean isRegistered(int id) {
    return idClassMap.containsKey(id);
  }

  public boolean isRegistered(Class<? extends Packet> packetClass) {
    return idClassMap.containsValue(packetClass);
  }

  public int getId(Class<? extends Packet> packetClass) {
    Preconditions.checkArgument(isRegistered(packetClass), String.format("class %s is not registered",
      packetClass.getCanonicalName()));

    return idClassMap.inverse().get(packetClass);
  }

  public Class<? extends Packet> getPacketClass(int id) {
    Preconditions.checkArgument(isRegistered(id), String.format("id %d is not registered", id));

    return idClassMap.get(id);
  }

  public void unregister(int id) {
    idClassMap.remove(id);
  }
}
