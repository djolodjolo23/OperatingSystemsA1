import java.io.IOException;
import controller.Controller;
import model.RegistryReader;

public class Main {

  public static void main(String[] args) throws IOException {


    var regReader = new RegistryReader();

    var controller = new Controller(regReader);

    controller.run();


}
}