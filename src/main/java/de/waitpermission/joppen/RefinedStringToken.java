package de.waitpermission.joppen;

import java.io.IOException;

/**
 * A refined String token, ready for printing.
 */
class RefinedStringToken extends RefinedToken {

  /**
   * The content of the token.
   */
  private final CharSequence content;

  /**
   * @param content the content of the token.
   */
  RefinedStringToken(CharSequence content) {
    super(content.length());
    this.content = content;
  }

  @Override
  public void print(PrintState printState) throws IOException {
    printState.sink.write(content.toString());
    printState.space -= length;
  }
}
