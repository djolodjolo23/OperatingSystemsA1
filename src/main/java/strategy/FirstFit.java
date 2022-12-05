package strategy;

import model.Block;
import model.Byte;
import model.Command;
import model.Interperter;
import model.RegistryReader;

public class FirstFit implements FitStrategy{

  public FirstFit(Interperter interperter, RegistryReader registryReader) {
    run(interperter, registryReader);
  }

  @Override
  public void run(Interperter interperter, RegistryReader registryReader) {
    for (Command c : registryReader.getAllCommands()) {
      if (registryReader.checkIfInteger(c.getCommandIdentifier())) {
        for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
          var b = new Byte(i);
          interperter.addToAllBytes(b);
        }
      }
      if (c.getCommandIdentifier().equals("A")) {
        var block = new Block(c.getBlockId());
        interperter.addToAllBlocks(block);
        for (int i = 0; i < c.getAmountOfMemory(); i++) {
          block.addToAllocatedBytes(interperter.getAllBytes().get(i));
          interperter.getAllBytes().get(i).setAllocated(true);
        }
        for (Byte b : block.getAllocatedBytes()) {
          if (b.isAllocated()) {
            interperter.removeFromAllBytes(b);
          }
        }
      }
      if (c.getCommandIdentifier().equals("D")) {
        for (Block b : interperter.getAllBlocks()) {
          if (b.getBlockId() == c.getBlockId()) {
            for (Byte bt : b.getAllocatedBytes()) {
              bt.setAllocated(false);
            }
            interperter.addListToALlBytes(b.getAllocatedBytes());
            b.getAllocatedBytes().clear();
            b.setAllocated(false);
          }
        }
      }
    }

  }
}
