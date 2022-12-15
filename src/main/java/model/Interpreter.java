package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import strategy.AbstractStrategyFactory;

public class Interpreter {


  private ArrayList<Integer> allBytes;

  private ArrayList<Block> allBlocks;

  private ArrayList<Error> allErrors;

  private InterpreterAssistant interpreterAssistant;


  public Interpreter() {
    this.interpreterAssistant = new InterpreterAssistant();
    allBytes = new ArrayList<>();
    allBlocks = new ArrayList<>();
    allErrors = new ArrayList<>();
  }



  public void clearAllLists() {
    allBytes.clear();
    allBlocks.clear();
    allErrors.clear();
  }

  public void addListToAllBytes(ArrayList<Integer> listOfBytes) {
    allBytes.addAll(listOfBytes);
  }

  public void addToAllBytes(Integer b) {
    allBytes.add(b);
  }

  public void removeFromAllBytes(Integer b) {
    allBytes.remove(b);
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

  public void addListToAllBlocks(ArrayList<Block> blocks) {
    allBlocks.addAll(blocks);
  }


  public void addListToAllErrors(ArrayList<Error> errors) { allErrors.addAll(errors); }


  public ArrayList<Integer> getAllBytes() {
    return allBytes;
  }

  public ArrayList<Block> getAllBlocks() {
    return allBlocks;
  }


  public ArrayList<Block> getAllBlocksWithBytes() {
    return interpreterAssistant.getAllBlocksWithBytes(this);
  }

  public void connectFreeBlocks() {
    interpreterAssistant.connectFreeBlocks(this);
  }

  public ArrayList<Error> getAllErrors() {
    return allErrors;
  }

  public ArrayList<Integer> getAllErrorsIds() {
    return interpreterAssistant.getAllErrorsIds(this);
  }

  public Integer getSpecificByte(int address) {
    return interpreterAssistant.getSpecificByte(address, this);
  }


  public double getBiggestFreeBlockSize() {
    return interpreterAssistant.getBiggestFreeBlockSize(this);
  }

  public Block getBiggestFreeBlock() {
    return interpreterAssistant.getBiggestFreeBlock(this);
  }

  public int getTotalFreeMemory() {
    return interpreterAssistant.getTotalFreeMemory(this);
  }

  public void removeAllFreeBlocks() {
    allBlocks.removeIf(b -> !b.isAllocated());
  }

  public Block getSpecificBlock(int blockId) {
    return interpreterAssistant.getSpecificBlock(blockId, this);
  }

  public void removeListFromAllBytes(ArrayList<Integer> bytes) {
    allBytes.removeAll(bytes);
  }

  public Block getFirstBestOrWorstFreeBlockWithEnoughMemory(int memory, char fitType) {
    return interpreterAssistant.getFirstBestOrWorstFreeBlockWithEnoughMemory(memory, fitType, this);
  }

  public ArrayList<Integer> getCurrentBlockIds() {
    return interpreterAssistant.getCurrentBlockIds(this);
  }

  public ArrayList<Integer> getFreeByteAddresses() {
    return interpreterAssistant.getFreeByteAddresses(this);
  }

}
