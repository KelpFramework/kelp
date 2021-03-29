package de.pxav.kelp.core.inventory.metadata;

import de.pxav.kelp.core.inventory.util.HeadTexture;

import java.util.Base64;

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
