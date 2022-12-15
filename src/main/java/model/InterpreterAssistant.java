package model;

import java.util.ArrayList;

public class InterpreterAssistant {

  public ArrayList<Block> getAllBlocksWithBytes(Interpreter interpreter) {
    ArrayList<Block> blocksWithBytes = new ArrayList<>();
    for (Block b : interpreter.getAllBlocks()) {
      if (!b.getAllocatedBytes().isEmpty()) {
        blocksWithBytes.add(b);
      }
    }
    return blocksWithBytes;
  }

  public void connectFreeBlocks(Interpreter interpreter) {
    int counter = 0;
    ArrayList<Block> freeBlocks = new ArrayList<>();
    for (Block b : interpreter.getAllBlocks()) {
      if (!b.isAllocated() && !b.getAllocatedBytes().isEmpty()) {
        freeBlocks.add(b);
      }
    }
    freeBlocks.sort(Comparator.byteAddressSort);
    for (int i = 0; i < freeBlocks.size(); i++) {
      Block theBlock = freeBlocks.get(counter);
      ArrayList<Integer> allocatedBytes = theBlock.getAllocatedBytes();
      counter++;
      if (counter == freeBlocks.size()) {
        break;
      }
      Block nextBlock = freeBlocks.get(counter);
      if (allocatedBytes.get(allocatedBytes.size() -1) + 1 == nextBlock.getAllocatedBytes().get(0)) {
        Block newBlock = new Block();
        newBlock.addListToAllocatedBytes(theBlock.getAllocatedBytes());
        newBlock.addListToAllocatedBytes(nextBlock.getAllocatedBytes());
        interpreter.removeFromAllBlocks(theBlock);
        interpreter.removeFromAllBlocks(nextBlock);
        interpreter.addToAllBlocks(newBlock);
      }
    }
  }

  public ArrayList<Integer> getAllErrorsIds(Interpreter interpreter) {
    ArrayList<Integer> errorIds = new ArrayList<>();
    for (Error error : interpreter.getAllErrors()) {
      errorIds.add(error.getBlockId());
    }
    return errorIds;
  }

  public Integer getSpecificByte(int address, Interpreter interpreter) {
    for (Integer b : interpreter.getAllBytes()) {
      if (b == address) {
        return b;
      }
    }
    return null;
  }


  public double getBiggestFreeBlockSize(Interpreter interpreter) {
    ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<>();
    for (Block b : interpreter.getAllBlocks()) {
      if (!b.isAllocated()) {
        if (!b.getAllocatedBytes().isEmpty()) {
          listOfLists.add(b.getAllocatedBytes());
        }
      }
    }
    double max = listOfLists.get(0).size()-1;
    for (ArrayList<Integer> list : listOfLists) {
      if (list.size() >= max) {
        max = list.size()-1;
      }
    }
    return max;
  }

  public Block getBiggestFreeBlock(Interpreter interpreter) {
    ArrayList<Block> blocks = new ArrayList<>();
    for (Block b : interpreter.getAllBlocks()) {
      if (!b.isAllocated()) {
        blocks.add(b);
      }
    }
    blocks.sort(Comparator.freeBlockSizeComparatorDescending);
    return blocks.get(0);
  }

  public int getTotalFreeMemory(Interpreter interpreter) {
    ArrayList<Integer> bytes = new ArrayList<>();
    for (Block block : interpreter.getAllBlocks()) {
      if (!block.isAllocated()) {
        bytes.addAll(block.getAllocatedBytes());
      }
    }
    return bytes.size()-1;
  }

  public Block getSpecificBlock(int blockId, Interpreter interpreter) {
    for (Block b : interpreter.getAllBlocks()) {
      if (b.getBlockId() == blockId) {
        return b;
      }
    }
    return null;
  }

  public Block getFirstBestOrWorstFreeBlockWithEnoughMemory(int memory, char fitType, Interpreter interpreter) {
    ArrayList<Block> freeBlocks = new ArrayList<>();
    for (Block block : interpreter.getAllBlocks()) {
      if (!block.isAllocated() && block.getAllocatedBytes().size() >= memory) {
        freeBlocks.add(block);
      }
    }
    if (fitType == FitType.FIRST.getValue()) {
      freeBlocks.sort(Comparator.byteAddressSort);
    }
    if (fitType == FitType.BEST.getValue()) {
      freeBlocks.sort(Comparator.freeBlockSizeComparatorAscending);
    }
    if (fitType == FitType.WORST.getValue()) {
      freeBlocks.sort(Comparator.freeBlockSizeComparatorDescending);
    }
    if (freeBlocks.isEmpty()) {
      return null;
    } else {
      return freeBlocks.get(0);
    }
  }

  public ArrayList<Integer> getCurrentBlockIds(Interpreter interpreter) {
    ArrayList<Integer> blockIds = new ArrayList<>();
    for (Block block : interpreter.getAllBlocks()) {
      blockIds.add(block.getBlockId());
    }
    return blockIds;
  }

  public ArrayList<Integer> getFreeByteAddresses(Interpreter interpreter) {
    return new ArrayList<>(interpreter.getAllBytes());
  }

}
