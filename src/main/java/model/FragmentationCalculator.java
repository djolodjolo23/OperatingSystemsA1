package model;

public abstract class FragmentationCalculator {

  public double calculate(double biggestFreeBlock, int totalFreeMemory) {
    return (double)Math.round((1 - (biggestFreeBlock / totalFreeMemory)) * 1000000d) / 1000000d;
  }

}
