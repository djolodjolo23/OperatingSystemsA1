package strategy;

import model.Interperter;
import model.RegistryReader;

public class BestFit implements FitStrategy{

  public BestFit(Interperter interperter, RegistryReader registryReader) {
    run(interperter, registryReader);
  }


  @Override
  public void run(Interperter interperter, RegistryReader registryReader) {

  }
}
