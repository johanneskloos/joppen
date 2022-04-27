package de.waitpermission.joppen;

import java.io.IOException;

/**
 * A refined open token, ready for printing.
 */
class RefinedOpenToken extends RefinedToken {

  public RefinedOpenToken(long initialLength) {
    super(initialLength);
  }

  @Override
  public void print(PrintState printState) throws IOException {
    printState.indentStack.push(printState.space);
  }
}
