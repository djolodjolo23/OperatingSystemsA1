package model;

public class Byte {

  private int address;
  private boolean allocated;

  public Byte(int address) {
    this.address = address;
    this.allocated = false;
  }

  public void setAllocated(boolean value) {
    this.allocated = value;
  }

  public boolean isAllocated() {
    return allocated;
  }

  public void setAddress(int address){
    this.address = address;
  }

  public int getAddress() {
    return this.address;
  }

}
