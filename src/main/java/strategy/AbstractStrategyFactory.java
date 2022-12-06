package strategy;

import java.io.IOException;
import model.Interpreter;
import model.RegistryReader;

public interface AbstractStrategyFactory {

  FitStrategy getBestFitRule(Interpreter interpreter, RegistryReader registryReader);

  FitStrategy getFirstFitRule(Interpreter interpreter, RegistryReader registryReader) throws IOException;

  FitStrategy getWorstFitRule(Interpreter interpreter,RegistryReader registryReader) throws IOException;
}
