# v0.0.3
> Release date: 12.09.2020 

**The particle engine update**:
* Kelp can now be referenced through a build tool like Maven or Gradle as it is now part of the Maven Central Repository.
* Create `ParticleType` class that maps particle type names across different versions.
* Create version template `ParticleVersionTemplate` and a v1.8 implementation for version independent particle spawning
* Create particle effect system to spawn complex particle effects.
* If you request a `ScheduledExecutorService` in a constructor annotated with `@Inject` by Guice, a new scheduled thread pool will be injected automatically.