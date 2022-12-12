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

public class RegistryReader implements FragmentationCalculator {

  private ArrayList<Command> allCommands;

  public RegistryReader() {
    allCommands = new ArrayList<>();
  }

  private String getInputPath() {
    Path path = Paths.get("Scenario1.txt");
    return path.toAbsolutePath().toString();
  }

  private Path getInputPathShort() {
    return Paths.get("Scenario1.txt");
  }

  private Path getOutputPath() {
    return Paths.get("Scenario1.out.txt");
  }

  public void createAndSaveIntermediateFile(int counter, Interpreter interpreter, char fitType) throws IOException {
    StringBuilder sb = new StringBuilder(getInputPathShort().toString());
    sb.delete(sb.length()-3, sb.length());
    File intermediateFile = new File(sb + "out" + counter + ".txt");
    //intermediateFile.createNewFile();
    new FileOutputStream(intermediateFile.getName()).close();
    PrintWriter printWriter = new PrintWriter(new FileWriter(intermediateFile.getName(),false));
    printAndFormat(printWriter, interpreter, fitType);
  }


  public ArrayList<Command> getAllCommands() {
    return allCommands;
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
          line[0].equals(CommandIdentifiers.OUTPUT.getValue()) ||
          checkIfInteger(line[0])) {
        var command = new Command(line[0]);
        allCommands.add(command);
      }
    }
  }


  public void saveFinalFile(Interpreter interpreter, char fitType) throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileWriter(getOutputPath().toString(), true))) {
      printAndFormat(pw, interpreter, fitType);
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
    ArrayList<Block> blocks = interpreter.getAllBlocksWithBytes();
    Collections.sort(blocks);
    printWriter.printf("Allocated blocks:");
    for (Block b : blocks) {
      if (b.isAllocated()) {
        printWriter.printf("%n%s;%s;%s",
            b.getBlockId(),
            b.getAllocatedBytes().get(0),
            b.getAllocatedBytes().get(b.getAllocatedBytes().size()-1));
      }
    }
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
    printWriter.printf(String.valueOf(calculate(interpreter.getBiggestFreeBlockSize(), interpreter.getTotalFreeMemory())));
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


  public boolean checkIfInteger(String num) {
    try {
      Integer.parseInt(num);
      return true;
    } catch (NumberFormatException ex) {
      return false;
    }
  }

}
