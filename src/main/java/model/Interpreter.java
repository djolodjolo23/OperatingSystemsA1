package model;

import java.io.IOException;
import java.util.ArrayList;
import strategy.AbstractStrategyFactory;
import strategy.FitStrategy;

public class Interpreter {

  private RegistryReader registryReader;

  private ArrayList<Byte> allBytes;

  private ArrayList<Block> allBlocks;

  private ArrayList<Error> allErrors;


  public Interpreter(RegistryReader registryReader) {
    this.registryReader = registryReader;
    allBytes = new ArrayList<>();
    allBlocks = new ArrayList<>();
    allErrors = new ArrayList<>();
  }

  public void go(AbstractStrategyFactory strategyFactory) throws IOException {
    FitStrategy firstFit = strategyFactory.getFirstFitRule(this, registryReader);
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

  public void addToAllErrors(Error e) {
    allErrors.add(e);
  }

  public void addListToAllBlocks(ArrayList<Block> blocks) {
    allBlocks.addAll(blocks);
  }

  public void addListToALlBytes(ArrayList<Byte> bytes) {
    allBytes.addAll(bytes);
  }

  public ArrayList<Byte> getAllBytes() {
    return allBytes;
  }

  public ArrayList<Block> getAllBlocks() {
    return allBlocks;
  }

  public ArrayList<Block> getAllFreeBlocks() {
    ArrayList<Block> freeBlocks = new ArrayList<>();
    for (Block block : allBlocks) {
      if (!block.isAllocated() && block.getAllocatedBytes().isEmpty()) {
        freeBlocks.add(block);
      }
    }
    return freeBlocks;
  }

  public ArrayList<Error> getAllErrors() {
    return allErrors;
  }

  public Byte getSpecificByte(int address) {
    for (Byte b : allBytes) {
      if (b.getAddress() == address) {
        return b;
      }
    }
    return null;
  }

  public double getBiggestFreeBlock() {
    ArrayList<ArrayList<Byte>> listOfLists = new ArrayList<>();
    for (Block b : allBlocks) {
      if (!b.isAllocated()) {
        if (!b.getAllocatedBytes().isEmpty()) {
          listOfLists.add(b.getAllocatedBytes());
        }
      }
    }
    double max = listOfLists.get(0).size()-1;
    for (ArrayList<Byte> list : listOfLists) {
      if (list.size() >= max) {
        max = list.size()-1;
      }
    }
    return max;
  }

  public int getTotalFreeMemory() {
    ArrayList<Byte> bytes = new ArrayList<>();
    for (Byte b : allBytes) {
      if (!b.isAllocated()) {
        bytes.add(b);
      }
    }
    return bytes.size()-1;
  }



  public ArrayList<Integer> getFreeByteAddresses() {
    ArrayList<Integer> freeAddresses = new ArrayList<>();
    for (Byte b : allBytes) {
      freeAddresses.add(b.getAddress());
    }
    return freeAddresses;
  }


}
