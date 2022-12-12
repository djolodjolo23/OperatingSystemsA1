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
        interpreter.removeFromAllBlocks(theBlock);
        interpreter.removeFromAllBlocks(nextBlock);
        interpreter.addToAllBlocks(newBlock);
        counter++;
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

  public Byte getSpecificByte(int address, Interpreter interpreter) {
    for (Byte b : interpreter.getAllBytes()) {
      if (b.getAddress() == address) {
        return b;
      }
    }
    return null;
  }


  public double getBiggestFreeBlockSize(Interpreter interpreter) {
    ArrayList<ArrayList<Byte>> listOfLists = new ArrayList<>();
    for (Block b : interpreter.getAllBlocks()) {
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

  public Block getBiggestFreeBlock(Interpreter interpreter) {
    ArrayList<Block> blocks = new ArrayList<>();
    for (Block b : interpreter.getAllBlocks()) {
      if (!b.isAllocated()) {
        blocks.add(b);
      }
    }
    blocks.sort(Block.freeBlockSizeComparatorDescending);
    return blocks.get(0);
  }

  public int getTotalFreeMemory(Interpreter interpreter) {
    ArrayList<Byte> bytes = new ArrayList<>();
    for (Block block : interpreter.getAllBlocks()) {
      for (Byte b : block.getAllocatedBytes()) {
        if (!b.isAllocated()) {
          bytes.add(b);
        }
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

  public ArrayList<Integer> getCurrentBlockIds(Interpreter interpreter) {
    ArrayList<Integer> blockIds = new ArrayList<>();
    for (Block block : interpreter.getAllBlocks()) {
      blockIds.add(block.getBlockId());
    }
    return blockIds;
  }

  public ArrayList<Integer> getFreeByteAddresses(Interpreter interpreter) {
    ArrayList<Integer> freeAddresses = new ArrayList<>();
    for (Byte b : interpreter.getAllBytes()) {
      freeAddresses.add(b.getAddress());
    }
    return freeAddresses;
  }

}
