package model;

import java.util.ArrayList;
import model.Byte;

public class Block implements Comparable<Block>{

  private int blockId;
  private ArrayList<Byte> allocatedBytes;

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

  @Override
  public int compareTo(Block o) {
    int compareSize = o.getAllocatedBytes().size();
    return compareSize - this.getAllocatedBytes().size() ;
  }
}
