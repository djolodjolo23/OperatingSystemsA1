import java.util.ArrayList;

public class Block {

  private int blockId;
  private ArrayList<Byte> allocatedBytes;

  private boolean allocated;

  public Block(int blockId) {
    allocatedBytes = new ArrayList<>();
    this.blockId = blockId;
    this.allocated = false;
  }

  public void addToAllocatedBytes(Byte b) {
    allocatedBytes.add(b);
  }

  public boolean isAllocated() {
    return allocated;
  }

  public void setAllocated(boolean value) {
    this.allocated = value;
  }

  public int getBlockId() {
    return blockId;
  }

  public ArrayList<Byte> getAllocatedBytes() {
    return allocatedBytes;
  }
}
