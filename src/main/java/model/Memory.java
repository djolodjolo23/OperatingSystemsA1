package model;

import java.util.ArrayList;


/**
 * The interpreter class, can be considered as a "Memory" class.
 */
public class Memory {


  private ArrayList<Integer> allBytes;

  private ArrayList<Block> allBlocks;

  private ArrayList<Error> allErrors;

  private MemoryAssistant memoryAssistant;


  /**
   * The object interpreter is the holder of all bytes, blocks and errors in the memory.
   */
  public Memory() {
    this.memoryAssistant = new MemoryAssistant();
    allBytes = new ArrayList<>();
    allBlocks = new ArrayList<>();
    allErrors = new ArrayList<>();
  }

  /**
   * Clears all the lists.
   * Used between the Fit switching.
   */
  public void clearAllLists() {
    allBytes.clear();
    allBlocks.clear();
    allErrors.clear();
  }

  public void addListToAllBytes(ArrayList<Integer> listOfBytes) {
    allBytes.addAll(listOfBytes);
  }

  public void addToAllBlocks(Block b) {
    allBlocks.add(b);
  }

  public void removeFromAllBlocks(Block block) {
    allBlocks.remove(block);
  }

  public void addToAllErrors(Error e) {
    allErrors.add(e);
  }

  public void addListToAllErrors(ArrayList<Error> errors) { allErrors.addAll(errors); }


  public ArrayList<Integer> getAllBytes() {
    return allBytes;
  }

  public ArrayList<Block> getAllBlocks() {
    return allBlocks;
  }


  public ArrayList<Block> getAllBlocksWithBytes() {
    return memoryAssistant.getAllBlocksWithBytes(this);
  }

  public void connectFreeBlocks() {
    memoryAssistant.connectFreeBlocks(this);
  }

  public ArrayList<Error> getAllErrors() {
    return allErrors;
  }

  public ArrayList<Integer> getAllErrorsIds() {
    return memoryAssistant.getAllErrorsIds(this);
  }

  public double getBiggestFreeBlockSize() {
    return memoryAssistant.getBiggestFreeBlockSize(this);
  }

  public Block getBiggestFreeBlock() {
    return memoryAssistant.getBiggestFreeBlock(this);
  }

  public int getTotalFreeMemory() {
    return memoryAssistant.getTotalFreeMemory(this);
  }

  public void removeAllFreeBlocks() {
    allBlocks.removeIf(b -> !b.isAllocated());
  }

  public Block getSpecificBlock(int blockId) {
    return memoryAssistant.getSpecificBlock(blockId, this);
  }

  public void removeListFromAllBytes(ArrayList<Integer> bytes) {
    allBytes.removeAll(bytes);
  }

  public Block getFirstBestOrWorstFreeBlockWithEnoughMemory(int memory, char fitType) {
    return memoryAssistant.getFirstBestOrWorstFreeBlockWithEnoughMemory(memory, fitType, this);
  }

}
