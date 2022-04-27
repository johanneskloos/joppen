package de.waitpermission.joppen;

import java.io.IOException;

/** A refined token, ready for printing. */
abstract class RefinedToken {
  /**
   * The virtual length of this token.
   */
  long length;

  protected RefinedToken(long initialLength) {
    this.length = initialLength;
  }

  /**
   * Print this token.
   *
   * @param printState the state of the printer backend.
   * @throws IOException I/O filed.
   */
  protected abstract void print(PrintState printState) throws IOException;
}
