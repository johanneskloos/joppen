package de.waitpermission.joppen;

import java.io.IOException;

/**
 * A refined close token, ready for printing.
 */
class RefinedCloseToken extends RefinedToken {

  public RefinedCloseToken() {
    super(0);
  }

  @Override
  public void print(PrintState printState) throws IOException {
    printState.indentStack.pop();
  }
}
