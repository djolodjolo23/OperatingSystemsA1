package strategy;

import java.io.IOException;
import model.Interpreter;
import model.RegistryReader;

public interface FitStrategy {

  void run(Interpreter interpreter) throws IOException;

}
