package model;

import java.io.FileNotFoundException;
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

public class RegistryReader extends FragmentationCalculator {

  private ArrayList<Command> allCommands;

  public RegistryReader() {
    allCommands = new ArrayList<>();
  }

  private String getInputPath() {
    Path path = Paths.get("Scenario1.txt");
    return path.toAbsolutePath().toString();
  }

  private String getOutputPath() {
    Path path = Paths.get("Scenario1_out.txt");
    return path.toAbsolutePath().toString();
  }

  private String getOutputPath1() {
    Path path = Paths.get("Scenario1_out1.txt");
    return path.toAbsolutePath().toString();
  }

  private String getOutputPath2() {
    Path path = Paths.get("Scenario1_out2.txt");
    return path.toAbsolutePath().toString();
  }

  private String getOutputPath3() {
    Path path = Paths.get("Scenario1_out3.txt");
    return path.toAbsolutePath().toString();
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

  public void saveFile1(Interpreter interpreter) throws IOException {
    new FileOutputStream(getOutputPath1()).close();
    PrintWriter printWriter = new PrintWriter(new FileWriter(getOutputPath1(), Charset.defaultCharset()));
    printAndFormat(printWriter, interpreter);
  }

  public void saveFile2(Interpreter interpreter) throws IOException {
    new FileOutputStream(getOutputPath2()).close();
    PrintWriter printWriter = new PrintWriter(new FileWriter(getOutputPath2(), Charset.defaultCharset()));
    printAndFormat(printWriter, interpreter);
  }

  public void saveFile3(Interpreter interpreter) throws IOException {
    new FileOutputStream(getOutputPath3()).close();
    PrintWriter printWriter = new PrintWriter(new FileWriter(getOutputPath3(), Charset.defaultCharset()));
    printAndFormat(printWriter, interpreter);
  }

  public void saveFinalFile(Interpreter interpreter) throws IOException {
    new FileOutputStream(getOutputPath()).close();
    PrintWriter printWriter = new PrintWriter(new FileWriter(getOutputPath(), Charset.defaultCharset()));
    printAndFormat(printWriter, interpreter);
  }

  private void printAndFormat(PrintWriter printWriter, Interpreter interpreter) {
    ArrayList<Block> blocks = interpreter.getAllBlocksWithBytes();
    Collections.sort(blocks);
    printWriter.printf("Allocated blocks:");
    for (Block b : blocks) {
      if (b.isAllocated()) {
        printWriter.printf("%n%s;%s;%s",
            b.getBlockId(),
            b.getAllocatedBytes().get(0).getAddress(),
            b.getAllocatedBytes().get(b.getAllocatedBytes().size()-1).getAddress());
      }
    }
    printWriter.printf("%nFree blocks:");
    for (Block fb : blocks) {
      if (!fb.isAllocated()) {
        if (!fb.getAllocatedBytes().isEmpty()) {
          printWriter.printf("%n%s;%s",
              fb.getAllocatedBytes().get(0).getAddress(),
              fb.getAllocatedBytes().get(fb.getAllocatedBytes().size()-1).getAddress());
        }
      }
    }
    printWriter.printf("%nFragmentation:%n");
    printWriter.printf(String.valueOf(super.calculate(interpreter.getBiggestFreeBlock(), interpreter.getTotalFreeMemory())));
    if (interpreter.getAllErrors().isEmpty()) {
      printWriter.printf("%nErrors%nNone");
    } else {
      printWriter.printf("%nErrors");
      for (Error e : interpreter.getAllErrors()) {
        printWriter.printf("%n%s;%s;%s",
            e.getCommandIdentifier(),
            e.getInstructionNumber(),
            e.getThirdParameter());
      }
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
