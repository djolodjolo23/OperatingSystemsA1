import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {

    /**
    for (int i = 0; i <= 1000; i++) {
      var b = new Byte();
      b.setAddress(i);
    }
     **/

    RegistryReader registryReader = new RegistryReader();
    registryReader.loadFile();

    for (Command c : registryReader.getAllCommands()) {
      System.out.println(c.getCommandIdentifier() + ";" +  c.getBlockId() + ";" + c.getAmountOfMemory());
    }

  }
}