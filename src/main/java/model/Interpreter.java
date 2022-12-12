package model;

import java.io.IOException;
import java.util.ArrayList;
import strategy.AbstractStrategyFactory;

public class Interpreter {

  private RegistryReader registryReader;

  private ArrayList<Byte> allBytes;

  private ArrayList<Block> allBlocks;

  private ArrayList<Error> allErrors;

  private int intermediateOutputCounter;

  private InterpreterAssistant interpreterAssistant;


  public Interpreter(RegistryReader registryReader) {
    this.registryReader = registryReader;
    this.interpreterAssistant = new InterpreterAssistant();
    allBytes = new ArrayList<>();
    allBlocks = new ArrayList<>();
    allErrors = new ArrayList<>();
  }

  public void go(AbstractStrategyFactory strategyFactory) throws IOException {
    strategyFactory.getFirstFitRule(this, registryReader);
    strategyFactory.getBestFitRule(this,registryReader);
    strategyFactory.getWorstFitRule(this, registryReader);
  }

  public void clearAllLists() {
    allBytes.clear();
    allBlocks.clear();
    allErrors.clear();
  }

  public void addToAllBytes(Byte b) {
    allBytes.add(b);
  }

  public void removeFromAllBytes(Byte b) {
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

  public void setByteToAllocated(int index) {
    allBytes.get(index).setAllocated(true);
  }

  public void addListToAllBlocks(ArrayList<Block> blocks) {
    allBlocks.addAll(blocks);
  }

  public void addListToAllErrors(ArrayList<Error> errors) { allErrors.addAll(errors); }

  public void addListToALlBytes(ArrayList<Byte> bytes) {
    allBytes.addAll(bytes);
  }

  public ArrayList<Byte> getAllBytes() {
    return allBytes;
  }

  public ArrayList<Block> getAllBlocks() {
    return allBlocks;
  }

  public int getIntermediateOutputCounter() {
    return intermediateOutputCounter;
  }

  public void setIntermediateOutputCounter(int intermediateOutputCounter) {
    this.intermediateOutputCounter = intermediateOutputCounter;
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

  public Byte getSpecificByte(int address) {
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

  public Block getSpecificBlock(int blockId) {
    return interpreterAssistant.getSpecificBlock(blockId, this);
  }

  public void removeListFromAllBytes(ArrayList<Byte> bytes) {
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
