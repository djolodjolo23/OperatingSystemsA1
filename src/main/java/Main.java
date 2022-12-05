import java.io.IOException;
import controller.Controller;
import model.Interpreter;
import model.RegistryReader;
import strategy.AbstractStrategyFactory;
import strategy.StrategyFactory;

public class Main {

  public static void main(String[] args) throws IOException {



    var regReader = new RegistryReader();

    AbstractStrategyFactory strategyFactory = new StrategyFactory();

    Interpreter interpreter = new Interpreter(regReader);

    var controller = new Controller(regReader, interpreter);

    controller.run(strategyFactory, interpreter);


}
}