package strategy;

import model.Interpreter;
import model.RegistryReader;

public class WorstFit implements FitStrategy{

  public WorstFit(Interpreter interpreter, RegistryReader registryReader) {
    run(interpreter, registryReader);
  }

  @Override
  public void run(Interpreter interpreter, RegistryReader registryReader) {

  }
}
