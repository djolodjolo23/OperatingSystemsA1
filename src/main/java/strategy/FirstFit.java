package strategy;

import model.Block;
import model.Byte;
import model.Command;
import model.Interpreter;
import model.RegistryReader;

public class FirstFit extends EmptyBlocks implements FitStrategy{

  public FirstFit(Interpreter interpreter, RegistryReader registryReader) {
    run(interpreter, registryReader);
    super.addToEmptyBlocks(interpreter);
  }

  @Override
  public void run(Interpreter interpreter, RegistryReader registryReader) {
    for (Command c : registryReader.getAllCommands()) {
      if (registryReader.checkIfInteger(c.getCommandIdentifier())) {
        for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
          var b = new Byte(i);
          interpreter.addToAllBytes(b);
        }
      }
      if (c.getCommandIdentifier().equals("A")) {
        var block = new Block(c.getBlockId());
        interpreter.addToAllBlocks(block);
        for (int i = 0; i < c.getAmountOfMemory(); i++) {
          block.addToAllocatedBytes(interpreter.getAllBytes().get(i));
          interpreter.getAllBytes().get(i).setAllocated(true);
        }
        for (Byte b : block.getAllocatedBytes()) {
          if (b.isAllocated()) {
            interpreter.removeFromAllBytes(b);
          }
        }
      }
      if (c.getCommandIdentifier().equals("D")) {
        for (Block b : interpreter.getAllBlocks()) {
          if (b.getBlockId() == c.getBlockId()) {
            for (Byte bt : b.getAllocatedBytes()) {
              bt.setAllocated(false);
            }
            interpreter.addListToALlBytes(b.getAllocatedBytes());
            b.getAllocatedBytes().clear();
            b.setAllocated(false);
          }
        }
      }
    }

  }
}
