package controller;

import java.io.IOException;
import model.Interpreter;
import model.RegistryReader;
import strategy.AbstractStrategyFactory;

public class Controller {

  private final RegistryReader registryReader;


  public Controller(RegistryReader registryReader) {
    this.registryReader = registryReader;
  }

  public void run(AbstractStrategyFactory strategyFactory, Interpreter interpreter) throws IOException {
    registryReader.loadFile();
    interpreter.go(strategyFactory);
  }

}
