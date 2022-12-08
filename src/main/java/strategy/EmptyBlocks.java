package strategy;

import java.util.ArrayList;
import java.util.Collections;
import model.Block;
import model.Byte;
import model.Interpreter;

public abstract class EmptyBlocks {

  void addToEmptyBlocks(Interpreter interpreter) {
    ArrayList<Integer> listOfFreeByteAddresses = new ArrayList<>(interpreter.getFreeByteAddresses());
    Collections.sort(listOfFreeByteAddresses);
    innerMethod(listOfFreeByteAddresses, interpreter);
  }
  private void innerMethod(ArrayList<Integer> list, Interpreter interpreter) {
    if (!list.isEmpty()) {
    int counter = list.get(0);
    int counter2 = list.get(0);
    Block b = new Block();
    b.setAllocated(false);
    interpreter.addToAllBlocks(b);
    while (list.contains(counter2)) {
      counter2++;
    }
    int finalValue = counter2;
    for (int i = list.get(0); i < finalValue; i++) {
      while (counter == i) {
        b.addToAllocatedBytes(getSpecificByte(i, interpreter));
        counter++;
      }
    }
    ArrayList<Integer> updatedList = new ArrayList<>();
    for (Byte byteInBlock : b.getAllocatedBytes()) {
      updatedList.add(byteInBlock.getAddress());
    }
    list.removeAll(updatedList);
    updatedList.clear();
    while (!list.isEmpty()) {
      innerMethod(list, interpreter);
    }
    }
  }

  public Byte getSpecificByte(int address, Interpreter interpreter) {
    for (Byte b : interpreter.getAllBytes()) {
      if (b.getAddress() == address) {
        return b;
      }
    }
    return null;
    }

}
