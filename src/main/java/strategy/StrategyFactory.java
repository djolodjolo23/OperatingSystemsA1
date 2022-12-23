package strategy;

import java.io.IOException;
import model.Memory;
import model.RegistryReader;

public class StrategyFactory implements AbstractStrategyFactory {

  @Override
  public void getBestFitRule(Memory memory, RegistryReader registryReader) throws IOException {
    new BestFit(memory, registryReader);
  }

  @Override
  public void getFirstFitRule(Memory memory, RegistryReader registryReader) throws IOException {
    new FirstFit(memory, registryReader);
  }

  @Override
  public void getWorstFitRule(Memory memory, RegistryReader registryReader) throws IOException {
    new WorstFit(memory, registryReader);
  }
}
