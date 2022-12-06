package strategy;

import java.io.IOException;
import model.Interpreter;
import model.RegistryReader;

public class WorstFit implements FitStrategy{

  public WorstFit(Interpreter interpreter, RegistryReader registryReader) throws IOException {
    run(interpreter);
  }

  @Override
  public void run(Interpreter interpreter) {

  }
}
