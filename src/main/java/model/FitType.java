package model;


public enum FitType {
  FIRST('F'), BEST('B'), WORST('W');

  private final char value;

  public char getValue() {
    return value;
  }

  FitType(char value) {
    this.value = value;
  }
}