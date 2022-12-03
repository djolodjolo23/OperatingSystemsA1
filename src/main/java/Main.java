import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {

    /**
    for (int i = 0; i <= 1000; i++) {
      var b = new Byte();
      b.setAddress(i);
    }
     **/

    var regReader = new RegistryReader();

    var controller = new Controller(regReader);

    controller.run();

  }
}