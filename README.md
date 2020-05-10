# Kelp

> Developed by pxav (c) 2019-2020

![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/PXAV/kelp?include_prereleases&label=version)
![GitHub issues](https://img.shields.io/github/issues/PXAV/finate-studio-java)
![GitHub pull requests](https://img.shields.io/github/issues-pr/PXAV/kelp)
![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/PXAV/kelp)
![Development Indicator](https://img.shields.io/badge/development-active-brightgreen)

### What is Kelp?

Kelp is an open-source framework for Spigot/Bukkit servers to simplify plugin development and increase development speed. This is achieved by reducing boiler-plate code to a minimum, increasing readability to a maximum and avoid version-specific code.



### The problem with the current spigot plugin development process 

The Spigot API has limited or even no backwards compatibility. Material and sound names change almost in every version, NMS packages are named after their version and require reflections to make them compatible, etc. Adjusting old code to new API versions requires lots of time & effort, which is why many developers don't upgrade their plugins anymore, although many server owners still rely on them. So server owners won't upgrade to the latest Minecraft versions and new ideas can't be realized. 

Furthermore, developers have to use lots of workarounds to achieve certain goals. If you want to create a non-flickering sidebar, you have to save the updateable content into a team prefix, put it into  the sidebar and update the prefix. New developers might not understand why there are teams in the scoreboard, which often causes confusion.



### How Kelp solves all these problems 

Kelp is built modularly. It consists of one core module, which only contains Java-logic and no version-specific code, so it will run on every spigot server version. It contains so-called template classes, which are basically dummies with empty methods doing version specific stuff. The server owner then puts a version-implementation module in a specific folder. This file contains implementations of all the template classes from the core module. It is detected by the core module and every template class is replaced with its version implementation class. So the core module can load version implementations for every version and can run on any version without changing any line in the code-base.



In addition it reduces boiler-plate code and increases readability. Here is an example of how easy NPC creation can be with Kelp:

```java
@EventHandler
public void handlePlayerJoin(PlayerJoinEvent event) {
  
  // create new npc instance
  KelpNpc kelpNpc = npcFactory.newKelpNpc()
          .itemInHand(kelpItemFactory.newKelpItem() // the NPC is holding an apple
                  .material(KelpMaterial.APPLE))    // in its hand
          .addTitleLine("§bThis is an epic NPC") // set titles above the npc
          .addTitleLine("§bCreate as many lines as you want")
          .imitateSneaking() // the npc will sneak when the player sneaks
          .followHeadRotation(); // the npc will always look at the player
  
  // finally make the npc visible to the player
  kelpNpc.spawn(event.getPlayer());
    
}
```



Compare this to complicated and version-specific packet handling, reflections and DataWatcher-manipulation:

```java
PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
PacketPlayOutNamedEntitySpawn spawnPacket = new PacketPlayOutNamedEntitySpawn();
int entityId = ThreadLocalRandom.current().nextInt(10_000) + 2_000;

GameProfile gameProfile = new GameProfile(npc.getUuid(), npc.getCustomName());

setValue(spawnPacket, "a", entityId);
setValue(spawnPacket, "b", gameProfile.getId());
setValue(spawnPacket, "c", MathHelper.floor(npc.getLocation().getX() * 32.0D));
setValue(spawnPacket, "d", MathHelper.floor(npc.getLocation().getY() * 32.0D));
setValue(spawnPacket, "e", MathHelper.floor(npc.getLocation().getZ() * 32.0D));
setValue(spawnPacket, "f", (byte) ((int) (npc.getLocation().getYaw() * 256.0F / 360.0F)));
setValue(spawnPacket, "g", (byte) ((int) (npc.getLocation().getPitch() * 256.0F / 360.0F)));
setValue(spawnPacket, "h", npc.getItemInHand().getItemStack().getType().getId());

DataWatcher dataWatcher = new DataWatcher(null);
dataWatcher.a(0, (byte) 0x02);
setValue(spawnPacket, "i", dataWatcher);

playerConnection.sendPacket(spawnPacket);
...

```





### Feature overview

- **Sidebar system:** Create sidebars - no matter if animated or just simple & casual. Use up to 32 chars per line and enjoy flicker-free updating
- **Inventory system:** Create stunning GUIs with minimal effort, use an integrated listener system and meet helpful pre-defined or custom widgets. Never mess with changed material names anymore.
- **NPC system:** Create NPCs with ease and give them abilities like sneaking or give them effects and animations. 
- **Particle engine:** coming soon



### Support

There are version implementations for the following version implementations available:

| Server Version | Supported | Notes                 |
| -------------- | --------- | --------------------- |
| 1.8            | ✅         | All features included |
| 1.9            | ❌         | Following soon        |
| 1.10           | ❌         | Following soon        |
| 1.11           | ❌         | Following soon        |
| 1.12           | ❌         | Following soon        |
| 1.13           | ❌         | Following soon        |
| 1.14           | ❌         | Work in progress      |
| 1.15           | ❌         | Following soon        |



### Build from source

To build all Kelp modules you first have to compile all CraftBukkit versions from 1.8 to 1.14 for yourself using the official BuildTools.jar. The CraftBukkit library may not be distributed by Kelp for legal reasons. A tutorial can be found [here](https://www.spigotmc.org/wiki/buildtools/).

```shell
java -jar BuildTools.jar --rev <version>
```



Then, clone the repo from GitHub and build it using maven

```shell
git clone https://github.com/PXAV/kelp.git
cd kelp
mvn install
```



### How to use

#### For server owners

Put the jar file of the core module into the normal `plugins` folder of your server. Start the server and wait until Kelp has generated the required folders. Pick the right version module for your server and drag it into the `kelp_versions` folder. Reload/Restart the server and Kelp should be ready to use. **Note:** Plugins based on Kelp go into the `kelp_plugins` folder!



#### For developers

A detailed wiki and documentation is following soon.



### Contributing

Currently Kelp is developed by a single developer and bug reports, feature suggestions and pull requests are very welcome. If you want to contribute to the code, have a look at our [contribution guidelines](CONTRIBUTING.md). If you have bug reports, ideas, or need support feel free to open a new issue here on GitHub. 

