package strategy;

import java.io.IOException;
import model.Block;
import model.Byte;
import model.Command;
import model.Command.CommandIdentifiers;
import model.Interpreter;
import model.RegistryReader;

public class FirstFit extends EmptyBlocks implements FitStrategy{

  private RegistryReader registryReader;

  public FirstFit(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    this.registryReader = registryReader;
    run(interpreter);
    super.addToEmptyBlocks(interpreter);
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
        //registryReader.saveFile(interpreter);
        //TODO: Crashing
      }
    }
  }
}
