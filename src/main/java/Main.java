import java.io.IOException;
import controller.Controller;
import java.util.ArrayList;
import model.Interpreter;
import model.RegistryReader;
import strategy.AbstractStrategyFactory;
import strategy.StrategyFactory;

public class Main {

  public static void main(String[] args) throws IOException {



    var regReader = new RegistryReader();

    AbstractStrategyFactory strategyFactory = new StrategyFactory();

    Interpreter interpreter = new Interpreter(regReader);

    var controller = new Controller(regReader);

    controller.run(strategyFactory, interpreter);

    ArrayList<Integer> integers = new ArrayList<>();
    integers.add(1);
    integers.add(2);
    integers.get(0);


}
}