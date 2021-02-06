package de.pxav.kelp.core.npc;

public enum MovementSpeed {

  WALKING(4.317, .4317, .21585),
  SPRINTING(5.612, .5612, .2806),
  SNEAKING(1.295, .1295, .06475),
  FALLING(78.4, 7.84, 3.92),
  FLYING(10.89, 1.089, .5445),
  FLYING_SPRINTING(21.78, 2.178, 1.089);

  private double blocksPerSecond;
  private double blocksPerNpcTick;
  private double blocksPerTick;

  MovementSpeed(double blocksPerSecond,
                double blocksPerNpcTick,
                double blocksPerTick) {
    this.blocksPerSecond = blocksPerSecond;
    this.blocksPerNpcTick = blocksPerNpcTick;
    this.blocksPerTick = blocksPerTick;
  }

  public static double getNpcSpeed(KelpNpc kelpNpc) {
    if (kelpNpc.isSneaking()) {
      return SNEAKING.getBlocksPerNpcTick();
    } else if (kelpNpc.isSprinting() && !kelpNpc.isFlying()) {
      return SPRINTING.getBlocksPerNpcTick();
    } else if (kelpNpc.isFalling()) {
      return FALLING.getBlocksPerNpcTick();
    } else if (kelpNpc.isFlying() && !kelpNpc.isSprinting()) {
      return WALKING.getBlocksPerNpcTick();
    } else if (kelpNpc.isFlying() && kelpNpc.isSprinting()) {
      return FLYING_SPRINTING.getBlocksPerNpcTick();
    }
    return WALKING.getBlocksPerNpcTick();
  }

  public double getBlocksPerNpcTick() {
    return blocksPerNpcTick;
  }

  public double getBlocksPerSecond() {
    return blocksPerSecond;
  }

  public double getBlocksPerTick() {
    return blocksPerTick;
  }

}
