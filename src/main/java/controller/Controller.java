package controller;

import java.io.IOException;
import model.Memory;
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
  public void run(AbstractStrategyFactory strategyFactory, Memory memory) throws IOException {
    registryReader.loadFile();
    strategyFactory.getFirstFitRule(memory, registryReader);
    strategyFactory.getBestFitRule(memory,registryReader);
    strategyFactory.getWorstFitRule(memory, registryReader);
  }
}
