import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Command {

  private String commandIdentifier;
  private String blockId;
  private String amountOfMemory;

  public Command(String command, String blockId, String amountOfMemory) {
    this.commandIdentifier = command;
    this.blockId = blockId;
    this.amountOfMemory = amountOfMemory;

  }

  public Command(String command, String blockId) {
    this.commandIdentifier = command;
    this.blockId = blockId;
  }

  public Command(String command) {
    this.commandIdentifier = command;
  }

  public String getCommandIdentifier() {
    return commandIdentifier;
  }

  public String getBlockId() {
    return blockId;
  }

  public String getAmountOfMemory() {
    return amountOfMemory;
  }
}
