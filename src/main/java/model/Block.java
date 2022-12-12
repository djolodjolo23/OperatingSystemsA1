package model;

import java.util.ArrayList;
import java.util.Comparator;
import model.Byte;

public class Block implements Comparable<Block>{

  private int blockId;
  private ArrayList<Byte> allocatedBytes;

  private int size;

  private boolean allocated;

  public Block(int blockId) {
    allocatedBytes = new ArrayList<>();
    this.blockId = blockId;
    this.allocated = true;
  }

  public Block() {
    allocatedBytes = new ArrayList<>();
    this.allocated = false;
  }
  public void removeFromAllocatedBytes(Byte b) {
    allocatedBytes.remove(b);
  }

  public void addToAllocatedBytes(Byte b) {
    allocatedBytes.add(b);
  }

  public void addListToAllocatedBytes(ArrayList<Byte> bytes) {
    allocatedBytes.addAll(bytes);
  }

  public boolean isAllocated() {
    return allocated;
  }

  public void setAllocated(boolean value) {
    this.allocated = value;
  }

  public int getBlockId() {
    return blockId;
  }

  public ArrayList<Byte> getAllocatedBytes() {
    return allocatedBytes;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }

  public void setBlockId(int blockId) {
    this.blockId = blockId;
  }

  @Override
  public int compareTo(Block o) {
    int compareSize = o.getAllocatedBytes().get(0).getAddress();
    return this.getAllocatedBytes().get(0).getAddress() - compareSize ;
  }

  public static Comparator<Block> freeBlockSizeComparatorAscending = Comparator.comparingInt(o -> o.getAllocatedBytes().size());

  public static Comparator<Block> freeBlockSizeComparatorDescending = (o1, o2) -> o2.getAllocatedBytes().size() - o1.getAllocatedBytes().size();

  public static Comparator<Block> byteAddressSort = Comparator.comparingInt(
      o -> o.getAllocatedBytes().get(0).getAddress());


}
