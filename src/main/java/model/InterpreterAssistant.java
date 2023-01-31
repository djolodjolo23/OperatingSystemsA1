package model;

import java.util.ArrayList;

/**
 * The assistant class holding the method logic.
 */
public class InterpreterAssistant {

  /**
   * Gets the blocks that has bytes.
   *
   * @return is the new array lists with blocks with bytes.
   */
  public ArrayList<Block> getAllBlocksWithBytes(Interpreter interpreter) {
    ArrayList<Block> blocksWithBytes = new ArrayList<>();
    for (Block b : interpreter.getAllBlocks()) {
      if (!b.getAllocatedBytes().isEmpty()) {
        blocksWithBytes.add(b);
      }
    }
    return blocksWithBytes;
  }

  /**
   * Gets the blocks that are free and has bytes.
   *
   * @return is the new array lists with free blocks with bytes.
   */
  public ArrayList<Block> getAllFreeBlocksWithBytes(Interpreter interpreter) {
    ArrayList<Block> freeBlocks = new ArrayList<>();
    for (Block b : interpreter.getAllBlocks()) {
      if (!b.isAllocated() && !b.getAllocatedBytes().isEmpty()) {
        freeBlocks.add(b);
      }
    }
    return freeBlocks;
  }

  /**
   * Connect free blocks with connected bytes into one block.
   */
  public void connectFreeBlocks(Interpreter interpreter) {
    int counter = 0;
    ArrayList<Block> freeBlocks = getAllFreeBlocksWithBytes(interpreter);
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
        newBlock.setBeginningAddress(newBlock.getAllocatedBytes().get(0));
        newBlock.setEndingAddress(newBlock.getAllocatedBytes().get(newBlock.getAllocatedBytes().size() - 1));
        interpreter.removeFromAllBlocks(theBlock);
        interpreter.removeFromAllBlocks(nextBlock);
        interpreter.addToAllBlocks(newBlock);
      }
    }
  }

  /**
   * Gets all errors Ids.
   *
   * @return is the new array list with error Ids.
   */
  public ArrayList<Integer> getAllErrorsIds(Interpreter interpreter) {
    ArrayList<Integer> errorIds = new ArrayList<>();
    for (Error error : interpreter.getAllErrors()) {
      errorIds.add(error.getBlockId());
    }
    return errorIds;
  }


  /**
   * Gets the size of the biggest free block in double value.
   *
   * @return is the biggest free block in double.
   */
  public double getBiggestFreeBlockSize(Interpreter interpreter) {
    ArrayList<ArrayList<Integer>> listOfLists = new ArrayList<>();
    for (Block b : interpreter.getAllBlocks()) {
      if (!b.isAllocated() && !b.getAllocatedBytes().isEmpty()) {
        listOfLists.add(b.getAllocatedBytes());
      }
    }
    double max = listOfLists.get(0).size();
    for (ArrayList<Integer> list : listOfLists) {
      if (list.size() >= max) {
        max = list.size();
      }
    }
    return max;
  }

  /**
   * Gets the biggest free block.
   *
   * @return is the biggest block object.
   */
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

  /**
   * Gets the total free memory in bytes.
   *
   * @return is the amount of free bytes as integer.
   */
  public int getTotalFreeMemory(Interpreter interpreter) {
    ArrayList<Integer> bytes = new ArrayList<>();
    for (Block block : interpreter.getAllBlocks()) {
      if (!block.isAllocated()) {
        bytes.addAll(block.getAllocatedBytes());
      }
    }
    return bytes.size();
  }

  /**
   * Get the block object by providing the block ID.
   *
   * @return block if exists, null if not.
   */
  public Block getSpecificBlock(int blockId, Interpreter interpreter) {
    for (Block b : interpreter.getAllBlocks()) {
      if (b.getBlockId() == blockId) {
        return b;
      }
    }
    return null;
  }

  /**
   * Get the free block depending on the Fit algorithm.
   *
   * @param fitType is the fit algorithm, can be either 'F', 'B' or 'W'
   *
   * @return is the free block.
   */
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
      if (freeBlocks.size() > 1) {
        for (int i = 0; i < freeBlocks.size(); i++) {
          if (i == freeBlocks.size() - 1) {
            break;
          }
          Block one = freeBlocks.get(i);
          Block two = freeBlocks.get(i+1);
          if (one.getAllocatedBytes().size() == two.getAllocatedBytes().size()) {
            freeBlocks.set(i+1, one);
            freeBlocks.set(i, two);
          }
        }
      }
    }
    if (freeBlocks.isEmpty()) {
      return null;
    } else {
      return freeBlocks.get(0);
    }
  }

}
