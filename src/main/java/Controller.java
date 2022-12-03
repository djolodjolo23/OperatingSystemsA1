import java.io.IOException;
import java.util.ArrayList;

public class Controller {

  private RegistryReader registryReader;

  private ArrayList<Byte> allBytes;

  private ArrayList<Block> allBlocks;

  public Controller(RegistryReader registryReader) {
    this.registryReader = registryReader;
    allBytes = new ArrayList<>();
    allBlocks = new ArrayList<>();
  }

  public void run() throws IOException {
    registryReader.loadFile();
    interpretCommands();
  }


  private void interpretCommands() {
    for (Command c : registryReader.getAllCommands()) {
      if (registryReader.checkIfInteger(c.getCommandIdentifier())) {
        for (int i = 0; i < Integer.parseInt(c.getCommandIdentifier()); i++) {
          var b = new Byte(i);
          allBytes.add(b);
          System.out.println(allBytes.get(i).getAddress());
        }
      }
      if (c.getCommandIdentifier().equals("A")) {
        var block = new Block(c.getBlockId());
        allBlocks.add(block);
        for (int i = 0; i < c.getAmountOfMemory(); i++) {
          block.addToAllocatedBytes(allBytes.get(i));
          allBytes.get(i).setAllocated(true);
          block.setAllocated(true);
          //allBytes.remove(i);
          //System.out.println(block.getAllocatedBytes().get(i).getAddress());
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
