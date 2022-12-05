package model;

import java.util.ArrayList;

public class Interperter {

  private RegistryReader registryReader;

  private ArrayList<Byte> allBytes;

  private ArrayList<Block> allBlocks;


  public Interperter(RegistryReader registryReader) {
    this.registryReader = registryReader;
    allBytes = new ArrayList<>();
    allBlocks = new ArrayList<>();
  }

  public void addToAllBytes(Byte b) {
    allBytes.add(b);
  }

  public void removeFromAllBytes(Byte b) {
    allBytes.remove(b);
  }

  public void addToAllBlocks(Block b) {
    allBlocks.add(b);
  }

  public void addListToAllBlocks(ArrayList<Block> blocks) {
    allBlocks.addAll(blocks);
  }

  public void addListToALlBytes(ArrayList<Byte> bytes) {
    allBytes.addAll(bytes);
  }

  public ArrayList<Byte> getAllBytes() {
    return allBytes;
  }

  public ArrayList<Block> getAllBlocks() {
    return allBlocks;
  }


}
