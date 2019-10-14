package de.pxav.kelp.sidebar;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.common.StringUtils;
import de.pxav.kelp.logger.KelpLogger;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class SidebarUtils {

  private KelpLogger logger;
  private StringUtils stringUtils;

  @Inject
  public SidebarUtils(KelpLogger logger, StringUtils stringUtils) {
    this.logger = logger;
    this.stringUtils = stringUtils;
  }

  public void setTeamData(String text, Team team) {
    if (text.length() <= 16) {
      team.setPrefix(text);
      return;
    }

    String toApply = stringUtils.lastFormattingCodesOf(text.substring(0, 16));
    String prefixText = text.substring(0, 16).endsWith("§")
            ? stringUtils.removeLastChar(text.substring(0, 16))
            : text.substring(0, 16);

    team.setPrefix(prefixText);
    if (text.length() > 30 - toApply.length()) {
      team.setSuffix(toApply + text.substring(16, 30 - toApply.length()));
      return;
    }
    team.setSuffix(toApply + text.substring(16));
  }

  public void setSplitTeamData(String text, Team team) {
    if (text.length() <= 16) {
      team.setPrefix(text);
    } else if (text.length() > 30) {
      team.setPrefix(text.substring(0, 16));
      team.setSuffix(ChatColor.RESET + text.substring(17, 30));
    }
  }

  public String randomEmptyEntry(Scoreboard scoreboard) {
    int index = ThreadLocalRandom.current().nextInt(1);
    int colorAmount = ThreadLocalRandom.current().nextInt(4);
    StringBuilder stringBuilder = new StringBuilder();
    Collection<String> forbidden = usedEntries(scoreboard);

    if (index == 0) {
      for (int i = 0; i < colorAmount; i++) {
        stringBuilder.append(randomChatColor());
      }
    } else if (index == 1) {
      stringBuilder.append(randomChatColor());
    }

    if (forbidden.contains(stringBuilder.toString())) {
      return randomEmptyEntry(scoreboard);
    }

    return stringBuilder.toString();
  }

  private Collection<String> usedEntries(Scoreboard scoreboard) {
    Collection<String> output = Lists.newArrayList();
    for (Team current : scoreboard.getTeams()) {
      output.addAll(current.getEntries());
    }
    return output;
  }

  private ChatColor randomChatColor() {
    int index = ThreadLocalRandom.current().nextInt(ChatColor.values().length - 1);
    return ChatColor.values()[index];
  }

}
