package strategy;

import model.Interpreter;
import model.RegistryReader;

public interface AbstractStrategyFactory {

  FitStrategy getBestFitRule(Interpreter interpreter, RegistryReader registryReader);

  FitStrategy getFirstFitRule(Interpreter interpreter, RegistryReader registryReader);

  FitStrategy getWorstFitRule(Interpreter interpreter, RegistryReader registryReader);
}
