package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import model.Command.CommandIdentifiers;

/**
 * Class responsible for reading and writing from/to the text file.
 */
public class RegistryReader implements FragmentationCalculator, IntegerChecker {

  private final ArrayList<Command> allCommands;

  public RegistryReader() {
    allCommands = new ArrayList<>();
  }

  public ArrayList<Command> getAllCommands() {
    return allCommands;
  }

  private String getInputPath() {
    Path path = Paths.get("Scenario1.in");
    return path.toAbsolutePath().toString();
  }

  private Path getInputPathShort() {
    return Paths.get("Scenario1.in");
  }

  private Path getOutputPath() {
    return Paths.get("Scenario1.out");
  }


  public void createAndSaveIntermediateFile(int counter, Interpreter interpreter, char fitType) throws IOException {
    StringBuilder sb = new StringBuilder(getInputPathShort().toString());
    sb.delete(sb.length()-3, sb.length());
    File intermediateFile = new File(sb + ".out" + counter);
    //new FileOutputStream(intermediateFile.getName()).close();
    try (PrintWriter pw = new PrintWriter(new FileWriter(intermediateFile.getName(), true))) {
      printAndFormat(pw, interpreter, fitType);
    }
  }

  public void saveFinalFile(Interpreter interpreter, char fitType) throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileWriter(getOutputPath().toString(), true))) {
      printAndFormat(pw, interpreter, fitType);
    }
  }

  public void loadFile() throws IOException {
    Scanner scanner = new Scanner(new FileReader(getInputPath(), Charset.defaultCharset()));
    while (scanner.hasNextLine()) {
      String[] line = scanner.nextLine().split(";");
      if (line[0].equals(CommandIdentifiers.ALLOCATE.getValue())) {
        var command = new Command(line[0], line[1], line[2]);
        allCommands.add(command);
      }
      if (line[0].equals(CommandIdentifiers.DEALLOCATE.getValue())) {
        var command = new Command(line[0], line[1]);
        allCommands.add(command);
      }
      if (line[0].equals(CommandIdentifiers.COMPACT.getValue()) ||
          integerCheck(line[0])) {
        var command = new Command(line[0]);
        allCommands.add(command);
      }
      if (line[0].equals(CommandIdentifiers.OUTPUT.getValue())) {
        Counter.setCounter(Counter.getCounter() + 1);
        var command = new Command(line[0], Counter.getCounter());
        allCommands.add(command);
      }
    }
  }

  private void printAndFormat(PrintWriter printWriter, Interpreter interpreter, char fitType) {
    if (fitType == FitType.FIRST.getValue()) {
      printWriter.printf("First Fit:%n");
    }
    if (fitType == FitType.BEST.getValue()) {
      printWriter.printf("Best Fit:%n");
    }
    if (fitType == FitType.WORST.getValue()) {
      printWriter.printf("Worst Fit:%n");
    }
    String fragmentation = String.valueOf(
        calculate(interpreter.getBiggestFreeBlockSize(), interpreter.getTotalFreeMemory()));
    if (fragmentation.length() == 3) {
      fragmentation = fragmentation + "00000";
    }
    ArrayList<Block> blocks = interpreter.getAllBlocksWithBytes();
    blocks.sort(Comparator.idSort);
    printWriter.printf("Allocated blocks:");
    for (Block b : blocks) {
      if (b.isAllocated()) {
        printWriter.printf("%n%s;%s;%s",
            b.getBlockId(),
            b.getAllocatedBytes().get(0),
            b.getAllocatedBytes().get(b.getAllocatedBytes().size()-1));
      }
    }
    blocks.sort(Comparator.byteAddressSort);
    printWriter.printf("%nFree blocks:");
    for (Block fb : blocks) {
      if (!fb.isAllocated()) {
        if (!fb.getAllocatedBytes().isEmpty()) {
          printWriter.printf("%n%s;%s",
              fb.getAllocatedBytes().get(0),
              fb.getAllocatedBytes().get(fb.getAllocatedBytes().size()-1));
        }
      }
    }
    printWriter.printf("%nFragmentation:%n");
    printWriter.printf(fragmentation);
    if (interpreter.getAllErrors().isEmpty()) {
      printWriter.printf("%nErrors%nNone%n");
      printWriter.printf("%n");
    } else {
      printWriter.printf("%nErrors");
      for (Error e : interpreter.getAllErrors()) {
        printWriter.printf("%n%s;%s;%s",
            e.getCommandIdentifier(),
            e.getInstructionNumber(),
            e.getThirdParameter());
      }
      printWriter.printf("%n%n");
    }
    printWriter.close();
  }

}
