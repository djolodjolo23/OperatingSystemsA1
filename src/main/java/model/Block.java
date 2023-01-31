package model;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * The memory block class.
 */

public class Block implements Comparable<Block>{

  private int blockId;
  private ArrayList<Integer> allocatedBytes;

  private int beginningAddress;

  private int endingAddress;

  private int size;

  private boolean allocated;

  /**
   * Block object holding the list of bytes.
   *
   * @param blockId depends on command.
   */
  public Block(int blockId) {
    allocatedBytes = new ArrayList<>();
    this.blockId = blockId;
    this.allocated = true;
    this.beginningAddress = 0;
    this.endingAddress = 0;
  }

  public Block() {
    allocatedBytes = new ArrayList<>();
    this.allocated = false;
  }

  public int getBeginningAddress() {
    return beginningAddress;
  }

  public void setBeginningAddress(int beginningAddress) {
    this.beginningAddress = beginningAddress;
  }

  public int getEndingAddress() {
    return endingAddress;
  }

  public void setEndingAddress(int endingAddress) {
    this.endingAddress = endingAddress;
  }

  public void removeFromAllocatedBytes(Integer b) {
    allocatedBytes.remove(b);
  }

  public void clearAllocatedBytesList() {
    allocatedBytes.clear();
  }

  public void addToAllocatedBytes(Integer b) {
    allocatedBytes.add(b);
  }

  public void addListToAllocatedBytes(ArrayList<Integer> bytes) {
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

  public ArrayList<Integer> getAllocatedBytes() {
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
    int compareSize = o.getAllocatedBytes().get(0);
    return this.getAllocatedBytes().get(0) - compareSize ;
  }

}
