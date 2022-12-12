package strategy;

import java.io.IOException;
import java.util.ArrayList;
import model.Error;
import model.FitType;
import model.Interpreter;
import model.RegistryReader;

public class WorstFit extends SuperFit implements FitStrategy{

  private final RegistryReader registryReader;

  private ArrayList<Error> tempErrorList;

  private final char fitType = FitType.WORST.getValue();


  public WorstFit(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    this.registryReader = registryReader;
    tempErrorList = new ArrayList<>();
    run(interpreter);
  }

  public char getFitType() {
    return fitType;
  }

  @Override
  public void run(Interpreter interpreter) throws IOException {
    super.run(interpreter, registryReader, tempErrorList, fitType);
  }
}
