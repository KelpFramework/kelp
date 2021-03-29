package de.pxav.kelp.core.inventory.util;

import java.util.Base64;

public class HeadTexture {

  private static final String HTTP_PREFIX = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";

  public static final HeadTexture BLUE_EASTER_EGG = new HeadTexture("MWIzMDgwMGQxYmRhNjE1MTBiYTYyZTJmMzQ2YjM5MjhhZTllZGRkYTRlNjNlOTM1ZjYwYTQ2NTM3NGVhZTYzZCJ9fX0=");
  public static final HeadTexture GIT_HUB_LOGO = new HeadTexture("MjZlMjdkYTEyODE5YThiMDUzZGEwY2MyYjYyZGVjNGNkYTkxZGU2ZWVlYzIxY2NmM2JmZTZkZDhkNDQzNmE3In19fQ==");
  public static final HeadTexture DRIED_KELP_BLOCK = new HeadTexture("YjgwNWNhZjNkMDJlMzVlNGFhZGMxOWFmMTVlODI3OTAxNzdmMWNkN2I3OWY0ZjViODhkOTQzYWM2YmUyMDNhMSJ9fX0=");
  public static final HeadTexture DISCORD_LOGO = new HeadTexture("ZGM1OWU1YzdiMDczOGI1NzlmM2I0NDRjMTNhNDdiZWQ0OTZiMzA4MzhiMmVlMmIxMjdjYzU5Y2Q3OThhZWU3NyJ9fX0=");
  public static final HeadTexture DISCORD_PING = new HeadTexture("YWQ4MzNiNTE1NjY1NjU2NThmOTAxMWRlODc4NGU5MGMxYWQ5YmE1ZDMzMzdmOGMwNjkyMTNiYmRlZTk4NjUyMyJ9fX0=");
  public static final HeadTexture PATREON_LOGO = new HeadTexture("NjQyMzQyNmY0NWNkMDk1NTVmMDA2Mzc3NmQ0MWE0MTBhOGUxNGYxNDcyZmU4OTgyODQ0ODVkMDk4ZTU4ZTU3ZiJ9fX0=");
  public static final HeadTexture BUKKIT_LOGO = new HeadTexture("NmZiNGM0NmQyZDM2NjYxMjdkNjIyYWM1MTk4NzE3YmExNjYzYjdjMTQ3YmMxMGZkMzcxNmQ3YWU3Y2NjMjkifX19");

  public static HeadTexture custom(String textureValue) {
    return new HeadTexture(textureValue);
  }

  public static HeadTexture fromEncodedURL(String encodedValue) {
    return new HeadTexture(encodedValue.replace(HTTP_PREFIX, ""));
  }

  public static HeadTexture fromURL(String decodedURL) {
    return new HeadTexture(Base64.getEncoder().encodeToString(decodedURL.getBytes()));
  }

  private String textureValue;

  HeadTexture(String value) {
    this.textureValue = value;
  }

  public String getTextureValue() {
    return textureValue;
  }

  public String getEncodedURL() {
    return HTTP_PREFIX + textureValue;
  }

}
