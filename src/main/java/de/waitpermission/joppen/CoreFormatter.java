package de.waitpermission.joppen;

import java.io.Writer;

/**
 * The core formatting algorithm that turns a sequence of tokens into string output. This implements
 * an algorithm along the lines of that in section 4 of Oppen's paper.
 */
public final class CoreFormatter {

  /** This is where the output gets written to. */
  private final Writer sink;

  /**
   * Instantiate a core formatter with a given output sink.
   *
   * @param sink where the output should be written.
   */
  public CoreFormatter(Writer sink) {
    this.sink = sink;
  }

  /** Accept an EOF token. */
  public void eof() {}

  /** Accept an "open box" token. */
  public void openBox() {}

  /** Accept a "close box" token. */
  public void closeBox() {}

  /** Accept a "blank" token. */
  public void blank() {}

  /**
   * Accept a "string" token containing a given string.
   *
   * @param string the contents of the token.
   */
  public void string(CharSequence string) {}

  /**
   * Accept a "string" token containing a given single character.
   *
   * @param c the contents of the token.
   */
  public void string(char c) {
    string(String.valueOf(c));
  }

  /**
   * Accept a "string" token containing a given character array.
   *
   * @param chars the contents of the token.
   */
  public void string(char[] chars) {
    string(String.valueOf(chars));
  }
}
