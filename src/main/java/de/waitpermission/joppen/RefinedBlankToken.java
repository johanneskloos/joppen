package de.waitpermission.joppen;

import java.io.IOException;
import java.util.Arrays;

/**
 * A refined blank token, ready for printing.
 */
class RefinedBlankToken extends RefinedToken {
  RefinedBlankToken(long initialLength) {
    super(initialLength);
  }

  @Override
  public void print(PrintState printState) throws IOException {
    if (length > printState.space) {
      printState.space = printState.indentStack.peek() - 2;
      char[] indentBuf = new char[printState.margin - printState.space];
      Arrays.fill(indentBuf, ' ');
      printState.sink.write(indentBuf);
    } else {
      printState.sink.write(' ');
      printState.space--;
    }
  }
}
