package strategy;

import model.Interperter;
import model.RegistryReader;

public class WorstFit implements FitStrategy{

  public WorstFit(Interperter interperter, RegistryReader registryReader) {
    run(interperter, registryReader);
  }

  @Override
  public void run(Interperter interperter, RegistryReader registryReader) {

  }
}
