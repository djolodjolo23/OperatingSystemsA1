package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.plaf.PanelUI;
import strategy.AbstractStrategyFactory;
import strategy.FitStrategy;

public class Interpreter {

  private RegistryReader registryReader;

  private ArrayList<Byte> allBytes;

  private ArrayList<Block> allBlocks;

  private ArrayList<Error> allErrors;

  private int intermediateOutputCounter;


  public Interpreter(RegistryReader registryReader) {
    this.registryReader = registryReader;
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
    ArrayList<Block> blocksWithBytes = new ArrayList<>();
    for (Block b : allBlocks) {
      if (!b.getAllocatedBytes().isEmpty()) {
        blocksWithBytes.add(b);
      }
    }
    return blocksWithBytes;
  }

  public void connectFreeBlocks() {
    int counter = 0;
    ArrayList<Block> freeBlocks = new ArrayList<>();
    for (Block b : allBlocks) {
      if (!b.isAllocated()) {
        freeBlocks.add(b);
      }
    }
    freeBlocks.sort(Block.byteAddressSort);
    for (int i = 0; i < freeBlocks.size(); i++) {
      Block theBlock = freeBlocks.get(counter);
      ArrayList<Byte> allocatedBytes = theBlock.getAllocatedBytes();
      counter++;
      if (counter == freeBlocks.size()) {
        break;
      }
      Block nextBlock = freeBlocks.get(counter);
      if (allocatedBytes.get(allocatedBytes.size() -1).getAddress() + 1 == nextBlock.getAllocatedBytes().get(0).getAddress()) {
        Block newBlock = new Block();
        newBlock.addListToAllocatedBytes(theBlock.getAllocatedBytes());
        newBlock.addListToAllocatedBytes(nextBlock.getAllocatedBytes());
        allBlocks.remove(theBlock);
        allBlocks.remove(nextBlock);
        allBlocks.add(newBlock);
        counter++;
      }
    }
  }



  public ArrayList<Error> getAllErrors() {
    return allErrors;
  }

  public ArrayList<Integer> getAllErrorsIds() {
    ArrayList<Integer> errorIds = new ArrayList<>();
    for (Error error : allErrors) {
      errorIds.add(error.getBlockId());
    }
    return errorIds;
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

  public Block getBiggestFreeBlockTest() {
    ArrayList<Block> blocks = new ArrayList<>();
    for (Block b : allBlocks) {
      if (!b.isAllocated()) {
        blocks.add(b);
      }
    }
    blocks.sort(Block.freeBlockSizeComparatorDescending);
    return blocks.get(0);
  }

  public int getTotalFreeMemory() {
    ArrayList<Byte> bytes = new ArrayList<>();
    for (Block block : allBlocks) {
      for (Byte b : block.getAllocatedBytes()) {
        if (!b.isAllocated()) {
          bytes.add(b);
        }
      }
    }
    return bytes.size()-1;
  }

  public Block getSpecificBlock(int blockId) {
    for (Block b : allBlocks) {
      if (b.getBlockId() == blockId) {
        return b;
      }
    }
    return null;
  }

  public void removeListFromAllBytes(ArrayList<Byte> bytes) {
    allBytes.removeAll(bytes);
  }



  public Block getFirstFreeBlockWithEnoughMemory(int memory) {
    for (Block b : getAllBlocksWithBytes()) {
      if (!b.isAllocated()) {
        if (b.getAllocatedBytes().size() >= memory) {
          return b;
        }
      }
    }
    return null;
  }


  public Block getFirstBestOrWorstFreeBlockWithEnoughMemory(int memory, char fitType) {
    ArrayList<Block> freeBlocks = new ArrayList<>();
    for (Block block : allBlocks) {
      if (!block.isAllocated() && block.getAllocatedBytes().size() >= memory) {
        freeBlocks.add(block);
      }
    }
    switch (fitType) {
      case 'F' -> freeBlocks.sort(Block.byteAddressSort);
      case 'B' -> freeBlocks.sort(Block.freeBlockSizeComparatorAscending);
      case 'W' -> freeBlocks.sort(Block.freeBlockSizeComparatorDescending);
    }
    if (freeBlocks.isEmpty()) {
      return null;
    } else {
      return freeBlocks.get(0);
    }
  }

  public ArrayList<Integer> getCurrentBlockIds() {
    ArrayList<Integer> blockIds = new ArrayList<>();
    for (Block block : allBlocks) {
      blockIds.add(block.getBlockId());
    }
    return blockIds;
  }

  public ArrayList<Integer> getFreeByteAddresses() {
    ArrayList<Integer> freeAddresses = new ArrayList<>();
    for (Byte b : allBytes) {
      freeAddresses.add(b.getAddress());
    }
    return freeAddresses;
  }


}
