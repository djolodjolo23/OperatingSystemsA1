package strategy;

import java.io.IOException;
import java.util.ArrayList;
import model.Block;
import model.Byte;
import model.Command;
import model.Command.CommandIdentifiers;
import model.Error;
import model.Interpreter;
import model.RegistryReader;

public class FirstFit extends EmptyBlocks implements FitStrategy{

  private RegistryReader registryReader;

  private ArrayList<Error> tempErrorList;

  private boolean outputAlreadyPrinted = false;

  public FirstFit(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    tempErrorList = new ArrayList<>();
    this.registryReader = registryReader;
    run(interpreter);
  }


  @Override
  public void run(Interpreter interpreter) throws IOException {
    for (Command c : registryReader.getAllCommands()) {
      if (registryReader.checkIfInteger(c.getCommandIdentifier())) {
        Block freeBlock = new Block();
        for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
          var b = new Byte(i);
          interpreter.addToAllBytes(b);
          freeBlock.addToAllocatedBytes(b);
        }
        interpreter.addToAllBlocks(freeBlock);
        interpreter.removeListFromAllBytes(freeBlock.getAllocatedBytes());
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.ALLOCATE.getValue())) {
        //if (outputAlreadyPrinted) {
            if (c.getAmountOfMemory() <= interpreter.getBiggestFreeBlock()) {
              var block = new Block(c.getBlockId());
              interpreter.addToAllBlocks(block);
              for (int i = 0; i < c.getAmountOfMemory(); i++) {
                block.addToAllocatedBytes(interpreter.getFreeBlockWithEnoughMemory(c.getAmountOfMemory()).getAllocatedBytes().get(i)); {
                  interpreter.getFreeBlockWithEnoughMemory(c.getAmountOfMemory()).getAllocatedBytes().get(i).setAllocated(true);
                }
              }
              for (Byte b : block.getAllocatedBytes()) {
                if (b.isAllocated()) {
                  interpreter.removeFromAllBytes(b);
                }
                for (Block free : interpreter.getAllBlocks()) {
                  if (!free.isAllocated()) {
                    if (free.getAllocatedBytes().contains(b)) {
                      free.removeFromAllocatedBytes(b);
                    }
                  }
                }
              }
            } else {
              Error error = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), (int)interpreter.getBiggestFreeBlock() + 1, c.getBlockId());
              //tempErrorList.add(error);
              interpreter.addToAllErrors(error);
              continue;
            }
        //}
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.DEALLOCATE.getValue())) {
          if (interpreter.getSpecificBlock(c.getBlockId()) != null) {
            Block b = interpreter.getSpecificBlock(c.getBlockId());
            for (Byte bt : b.getAllocatedBytes()) {
              bt.setAllocated(false);
            }
            interpreter.addListToALlBytes(b.getAllocatedBytes());
            b.getAllocatedBytes().clear();
            b.setAllocated(false);
          } else {
              Error theError;
              if (interpreter.getAllErrorsIds().contains(c.getBlockId())) {
                theError = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), 1);
              } else {
                theError = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), 0);
              }
              tempErrorList.add(theError);
          }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.OUTPUT.getValue())) {
        setOutputPrintedTrue();
        super.addToEmptyBlocks(interpreter);
        registryReader.saveFile(interpreter);
      }
      interpreter.addListToAllErrors(tempErrorList);
      tempErrorList.clear();
    }
    //super.addToEmptyBlocks(interpreter);
    registryReader.saveFinalFile(interpreter);
  }

  private void setOutputPrintedTrue() {
    outputAlreadyPrinted = true;
  }

}
