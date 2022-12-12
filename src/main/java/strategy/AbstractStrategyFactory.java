package strategy;

import java.io.IOException;
import model.Interpreter;
import model.RegistryReader;

public interface AbstractStrategyFactory {

  void getBestFitRule(Interpreter interpreter, RegistryReader registryReader) throws IOException;

  void getFirstFitRule(Interpreter interpreter, RegistryReader registryReader) throws IOException;

  void getWorstFitRule(Interpreter interpreter,RegistryReader registryReader) throws IOException;
}
