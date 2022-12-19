package model;

/**
 * The error class.
 * The error objects are created when something goes wrong with reading the commands.
 */
public class Error {

  private String commandIdentifier;
  private int instructionNumber;
  private int thirdParameter;

  private int blockId;

  public Error(String commandIdentifier, int instructionNumber, int thirdParameter) {
    this.commandIdentifier = commandIdentifier;
    this.instructionNumber = instructionNumber;
    this.thirdParameter = thirdParameter;
  }

  public Error(String commandIdentifier, int instructionNumber, int thirdParameter, int blockId) {
    this.commandIdentifier = commandIdentifier;
    this.instructionNumber = instructionNumber;
    this.thirdParameter = thirdParameter;
    this.blockId = blockId;
  }



  public String getCommandIdentifier() {
    return commandIdentifier;
  }

  public int getInstructionNumber() {
    return instructionNumber;
  }

  public int getThirdParameter() {
    return thirdParameter;
  }

  public int getBlockId() {
    return blockId;
  }

}
