package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import model.Block;
import model.Byte;
import model.Command;
import model.Error;
import model.Interpreter;
import model.RegistryReader;
import strategy.AbstractStrategyFactory;

public class Controller {

  private RegistryReader registryReader;

  private ArrayList<Byte> allBytes;

  private ArrayList<Block> allBlocks;

  private Interpreter interpreter;





  public Controller(RegistryReader registryReader, Interpreter interpreter) {
    this.registryReader = registryReader;
    //this.interpreter = interpreter;
    //bestFit = strategyFactory.getBestFitRule();
    allBytes = new ArrayList<>();
    allBlocks = new ArrayList<>();
  }

  public void run(AbstractStrategyFactory strategyFactory, Interpreter interpreter) throws IOException {
    registryReader.loadFile();
    interpreter.go(strategyFactory);
    //addToEmptyBlocks();
    printTest(interpreter);
    //registryReader.saveFile(interpreter);
  }

  void printTest(Interpreter interpreter) {
    System.out.println("Allocated blocks:");
    for (Block b : interpreter.getAllBlocks()) {
      if (b.isAllocated()) {
        System.out.println(b.getBlockId() + ";" + b.getAllocatedBytes().get(0).getAddress() + ";" + b.getAllocatedBytes().get(b.getAllocatedBytes().size()-1).getAddress());
      }
    }
    System.out.println("Free blocks: ");
    for (Block bl : interpreter.getAllBlocks()) {
      if (!bl.isAllocated()) {
        if (!bl.getAllocatedBytes().isEmpty()) {
        System.out.println(bl.getAllocatedBytes().get(0).getAddress() + ";" + bl.getAllocatedBytes().get(bl.getAllocatedBytes().size()-1).getAddress());
        }
      }
    }
    System.out.println("Fragmentation:");
    System.out.println(calculateFragmentation(interpreter));
    System.out.println("Errors");
    for (Error error : interpreter.getAllErrors()) {
      System.out.println(error.getInstructionNumber() + "|" + error.getBlockId());
    }
  }

  private double calculateFragmentation(Interpreter interpreter) {
    return 1 - (interpreter.getBiggestFreeBlock() / interpreter.getTotalFreeMemory());
  }

  void addToEmptyBlocks() {
    ArrayList<Integer> listOfFreeByteAddresses = new ArrayList<>(getFreeByteAddresses());
    Collections.sort(listOfFreeByteAddresses);
    innerMethod(listOfFreeByteAddresses);
  }
  private void innerMethod(ArrayList<Integer> list) {
    int counter = list.get(0);
    int counter2 = list.get(0);
    Block b = new Block();
    b.setAllocated(false);
    allBlocks.add(b);
    while (list.contains(counter2)) {
      counter2++;
    }
    int finalValue = counter2;
    for (int i = list.get(0); i < finalValue; i++) {
      while (counter == i) {
        b.addToAllocatedBytes(getSpecificByte(i));
        counter++;
      }
    }
    ArrayList<Integer> updatedList = new ArrayList<>();
    for (Byte byteInBlock : b.getAllocatedBytes()) {
      updatedList.add(byteInBlock.getAddress());
    }
    list.removeAll(updatedList);
    updatedList.clear();
    while (!list.isEmpty()) {
      innerMethod(list);
    }
  }

  public Byte getSpecificByte(int address) {
    for (Byte b : allBytes) {
      if (b.getAddress() == address) {
        return b;
      }
    }
    return null;
  }



  public ArrayList<Integer> getFreeByteAddresses() {
    ArrayList<Integer> freeAddresses = new ArrayList<>();
    for (Byte b : allBytes) {
      freeAddresses.add(b.getAddress());
    }
    return freeAddresses;
  }

  private void interpretCommands() {
    for (Command c : registryReader.getAllCommands()) {
      if (registryReader.checkIfInteger(c.getCommandIdentifier())) {
        for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
          var b = new Byte(i);
          allBytes.add(b);
        }
      }
      if (c.getCommandIdentifier().equals("A")) {
        var block = new Block(c.getBlockId());
        allBlocks.add(block);
        for (int i = 0; i < c.getAmountOfMemory(); i++) {
          block.addToAllocatedBytes(allBytes.get(i));
          allBytes.get(i).setAllocated(true);
          }
        for (Byte b : block.getAllocatedBytes()) {
          if (b.isAllocated()) {
            allBytes.remove(b);
          }
        }
      }
      if (c.getCommandIdentifier().equals("D")) {
        for (Block b : allBlocks) {
          if (b.getBlockId() == c.getBlockId()) {
            for (Byte bt : b.getAllocatedBytes()) {
              bt.setAllocated(false);
            }
            allBytes.addAll(b.getAllocatedBytes());
            b.getAllocatedBytes().clear();
            b.setAllocated(false);
          }
        }
      }
    }
  }

}
