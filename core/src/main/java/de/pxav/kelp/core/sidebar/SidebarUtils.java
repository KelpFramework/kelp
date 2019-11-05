package de.pxav.kelp.core.sidebar;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.common.StringUtils;
import de.pxav.kelp.core.logger.KelpLogger;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class provides essential methods for
 * scoreboard management like handling prefixes for
 * your teams, etc.
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

  /**
   * This method modifies the data of the given team
   * and applies the given text to it. Since the method
   * uses the prefix as well as the suffix of the team
   * to display the information your text may be up to
   * 30 characters long.
   *
   * @param text The text you want to apply to the team.
   * @param team The team you want to modify.
   */
  public void setTeamData(String text, Team team) {

    // if the given text is not longer than 16 chars,
    // it fits in the prefix only as well and no more work has to be done.
    if (text.length() <= 16) {
      team.setPrefix(text);
      return;
    }

    // when separating the text on prefix and suffix, you
    // have to care about the team name which will reset the color
    // codes. So you need to know which color codes affect the second
    // part and put them at the front of the actual suffix text.
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

  /**
   * Generates a random empty entry, which means that it will
   * be invisible. This can be used to generate team names, because
   * they only serve as placeholders and may not be seen by players.
   * Therefore the entry name does even not contain spaces, but only
   * formatting codes.
   *
   * @param scoreboard The scoreboard you want to generate
   *                  the entry for.
   * @return The final generation result.
   */
  public String randomEmptyEntry(Scoreboard scoreboard) {
    int index = ThreadLocalRandom.current().nextInt(1);
    int colorAmount = ThreadLocalRandom.current().nextInt(3);
    StringBuilder stringBuilder = new StringBuilder();
    Collection<String> forbidden = usedEntries(scoreboard);

    for (int i = 0; i < colorAmount; i++) {
      stringBuilder.append(randomChatColor());
    }

    if (forbidden.contains(stringBuilder.toString())) {
      return randomEmptyEntry(scoreboard);
    }

    return stringBuilder.toString();
  }

  /**
   * Checks which team entries are already in use inside the scoreboard.
   * This is important, because duplicate entries sometimes lead to
   * a buggy result, because entries that appear multiple times will
   * simply be cut out.
   *
   * @param scoreboard The scoreboard in which you want to search for the entries.
   * @return The collection of forbidden entry names.
   */
  private Collection<String> usedEntries(Scoreboard scoreboard) {
    Collection<String> output = Lists.newArrayList();

    // iterate all team names and add them to the output.
    for (Team current : scoreboard.getTeams()) {
      output.addAll(current.getEntries());
    }
    return output;
  }

  /**
   * Picks a random chat color from the bukkit enum.
   *
   * @return The final chat color enum.
   * @see ChatColor
   */
  private ChatColor randomChatColor() {
    int index = ThreadLocalRandom.current().nextInt(ChatColor.values().length - 1);
    return ChatColor.values()[index];
  }

}
