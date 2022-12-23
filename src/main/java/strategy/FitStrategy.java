package strategy;

import java.io.IOException;
import model.Memory;

public interface FitStrategy {

  void run(Memory memory) throws IOException;

}
