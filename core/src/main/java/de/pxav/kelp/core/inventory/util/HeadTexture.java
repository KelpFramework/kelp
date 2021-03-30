package de.pxav.kelp.core.inventory.util;

import java.util.Base64;

/**
 * This class can be used to represent custom head textures used
 * by {@link de.pxav.kelp.core.inventory.metadata.SkullMetadata}.
 *
 * What you basically do when applying a custom texture to a head is getting
 * a skin from the Mojang database, fetch the skin texture of the head and put
 * it on the head. This Mojang database saves all player skins that have been applied to any player
 * at any time. Even if you change your skin, the old skin is retained in the database and
 * can be fetched via the same id. This is also the mechanics used by {@link de.pxav.kelp.core.npc.KelpNpc NPCs}
 * to have constant skin data even when the source player changes their skin.
 *
 * Internally, minecraft uses those skin ids from the database as well and this class
 * helps you to convert between the encoded format used by minecraft and the human-readable
 * url format.
 *
 * Every skin texture URL consists of this format:
 * {@code http://textures.minecraft.net/texture/<textureId>}
 *
 * So we have a constant part that remains the same for all skins, which is the URL prefix
 * (http://.../texture/) and a variable part, which is the texture id.
 *
 * When applying the skin to a head, we have to encode this URL to a {@code base64 String}.
 * The part for the prefix always stays the same and is represented by {@link #HTTP_PREFIX}.
 * It always begins with {@code eyJ...} and ends with {@code ...cmUv}. If you see the so-called
 * texture value on sites like <a href="https://minecraft-heads.com">Minecraft Heads</a>, you will
 * recognize this part of the value always is the same. The only thing that changes is the texture id,
 * which is directly appended to the URL prefix to get the final encoded string that can be read
 * by minecraft.
 *
 * @author pxav
 */
public class HeadTexture {

  private static final String HTTP_PREFIX = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";

  // default/preset head textures
  public static final HeadTexture BLUE_EASTER_EGG = new HeadTexture("MWIzMDgwMGQxYmRhNjE1MTBiYTYyZTJmMzQ2YjM5MjhhZTllZGRkYTRlNjNlOTM1ZjYwYTQ2NTM3NGVhZTYzZCJ9fX0=");
  public static final HeadTexture GIT_HUB_LOGO = new HeadTexture("MjZlMjdkYTEyODE5YThiMDUzZGEwY2MyYjYyZGVjNGNkYTkxZGU2ZWVlYzIxY2NmM2JmZTZkZDhkNDQzNmE3In19fQ==");
  public static final HeadTexture DRIED_KELP_BLOCK = new HeadTexture("YjgwNWNhZjNkMDJlMzVlNGFhZGMxOWFmMTVlODI3OTAxNzdmMWNkN2I3OWY0ZjViODhkOTQzYWM2YmUyMDNhMSJ9fX0=");
  public static final HeadTexture DISCORD_LOGO = new HeadTexture("ZGM1OWU1YzdiMDczOGI1NzlmM2I0NDRjMTNhNDdiZWQ0OTZiMzA4MzhiMmVlMmIxMjdjYzU5Y2Q3OThhZWU3NyJ9fX0=");
  public static final HeadTexture DISCORD_PING = new HeadTexture("YWQ4MzNiNTE1NjY1NjU2NThmOTAxMWRlODc4NGU5MGMxYWQ5YmE1ZDMzMzdmOGMwNjkyMTNiYmRlZTk4NjUyMyJ9fX0=");
  public static final HeadTexture PATREON_LOGO = new HeadTexture("NjQyMzQyNmY0NWNkMDk1NTVmMDA2Mzc3NmQ0MWE0MTBhOGUxNGYxNDcyZmU4OTgyODQ0ODVkMDk4ZTU4ZTU3ZiJ9fX0=");
  public static final HeadTexture BUKKIT_LOGO = new HeadTexture("NmZiNGM0NmQyZDM2NjYxMjdkNjIyYWM1MTk4NzE3YmExNjYzYjdjMTQ3YmMxMGZkMzcxNmQ3YWU3Y2NjMjkifX19");

  /**
   * Creates a new head texture object based on only the texture value.
   * This value should already be converted to a base64 string.
   *
   * If you are getting your head textures from <a href="https://minecraft-heads.com/>MC Heads</a>,
   * there is a field called {@code Loot table}, where you can find the {@code Value} of the skin.
   * Copy the part after the URL prefix, so after the {@code eyJ...cmUv} part (excluding the {@code /} at the end).
   *
   * This gives you the encoded texture value needed by this method.
   *
   * @param textureValue The encoded texture value (excluding the URL prefix) of the skin
   *                     texture you want to get.
   * @return The new {@code HeadTexture} object representing the texture.
   */
  public static HeadTexture fromEncodedId(String textureValue) {
    return new HeadTexture(textureValue);
  }

  /**
   * Creates a head texture object based on the completely base64 encoded URL.
   *
   * @param encodedValue The base64 encoded URL for the skin texture you want to apply.
   * @return The new head texture object.
   */
  public static HeadTexture fromEncodedURL(String encodedValue) {
    return new HeadTexture(encodedValue.replace(HTTP_PREFIX, ""));
  }

  /**
   * Creates a new head texture object from a decoded URL in the following
   * format: {@code http://textures.minecraft.net/texture/<textureId>}.
   *
   * @param decodedURL The decoded URL of the texture you want to get.
   * @return The new head texture object.
   */
  public static HeadTexture fromDecodedURL(String decodedURL) {
    return new HeadTexture("{\"textures\":{\"SKIN\":{\"url\":\"" + decodedURL);
  }

  /**
   * Creates a new head texture based on the decoded id used in the
   * decoded url.
   *
   * @param decodedId The decoded id of the texture you want to use.
   * @return The new head texture object.
   */
  public static HeadTexture fromDecodedId(String decodedId) {
    return new HeadTexture("{\"textures\":{\"SKIN\":{\"url\":\"http://textures.minecraft.net/texture/" + decodedId);
  }

  private String textureValue;

  private HeadTexture(String value) {
    this.textureValue = value;
  }

  /**
   * Takes the textureValue this object has been initialized with and converts
   * it to the encoded URL used by minecraft to apply the skin texture
   * to a head item.
   *
   * @return The base64 encoded URL that can be applied to a minecraft head.
   */
  public String getEncodedURL() {

    // if the value is already encoded and contains the encoded prefix, do nothing
    if (textureValue.contains(HTTP_PREFIX)) {
      return textureValue;
    }

    // if the value is decoded, but contains the http:// part, simply
    // encode it to base64.
    if (textureValue.contains("http://") || textureValue.contains("https://")) {
      // add the minecraft JSON opener if needed
      if (!textureValue.contains("{\"textures\":{\"SKIN\":{\"url\":\"")) {
        textureValue = "{\"textures\":{\"SKIN\":{\"url\":\"" + textureValue;
      }
      return Base64.getEncoder().encodeToString(textureValue.getBytes());
    }

    return HTTP_PREFIX + textureValue;
  }

}
