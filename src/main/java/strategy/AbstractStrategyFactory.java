package strategy;

import java.io.IOException;
import model.Memory;
import model.RegistryReader;

public interface AbstractStrategyFactory {

  void getBestFitRule(Memory memory, RegistryReader registryReader) throws IOException;

  void getFirstFitRule(Memory memory, RegistryReader registryReader) throws IOException;

  void getWorstFitRule(Memory memory,RegistryReader registryReader) throws IOException;
}
