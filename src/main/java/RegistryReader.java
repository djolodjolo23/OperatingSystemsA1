import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class RegistryReader {

  private ArrayList<Command> allCommands;

  public RegistryReader() {
    allCommands = new ArrayList<>();
  }

  private String getPath() {
    Path path = Paths.get("Scenario1.txt");
    return path.toAbsolutePath().toString();
  }

  public ArrayList<Command> getAllCommands() {
    return allCommands;
  }

  public void loadFile() throws IOException {
    Scanner scanner = new Scanner(new FileReader(getPath(), Charset.defaultCharset()));
    while (scanner.hasNextLine()) {
      String[] line = scanner.nextLine().split(";");
      if (line[0].equals("A")) {
        var command = new Command(line[0], line[1], line[2]);
        allCommands.add(command);
      }
      if (line[0].equals("D")) {
        var command = new Command(line[0], line[1]);
        allCommands.add(command);
      }
      if (line[0].equals("C")) {
        var command = new Command(line[0]);
        allCommands.add(command);
      }
    }
  }

}
