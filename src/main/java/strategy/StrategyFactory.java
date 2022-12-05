package strategy;

import model.Interperter;
import model.RegistryReader;

public class StrategyFactory implements AbstractStrategyFactory {

  @Override
  public FitStrategy getBestFitRule(Interperter interperter, RegistryReader registryReader) {
    return new BestFit(interperter, registryReader);
  }

  @Override
  public FitStrategy getFirstFitRule(Interperter interperter, RegistryReader registryReader) {
    return new FirstFit(interperter, registryReader);
  }

  @Override
  public FitStrategy getWorstFitRule(Interperter interperter, RegistryReader registryReader) {
    return new WorstFit(interperter, registryReader);
  }
}
