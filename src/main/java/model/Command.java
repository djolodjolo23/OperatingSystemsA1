package model;


public class Command {

  private String commandIdentifier;
  private int blockId;
  private int amountOfMemory;

  public Command(String command, String blockId, String amountOfMemory) {
    this.commandIdentifier = command;
    this.blockId = Integer.parseInt(blockId);
    this.amountOfMemory = Integer.parseInt(amountOfMemory);

  }

  public Command(String command, String blockId) {
    this.commandIdentifier = command;
    this.blockId = Integer.parseInt(blockId);
  }

  public Command(String command) {
    this.commandIdentifier = command;
  }

  public String getCommandIdentifier() {
    return commandIdentifier;
  }

  public int getBlockId() {
    return blockId;
  }

  public int getAmountOfMemory() {
    return amountOfMemory;
  }
}
