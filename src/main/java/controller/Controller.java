package controller;

import java.io.IOException;
import model.Interpreter;
import model.RegistryReader;
import strategy.AbstractStrategyFactory;

/**
 * The controller class responsible for calling the registryReader to load the file.
 * Also calling the strategy factory for getting all the rules.
 */
public class Controller {

  private final RegistryReader registryReader;


  public Controller(RegistryReader registryReader) {
    this.registryReader = registryReader;
  }

  /**
   * A method for running 3 different fit types.
   */
  public void run(AbstractStrategyFactory strategyFactory, Interpreter interpreter) throws IOException {
    registryReader.loadFile();
    strategyFactory.getFirstFitRule(interpreter, registryReader);
    strategyFactory.getBestFitRule(interpreter,registryReader);
    strategyFactory.getWorstFitRule(interpreter, registryReader);
  }
}
