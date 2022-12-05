package strategy;

import model.Interpreter;
import model.RegistryReader;

public class BestFit implements FitStrategy{

  public BestFit(Interpreter interpreter, RegistryReader registryReader) {
    run(interpreter, registryReader);
  }


  @Override
  public void run(Interpreter interpreter, RegistryReader registryReader) {

  }
}