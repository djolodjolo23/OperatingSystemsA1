package strategy;

import model.Interperter;
import model.RegistryReader;
import strategy.FitStrategy;

public interface AbstractStrategyFactory {

  FitStrategy getBestFitRule(Interperter interperter, RegistryReader registryReader);

  FitStrategy getFirstFitRule(Interperter interperter, RegistryReader registryReader);

  FitStrategy getWorstFitRule(Interperter interperter, RegistryReader registryReader);
}
