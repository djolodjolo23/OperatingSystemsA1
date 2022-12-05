package strategy;

import model.Interperter;
import model.RegistryReader;

public interface FitStrategy {

  void run(Interperter interperter, RegistryReader registryReader);

}
