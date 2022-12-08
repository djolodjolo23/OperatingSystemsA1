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
    Collections.sort(interpreter.getAllBlocks());
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
      System.out.println(error.getCommandIdentifier() + ";" + error.getInstructionNumber() + ";" + error.getThirdParameter());
    }
  }

  private double calculateFragmentation(Interpreter interpreter) {
    return 1 - (interpreter.getBiggestFreeBlock() / interpreter.getTotalFreeMemory());
  }




}
