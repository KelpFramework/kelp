package de.pxav.kelp.core.configuration.parse.event;

public class ConfigPointer {

  private int line;
  private int column;

  public ConfigPointer(int line, int column) {
    this.line = line;
    this.column = column;
  }

  public int getLine() {
    return line;
  }

  public int getColumn() {
    return column;
  }

}
