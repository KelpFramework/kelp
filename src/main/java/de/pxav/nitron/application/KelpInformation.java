package de.pxav.nitron.application;

import java.io.File;
import java.util.Collection;
import java.util.Set;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpInformation {

  private String applicationName;
  private String version;
  private String description;
  private String main;
  private Set<String> hardDependencies;
  private Set<String> softDependencies;
  private Collection<String> authors;
  private File file;

  public KelpInformation(String applicationName,
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

  public KelpInformation() {}

  public String getApplicationName() {
    return applicationName;
  }

  public KelpInformation applicationName(String applicationName) {
    this.applicationName = applicationName;
    return this;
  }

  public String getVersion() {
    return version;
  }

  public KelpInformation version(String version) {
    this.version = version;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public KelpInformation description(String description) {
    this.description = description;
    return this;
  }

  public String getMain() {
    return main;
  }

  public KelpInformation main(String main) {
    this.main = main;
    return this;
  }

  public Set<String> getHardDependencies() {
    return hardDependencies;
  }

  public KelpInformation hardDependencies(Set<String> hardDependencies) {
    this.hardDependencies = hardDependencies;
    return this;
  }

  public Set<String> getSoftDependencies() {
    return softDependencies;
  }

  public KelpInformation softDependencies(Set<String> softDependencies) {
    this.softDependencies = softDependencies;
    return this;
  }

  public Collection<String> getAuthors() {
    return authors;
  }

  public KelpInformation authors(Collection<String> authors) {
    this.authors = authors;
    return this;
  }

  public File getFile() {
    return file;
  }

  public KelpInformation file(File file) {
    this.file = file;
    return this;
  }

}
