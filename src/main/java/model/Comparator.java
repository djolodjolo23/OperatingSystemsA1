package model;

/**
 * A class created for sorting the Blocks in a various way.
 */
public class Comparator {

  public static java.util.Comparator<Block> freeBlockSizeComparatorAscending = java.util.Comparator.comparingInt(o -> o.getAllocatedBytes().size());

  public static java.util.Comparator<Block> freeBlockSizeComparatorDescending = (o1, o2) -> o2.getAllocatedBytes().size() - o1.getAllocatedBytes().size();

  public static java.util.Comparator<Block> byteAddressSort = java.util.Comparator.comparingInt(
      o -> o.getAllocatedBytes().get(0));

  public static java.util.Comparator<Block> sizeSort = java.util.Comparator.comparingInt(Block::getSize);

  public static java.util.Comparator<Block> sizeSortDescending = (o1, o2) -> o2.getSize() - o1.getSize();

  public static java.util.Comparator<Block> sizeSortAscending = (o1, o2) -> o1.getSize() - o2.getSize();

  public static java.util.Comparator<Block> beginningAddressSortAscending = (o1, o2) -> o1.getBeginningAddress() - o2.getBeginningAddress();



  public static java.util.Comparator<Block> idSort = java.util.Comparator.comparingInt(Block::getBlockId);

}
