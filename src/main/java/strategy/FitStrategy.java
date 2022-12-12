package strategy;

import java.io.IOException;
import model.Interpreter;

public interface FitStrategy {

  void run(Interpreter interpreter) throws IOException;

}
