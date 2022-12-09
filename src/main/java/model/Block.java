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

  @Override
  public int compareTo(Block o) {
    int compareSize = o.getAllocatedBytes().get(0).getAddress();
    return this.getAllocatedBytes().get(0).getAddress() - compareSize ;
  }



  public static Comparator<Block> freeBlockSizeComparator = Comparator.comparingInt(o -> o.getAllocatedBytes().size());

}
