package model;

public interface IntegerChecker {

  default boolean integerCheck(String numberAsString) {
    try {
      Integer.parseInt(numberAsString);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }
}
