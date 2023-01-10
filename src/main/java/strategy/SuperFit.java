package strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import model.Block;
import model.Command;
import model.Command.CommandIdentifiers;
import model.Error;
import model.IntegerChecker;
import model.Interpreter;
import model.RegistryReader;

/**
 * The super class containing the method for running defragmentation.
 * All types of fragmentation extend this class since the difference between the methods
 * is only in the 'allocate' method, and that is handled with fitType parameter.
 */
public abstract class SuperFit implements IntegerChecker {


  public void run(Interpreter interpreter, RegistryReader registryReader, ArrayList<Error> tempErrorList, char fitType)
      throws IOException {
    for (Command c : registryReader.getAllCommands()) {
      if (integerCheck(c.getCommandIdentifier())) {
        begin(interpreter, c);
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.ALLOCATE.getValue())) {
        if (c.getAmountOfMemory() <= interpreter.getBiggestFreeBlock().getAllocatedBytes().size()) {
          allocate(interpreter, c, fitType);
        } else {
          Error newError = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), (int) interpreter.getBiggestFreeBlockSize() + 1, c.getBlockId());
          interpreter.addToAllErrors(newError);
          continue;
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.DEALLOCATE.getValue())) {
        if (interpreter.getSpecificBlock(c.getBlockId()) != null) {
          deallocate(interpreter, c);
        } else {
          Error newError;
          if (interpreter.getAllErrorsIds().contains(c.getBlockId())) {
            newError = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), 1);
          } else {
            newError = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), 0);
          }
          tempErrorList.add(newError);
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.OUTPUT.getValue())) {
        createIntermediateOutput(interpreter, registryReader, fitType, c.getOCounter());
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.COMPACT.getValue())) {
        compact(interpreter);
      }
      interpreter.addListToAllErrors(tempErrorList);
      tempErrorList.clear();
    }
    registryReader.saveFinalFile(interpreter, fitType);
    interpreter.clearAllLists();
  }

  /**
   * Create the first chunk of memory, size depends on the input file.
   */
  private void begin(Interpreter interpreter, Command c) {
    var freeBlock = new Block();
    freeBlock.setBlockId(Integer.parseInt(c.getCommandIdentifier()));
    for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
      freeBlock.addToAllocatedBytes(i);
    }
    freeBlock.setSize(freeBlock.getAllocatedBytes().size());
    interpreter.addToAllBlocks(freeBlock);
  }

  /**
   * Method for allocating the blocks and shifting bytes.
   *
   * @param fitType is the type of 'fit' algorithm.
   */
  private void allocate(Interpreter interpreter, Command c, char fitType) {
    var block = new Block(c.getBlockId());
    interpreter.addToAllBlocks(block);
    Block chosenBlock = interpreter.getFirstBestOrWorstFreeBlockWithEnoughMemory(c.getAmountOfMemory(), fitType);
    for (int i = 0; i < c.getAmountOfMemory(); i++) {
      block.addToAllocatedBytes(chosenBlock.getAllocatedBytes().get(i));
    }
    block.setSize(block.getAllocatedBytes().size());
    for (Integer b : block.getAllocatedBytes()) {
      for (Block free : interpreter.getAllBlocks()) {
        if (!free.isAllocated()) {
          if (free.getAllocatedBytes().contains(b)) {
            free.removeFromAllocatedBytes(b);
          }
        }
      }
    }
  }

  /**
   * A method for deallocating blocks.
   *
   */
  private void deallocate(Interpreter interpreter, Command c) {
    Block b = interpreter.getSpecificBlock(c.getBlockId());
    b.setAllocated(false);
    interpreter.connectFreeBlocks();
  }

  /**
   * A method for creating the Intermediate Output.
   */
  private void createIntermediateOutput(Interpreter interpreter, RegistryReader registryReader, char fitType, int oCounter)
      throws IOException {
    registryReader.createAndSaveIntermediateFile(oCounter, interpreter, fitType);
  }

  /**
   * A method for compacting the memory.
   */
  private void compact(Interpreter interpreter) {
    ArrayList<Block> allBlocks = interpreter.getAllBlocks();
    for (Block b : allBlocks) {
      interpreter.addListToAllBytes(b.getAllocatedBytes());
      b.clearAllocatedBytesList();
    }
    ArrayList<Integer> allBytes = interpreter.getAllBytes();
    Collections.sort(allBytes);
    for (Block allocatedBlock : allBlocks) {
      if (allocatedBlock.isAllocated()) {
        for (int i = 0; i < allocatedBlock.getSize(); i++) {
          allocatedBlock.addToAllocatedBytes(allBytes.get(i));
        }
        interpreter.removeListFromAllBytes(allocatedBlock.getAllocatedBytes());
      }
    }
    interpreter.removeAllFreeBlocks();
    var freeBlock = new Block();
    interpreter.addToAllBlocks(freeBlock);
    freeBlock.addListToAllocatedBytes(interpreter.getAllBytes());
    freeBlock.setSize(freeBlock.getAllocatedBytes().size());
    interpreter.removeListFromAllBytes(freeBlock.getAllocatedBytes());
  }

}
