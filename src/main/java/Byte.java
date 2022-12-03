public class Byte {

  private int address = 0;
  private boolean allocated;

  public Byte(int address) {
    this.address = address;
    this.allocated = false;
  }

  public void setAllocated() {
    this.allocated = true;
  }

  public void setAddress(int address){
    this.address = address;
  }

  public int getAddress() {
    return this.address;
  }

}
