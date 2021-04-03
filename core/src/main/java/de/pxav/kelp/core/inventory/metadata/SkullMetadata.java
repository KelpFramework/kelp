package de.pxav.kelp.core.inventory.metadata;

import de.pxav.kelp.core.inventory.util.HeadTexture;

import java.util.Base64;

/**
 * Represents the metadata of all items of type {@link de.pxav.kelp.core.inventory.material.KelpMaterial#PLAYER_HEAD}.
 * It allows you to apply custom textures or player skins to player heads, which would normally just have a Steve or
 * Alex skin.
 *
 * If you want to apply the skin of a player that is currently on the server, you can use {@link #setSkullOwner(String)}.
 *
 * If you want to apply a custom texture or the skin of an offline player to the skull, you have to use mojang textures
 * encoded in base 64 using one of the corresponding methods such as {@link #textureURL(String)} or {@link #textureURLbase64(String)}.
 * Everytime a player applies a new skin, this skin is saved forever in the mojang database and can be retrieved via
 * the corresponding URL, which is written in this format:
 * {@code http://textures.minecraft.net/texture/<textureId>}
 *
 * So you can see the URL basically consists of two parts:
 * - the URL prefix (http and textures.minecraft.net/...)
 * - the actual texture id.
 *
 * If you compile those components into a base64 String, you will see there is always a
 * URL prefix ({@link HeadTexture#HTTP_PREFIX})
 *
 *
 * @author pxav
 */
public class SkullMetadata implements ItemMetadata {

  private String skullOwner;
  private String textureURL;

  public static SkullMetadata create() {
    return new SkullMetadata();
  }

  public String getSkullOwner() {
    return skullOwner;
  }

  public String getTextureURL() {
    return textureURL;
  }

  public SkullMetadata textureURL(String textureURL) {
    this.textureURL = Base64.getEncoder().encodeToString(textureURL.getBytes());
    return this;
  }

  public SkullMetadata textureURLbase64(String base64) {
    this.textureURL = textureURL;
    return this;
  }

  public SkullMetadata texture(HeadTexture texture) {
    this.textureURL = texture.getEncodedURL();
    return this;
  }

  public SkullMetadata setSkullOwner(String skullOwner) {
    this.skullOwner = skullOwner;
    return this;
  }

}
