package strategy;

import java.io.IOException;
import java.util.ArrayList;
import model.Error;
import model.FitType;
import model.Memory;
import model.RegistryReader;

/**
 * First fit allocation technique.
 */
public class FirstFit extends SuperFit implements FitStrategy{

  private final RegistryReader registryReader;

  private ArrayList<Error> tempErrorList;

  private final char fitType = FitType.FIRST.getValue();


  public FirstFit(Memory memory, RegistryReader registryReader) throws IOException {
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
