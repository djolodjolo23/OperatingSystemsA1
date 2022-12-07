package strategy;

import java.io.IOException;
import model.Block;
import model.Byte;
import model.Command;
import model.Command.CommandIdentifiers;
import model.Error;
import model.Interpreter;
import model.RegistryReader;

public class FirstFit extends EmptyBlocks implements FitStrategy{

  private RegistryReader registryReader;

  private boolean outputAlreadyPrinted = false;

  public FirstFit(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    this.registryReader = registryReader;
    runTest(interpreter);
    //runFinal(interpreter);
    //super.addToEmptyBlocks(interpreter);
  }



  @Override
  public void run(Interpreter interpreter) throws IOException {
    for (Command c : registryReader.getAllCommands()) {
      if (registryReader.checkIfInteger(c.getCommandIdentifier())) {
        for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
          var b = new Byte(i);
          interpreter.addToAllBytes(b);
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.ALLOCATE.getValue())) {
        var block = new Block(c.getBlockId());
        interpreter.addToAllBlocks(block);
        for (int i = 0; i < c.getAmountOfMemory(); i++) {
          block.addToAllocatedBytes(interpreter.getAllBytes().get(i));
          interpreter.getAllBytes().get(i).setAllocated(true);
        }
        for (Byte b : block.getAllocatedBytes()) {
          if (b.isAllocated()) {
            //removes the allocated bytes from the all bytes arraylist
            interpreter.removeFromAllBytes(b);
          }
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.DEALLOCATE.getValue())) {
        for (Block block : interpreter.getAllBlocks()) {
          if (block.getBlockId() == c.getBlockId()) {
            for (Byte bt : block.getAllocatedBytes()) {
              bt.setAllocated(false);
            }
            interpreter.addListToALlBytes(block.getAllocatedBytes());
            block.getAllocatedBytes().clear();
            block.setAllocated(false);
          }
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.OUTPUT.getValue())) {
        super.addToEmptyBlocks(interpreter);
        registryReader.saveFile(interpreter);
        break;
      }
    }
  }

  public void runTest(Interpreter interpreter) throws IOException {
    for (Command c : registryReader.getAllCommands()) {
      if (registryReader.checkIfInteger(c.getCommandIdentifier())) {
        //Block emptyBlock = new Block();
        //interpreter.addToAllBlocks(emptyBlock);
        for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
          var b = new Byte(i);
          interpreter.addToAllBytes(b);
          //emptyBlock.addToAllocatedBytes(b);
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.ALLOCATE.getValue())) {
        if (outputAlreadyPrinted) {
          for (Block bl : interpreter.getAllBlocks()) {
            if (!bl.isAllocated() && !bl.getAllocatedBytes().isEmpty()) {
              // two blocks registered instead of one
              if (c.getAmountOfMemory() <= bl.getAllocatedBytes().size()) {
                var block = new Block(c.getBlockId());
                interpreter.addToAllBlocks(block);
                for (int i = 0; i < c.getAmountOfMemory(); i++) {
                  block.addToAllocatedBytes(interpreter.getAllBytes().get(i)); {
                    interpreter.getAllBytes().get(i).setAllocated(true);
                  }
                  for (Byte b : block.getAllocatedBytes()) {
                    if (b.isAllocated()) {
                      interpreter.removeFromAllBytes(b);
                    }
                  }
                }
              } else {
                Error error = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), bl.getAllocatedBytes().size(), bl.getBlockId());
                interpreter.addToAllErrors(error);
                continue;
              }
            }
          }
        } else {
          var block = new Block(c.getBlockId());
          interpreter.addToAllBlocks(block);
          for (int i = 0; i < c.getAmountOfMemory(); i++) {
            block.addToAllocatedBytes(interpreter.getAllBytes().get(i));
            interpreter.getAllBytes().get(i).setAllocated(true);
          }
          for (Byte b : block.getAllocatedBytes()) {
            if (b.isAllocated()) {
              //removes the allocated bytes from the all bytes arraylist
              interpreter.removeFromAllBytes(b);
            }
          }
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.DEALLOCATE.getValue())) {
        for (Block b : interpreter.getAllBlocks()) {
          if (b.getBlockId() == c.getBlockId()) {
            for (Byte bt : b.getAllocatedBytes()) {
              bt.setAllocated(false);
            }
            interpreter.addListToALlBytes(b.getAllocatedBytes());
            b.getAllocatedBytes().clear();
            b.setAllocated(false);
          } else {
            for (Error e : interpreter.getAllErrors()) {
              if (e.getBlockId() == c.getBlockId()) {
                Error error = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), 1);
                interpreter.addToAllErrors(error);
              }
                Error error = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), 0);
                interpreter.addToAllErrors(error);

            }
          }
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.OUTPUT.getValue())) {
        setOutputPrintedTrue();
        super.addToEmptyBlocks(interpreter);
        registryReader.saveFile(interpreter);
      }
    }
    super.addToEmptyBlocks(interpreter);
    registryReader.saveFinalFile(interpreter);
  }

  private void setOutputPrintedTrue() {
    outputAlreadyPrinted = true;
  }



  public void runFinal(Interpreter interpreter) throws IOException {
    for (Command c : registryReader.getAllCommands()) {
      if (registryReader.checkIfInteger(c.getCommandIdentifier())) {
        for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
          var b = new Byte(i);
          interpreter.addToAllBytes(b);
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.ALLOCATE.getValue())) {
        for (Block bl : interpreter.getAllBlocks()) {
          if (!bl.isAllocated() && !bl.getAllocatedBytes().isEmpty()) {
            if (c.getAmountOfMemory() <= bl.getAllocatedBytes().size()) {
              var block = new Block(c.getBlockId());
              interpreter.addToAllBlocks(block);
              for (int i = 0; i < c.getAmountOfMemory(); i++) {
                block.addToAllocatedBytes(interpreter.getAllBytes().get(i));
                interpreter.getAllBytes().get(i).setAllocated(true);
              }
              for (Byte b : block.getAllocatedBytes()) {
              if (b.isAllocated()) {
            //removes the allocated bytes from the all bytes arraylist
                interpreter.removeFromAllBytes(b);
                }
              }
            } else {
              Error error = new Error(c.getCommandIdentifier(), registryReader.getAllCommands().indexOf(c), bl.getAllocatedBytes().size());
              interpreter.addToAllErrors(error);
            }
        }
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.DEALLOCATE.getValue())) {
        for (Block block : interpreter.getAllBlocks()) {
          if (block.getBlockId() == c.getBlockId()) {
            for (Byte bt : block.getAllocatedBytes()) {
              bt.setAllocated(false);
            }
            interpreter.addListToALlBytes(block.getAllocatedBytes());
            block.getAllocatedBytes().clear();
            block.setAllocated(false);
          }
        }
      }
      if (c.getCommandIdentifier().equals(CommandIdentifiers.OUTPUT.getValue())) {
        addToEmptyBlocks(interpreter);
        return;
      }
    }
    registryReader.saveFinalFile(interpreter);
  }
}
