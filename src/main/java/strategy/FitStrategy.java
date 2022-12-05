package strategy;

import model.Interpreter;
import model.RegistryReader;

public interface FitStrategy {

  void run(Interpreter interpreter, RegistryReader registryReader);

}
