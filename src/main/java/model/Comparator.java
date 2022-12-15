package model;

public class Comparator {

  public static java.util.Comparator<Block> freeBlockSizeComparatorAscending = java.util.Comparator.comparingInt(o -> o.getAllocatedBytes().size());

  public static java.util.Comparator<Block> freeBlockSizeComparatorDescending = (o1, o2) -> o2.getAllocatedBytes().size() - o1.getAllocatedBytes().size();

  public static java.util.Comparator<Block> byteAddressSort = java.util.Comparator.comparingInt(
      o -> o.getAllocatedBytes().get(0));

}
