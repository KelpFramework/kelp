package de.pxav.kelp.core;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.version.KelpVersion;
import org.bukkit.Bukkit;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpServer {

  private KelpPlayerRepository kelpPlayerRepository;

  public KelpVersion getVersion() {
    return KelpVersion.withBukkitVersion(Bukkit.getBukkitVersion());
  }

  public Collection<KelpPlayer> getOnlinePlayers() {
    Collection<KelpPlayer> output = Lists.newArrayList();
    Bukkit.getOnlinePlayers().forEach(current -> output.add(kelpPlayerRepository.getKelpPlayer(current)));
    return output;
  }

  public int getMaxPlayers() {
    return Bukkit.getMaxPlayers();
  }

  public void broadcastMessage(String message) {
    Bukkit.getOnlinePlayers().forEach(current -> current.sendMessage(message));
  }

  public void broadcastMessages(String... message) {
    Bukkit.getOnlinePlayers().forEach(current -> {
      for (String s : message) {
        current.sendMessage(s);
      }
    });
  }

  public void broadcastMessages(Collection<String> messages) {
    Bukkit.getOnlinePlayers().forEach(current -> {
      for (String s : messages) {
        current.sendMessage(s);
      }
    });
  }

}
