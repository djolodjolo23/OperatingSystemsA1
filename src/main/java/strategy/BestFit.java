package strategy;

import java.io.IOException;
import java.util.ArrayList;
import model.Error;
import model.FitType;
import model.Memory;
import model.RegistryReader;

/**
 * Best fit allocation technique.
 */
public class BestFit extends SuperFit implements FitStrategy{

  private final RegistryReader registryReader;

  private ArrayList<Error> tempErrorList;

  private final char fitType = FitType.BEST.getValue();



  public BestFit(Memory memory, RegistryReader registryReader) throws IOException {
    this.registryReader = registryReader;
    tempErrorList = new ArrayList<>();
    run(memory);
  }

  public char getFitType() {
    return fitType;
  }

  @Override
  public void run(Memory memory) throws IOException {
    super.run(memory, registryReader, tempErrorList, fitType);
  }
}
