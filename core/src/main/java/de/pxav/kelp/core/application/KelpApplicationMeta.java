package de.pxav.kelp.core.application;

import java.io.File;
import java.util.Collection;
import java.util.Set;

/**
 * This class represents the information about a
 * kelp application.
 *
 * @author pxav
 * @see NewKelpApplication
 */
public class KelpApplicationMeta {

  private String applicationName;
  private String version;
  private String description;
  private String main;
  private Set<String> hardDependencies;
  private Set<String> softDependencies;
  private Collection<String> authors;
  private File file;

  public KelpApplicationMeta(String applicationName,
                             String version,
                             String description,
                             Collection<String> authors,
                             File file) {
    this.applicationName = applicationName;
    this.version = version;
    this.description = description;
    this.authors = authors;
    this.file = file;
  }

  public KelpApplicationMeta() {}

  public String getApplicationName() {
    return applicationName;
  }

  public KelpApplicationMeta applicationName(String applicationName) {
    this.applicationName = applicationName;
    return this;
  }

  public String getVersion() {
    return version;
  }

  public KelpApplicationMeta version(String version) {
    this.version = version;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public KelpApplicationMeta description(String description) {
    this.description = description;
    return this;
  }

  public String getMain() {
    return main;
  }

  public KelpApplicationMeta main(String main) {
    this.main = main;
    return this;
  }

  public Set<String> getHardDependencies() {
    return hardDependencies;
  }

  public KelpApplicationMeta hardDependencies(Set<String> hardDependencies) {
    this.hardDependencies = hardDependencies;
    return this;
  }

  public Set<String> getSoftDependencies() {
    return softDependencies;
  }

  public KelpApplicationMeta softDependencies(Set<String> softDependencies) {
    this.softDependencies = softDependencies;
    return this;
  }

  public Collection<String> getAuthors() {
    return authors;
  }

  public KelpApplicationMeta authors(Collection<String> authors) {
    this.authors = authors;
    return this;
  }

  public File getFile() {
    return file;
  }

  public KelpApplicationMeta file(File file) {
    this.file = file;
    return this;
  }

}
