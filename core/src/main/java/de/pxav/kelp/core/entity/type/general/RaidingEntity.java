package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.world.KelpBlock;

public interface RaidingEntity<T extends RaidingEntity<?>> extends MonsterEntity<T> {

  KelpBlock getPatrolTarget();

  T setPatrolTarget(KelpBlock patrolTarget);

  boolean canJoinRaid();

  boolean isPatrolLeader();

  T setCanJoinRaid(boolean canJoinRaid);

  T setPatrolLeader(boolean patrolLeader);

}
