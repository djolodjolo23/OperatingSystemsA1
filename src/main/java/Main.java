import java.io.IOException;
import controller.Controller;
import model.Memory;
import model.RegistryReader;
import strategy.AbstractStrategyFactory;
import strategy.StrategyFactory;

/**
 * The main class.
 */
public class Main {

  /**
   * Main method.
   * TODO: RUN!
   */

  public static void main(String[] args) throws IOException {

    var regReader = new RegistryReader();

    AbstractStrategyFactory strategyFactory = new StrategyFactory();

    Memory memory = new Memory();

    var controller = new Controller(regReader);

    controller.run(strategyFactory, memory);

  }
}