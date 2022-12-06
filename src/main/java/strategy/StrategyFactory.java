package strategy;

import java.io.IOException;
import model.Interpreter;
import model.RegistryReader;

public class StrategyFactory implements AbstractStrategyFactory {

  @Override
  public FitStrategy getBestFitRule(Interpreter interpreter, RegistryReader registryReader) {
    return new BestFit(interpreter, registryReader);
  }

  @Override
  public FitStrategy getFirstFitRule(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    return new FirstFit(interpreter, registryReader);
  }

  @Override
  public FitStrategy getWorstFitRule(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    return new WorstFit(interpreter, registryReader);
  }
}
