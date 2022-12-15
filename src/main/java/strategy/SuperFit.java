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
import model.Interpreter;
import model.RegistryReader;

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
          Error newError = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), (int)interpreter.getBiggestFreeBlockSize() + 1, c.getBlockId());
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
        createIntermediateOutput(interpreter, registryReader, fitType);
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.COMPACT.getValue())) {
        compact(interpreter);
      }
      interpreter.addListToAllErrors(tempErrorList);
      tempErrorList.clear();
    }
    addToEmptyBlocks(interpreter);
    registryReader.saveFinalFile(interpreter, fitType);
    interpreter.clearAllLists();
  }

  void begin(Interpreter interpreter, Command c) {
    Block freeBlock = new Block();
    freeBlock.setBlockId(Integer.parseInt(c.getCommandIdentifier()));
    for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
      interpreter.addToAllBytes(i);
      freeBlock.addToAllocatedBytes(i);
    }
    freeBlock.setSize(freeBlock.getAllocatedBytes().size());
    interpreter.addToAllBlocks(freeBlock);
    interpreter.removeListFromAllBytes(freeBlock.getAllocatedBytes());
  }

  private void allocate(Interpreter interpreter, Command c, char fitType) {
    var block = new Block(c.getBlockId());
    interpreter.addToAllBlocks(block);
    Block chosenBlock = interpreter.getFirstBestOrWorstFreeBlockWithEnoughMemory(c.getAmountOfMemory(), fitType);
    for (int i = 0; i < c.getAmountOfMemory(); i++) {
      block.addToAllocatedBytes(chosenBlock.getAllocatedBytes().get(i));
    }
    block.setSize(block.getAllocatedBytes().size());
    for (Integer b : block.getAllocatedBytes()) {
      interpreter.removeFromAllBytes(b);
      for (Block free : interpreter.getAllBlocks()) {
        if (!free.isAllocated()) {
          if (free.getAllocatedBytes().contains(b)) {
            free.removeFromAllocatedBytes(b);
          }
        }
      }
    }
  }

  private void deallocate(Interpreter interpreter, Command c) {
    Block b = interpreter.getSpecificBlock(c.getBlockId());
    b.setAllocated(false);
    interpreter.connectFreeBlocks();
  }

  private void createIntermediateOutput(Interpreter interpreter, RegistryReader registryReader, char fitType)
      throws IOException {
    Counter.setCounter(Counter.getCounter() + 1);
    addToEmptyBlocks(interpreter);
    registryReader.createAndSaveIntermediateFile(Counter.getCounter(), interpreter, fitType);
  }

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


  private void addToEmptyBlocks(Interpreter interpreter) {
    ArrayList<Integer> listOfFreeByteAddresses = new ArrayList<>(interpreter.getFreeByteAddresses());
    Collections.sort(listOfFreeByteAddresses);
    emptyBlocksInnerMethod(listOfFreeByteAddresses, interpreter);
  }
  private void emptyBlocksInnerMethod(ArrayList<Integer> list, Interpreter interpreter) {
    if (!list.isEmpty()) {
      int counter = list.get(0);
      int counter2 = list.get(0);
      Block b = new Block();
      b.setAllocated(false);
      interpreter.addToAllBlocks(b);
      while (list.contains(counter2)) {
      counter2++;
      }
      int finalValue = counter2;
      for (int i = list.get(0); i < finalValue; i++) {
        while (counter == i) {
          b.addToAllocatedBytes(interpreter.getSpecificByte(i));
          counter++;
        }
      }
      ArrayList<Integer> updatedList = new ArrayList<>(b.getAllocatedBytes());
      list.removeAll(updatedList);
      updatedList.clear();
      b.setSize(b.getAllocatedBytes().size());
      while (!list.isEmpty()) {
        emptyBlocksInnerMethod(list, interpreter);
      }
    }
  }


}
