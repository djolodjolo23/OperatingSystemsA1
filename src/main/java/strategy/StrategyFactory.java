package strategy;

import java.io.IOException;
import model.Interpreter;
import model.RegistryReader;

public class StrategyFactory implements AbstractStrategyFactory {

  @Override
  public void getBestFitRule(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    new BestFit(interpreter, registryReader);
  }

  @Override
  public void getFirstFitRule(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    new FirstFit(interpreter, registryReader);
  }

  @Override
  public void getWorstFitRule(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    new WorstFit(interpreter, registryReader);
  }
}
