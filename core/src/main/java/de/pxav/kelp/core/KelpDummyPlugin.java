package de.pxav.kelp.core;

import de.pxav.kelp.core.application.KelpApplication;

/**
 * This class is used to pass a dummy plugin instance when developing
 * inside the core module.
 *
 * For some things like {@link de.pxav.kelp.core.world.KelpChunk#addForceLoadFlag(Class)} you
 * need to pass a {@link KelpApplication} class representing your plugin, but the kelp core
 * obviously is no Kelp plugin, but a spigot plugin ({@link org.bukkit.plugin.java.JavaPlugin}).
 *
 * So if you ever run into such scenarios as a KelpCore developer, use this
 * class.
 *
 * @author pxav
 */
public class KelpDummyPlugin extends KelpApplication {}
