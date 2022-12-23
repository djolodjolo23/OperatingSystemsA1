package strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import model.Block;
import model.Command;
import model.Command.CommandIdentifiers;
import model.Counter;
import model.Error;
import model.IntegerChecker;
import model.Memory;
import model.RegistryReader;

/**
 * The super class containing the method for running defragmentation.
 * All types of fragmentation extend this class since the difference between the methods
 * is only in the 'allocate' method, and that is handled with fitType parameter.
 */
public abstract class SuperFit implements IntegerChecker {


  public void run(Memory memory, RegistryReader registryReader, ArrayList<Error> tempErrorList, char fitType)
      throws IOException {
    for (Command c : registryReader.getAllCommands()) {
      if (integerCheck(c.getCommandIdentifier())) {
        begin(memory, c);
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.ALLOCATE.getValue())) {
        if (c.getAmountOfMemory() <= memory.getBiggestFreeBlock().getAllocatedBytes().size()) {
          allocate(memory, c, fitType);
        } else {
          Error newError = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), (int) memory.getBiggestFreeBlockSize() + 1, c.getBlockId());
          memory.addToAllErrors(newError);
          continue;
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.DEALLOCATE.getValue())) {
        if (memory.getSpecificBlock(c.getBlockId()) != null) {
          deallocate(memory, c);
        } else {
          Error newError;
          if (memory.getAllErrorsIds().contains(c.getBlockId())) {
            newError = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), 1);
          } else {
            newError = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), 0);
          }
          tempErrorList.add(newError);
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.OUTPUT.getValue())) {
        createIntermediateOutput(memory, registryReader, fitType);
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.COMPACT.getValue())) {
        compact(memory);
      }
      memory.addListToAllErrors(tempErrorList);
      tempErrorList.clear();
    }
    registryReader.saveFinalFile(memory, fitType);
    memory.clearAllLists();
  }

  private void begin(Memory memory, Command c) {
    var freeBlock = new Block();
    freeBlock.setBlockId(Integer.parseInt(c.getCommandIdentifier()));
    for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
      freeBlock.addToAllocatedBytes(i);
    }
    freeBlock.setSize(freeBlock.getAllocatedBytes().size());
    memory.addToAllBlocks(freeBlock);
  }

  private void allocate(Memory memory, Command c, char fitType) {
    var block = new Block(c.getBlockId());
    memory.addToAllBlocks(block);
    Block chosenBlock = memory.getFirstBestOrWorstFreeBlockWithEnoughMemory(c.getAmountOfMemory(), fitType);
    for (int i = 0; i < c.getAmountOfMemory(); i++) {
      block.addToAllocatedBytes(chosenBlock.getAllocatedBytes().get(i));
    }
    block.setSize(block.getAllocatedBytes().size());
    for (Integer b : block.getAllocatedBytes()) {
      for (Block free : memory.getAllBlocks()) {
        if (!free.isAllocated()) {
          if (free.getAllocatedBytes().contains(b)) {
            free.removeFromAllocatedBytes(b);
          }
        }
      }
    }
  }

  private void deallocate(Memory memory, Command c) {
    Block b = memory.getSpecificBlock(c.getBlockId());
    b.setAllocated(false);
    memory.connectFreeBlocks();
  }

  private void createIntermediateOutput(Memory memory, RegistryReader registryReader, char fitType)
      throws IOException {
    Counter.setCounter(Counter.getCounter() + 1);
    registryReader.createAndSaveIntermediateFile(Counter.getCounter(), memory, fitType);
  }

  private void compact(Memory memory) {
    ArrayList<Block> allBlocks = memory.getAllBlocks();
    for (Block b : allBlocks) {
      memory.addListToAllBytes(b.getAllocatedBytes());
      b.clearAllocatedBytesList();
    }
    ArrayList<Integer> allBytes = memory.getAllBytes();
    Collections.sort(allBytes);
    for (Block allocatedBlock : allBlocks) {
      if (allocatedBlock.isAllocated()) {
        for (int i = 0; i < allocatedBlock.getSize(); i++) {
          allocatedBlock.addToAllocatedBytes(allBytes.get(i));
        }
        memory.removeListFromAllBytes(allocatedBlock.getAllocatedBytes());
      }
    }
    memory.removeAllFreeBlocks();
    var freeBlock = new Block();
    memory.addToAllBlocks(freeBlock);
    freeBlock.addListToAllocatedBytes(memory.getAllBytes());
    freeBlock.setSize(freeBlock.getAllocatedBytes().size());
    memory.removeListFromAllBytes(freeBlock.getAllocatedBytes());
  }

}
