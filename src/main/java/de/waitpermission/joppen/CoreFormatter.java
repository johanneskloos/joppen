package de.waitpermission.joppen;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

/**
 * The core formatting algorithm that turns a sequence of tokens into string output. This implements
 * an algorithm along the lines of that in section 4 of Oppen's paper.
 */
public final class CoreFormatter {

  /**
   * The state of the printing backend.
   */
  private final PrintState printState;

  private final Queue<RefinedToken> tokenQueue = new ArrayDeque<>();
  private final Deque<RefinedToken> tokensToRefine = new ArrayDeque<>();
  private long leftTotal;
  private long rightTotal;

  /**
   * Instantiate a core formatter with a given output sink.
   *
   * @param sink where the output should be written.
   * @param margin the maximal length of output lines
   */
  public CoreFormatter(Writer sink, int margin) {
    printState = new PrintState(margin, sink);
  }

  /** Accept an EOF token. */
  public void eof() throws IOException {
    while (!tokenQueue.isEmpty())
      closeBox();
  }

  /** Accept an "open box" token. */
  public void openBox() {
    if (tokensToRefine.isEmpty()) {
      leftTotal = 1;
      rightTotal = 1;
    }
    RefinedToken token = new RefinedOpenToken(-rightTotal);
    tokenQueue.add(token);
    tokensToRefine.push(token);
  }

  /** Accept a "close box" token. */
  public void closeBox() throws IOException {
    RefinedToken token = new RefinedCloseToken();
    if (tokensToRefine.isEmpty()) {
      token.print(printState);
    } else {
      tokenQueue.add(token);
      RefinedToken closedToken = tokensToRefine.pop();
      closedToken.length += rightTotal;
      if (closedToken instanceof RefinedBlankToken && !tokensToRefine.isEmpty())
        tokensToRefine.pop().length += rightTotal;
      if (tokensToRefine.isEmpty())
        advanceLeft();
    }
  }

  /** Accept a "blank" token. */
  public void blank() {
    if (tokensToRefine.isEmpty()) {
      rightTotal = 1;
    } else if (tokensToRefine.peek() instanceof RefinedBlankToken) {
      tokensToRefine.pop().length += rightTotal;
    }
    RefinedToken token = new RefinedBlankToken(-rightTotal);
    tokenQueue.add(token);
    tokensToRefine.push(token);
    rightTotal++;
  }

  /**
   * Accept a "string" token containing a given string.
   *
   * @param string the contents of the token.
   */
  public void string(CharSequence string) throws IOException {
    RefinedToken token = new RefinedStringToken(string);
    if (tokensToRefine.isEmpty()) {
      token.print(printState);
    } else {
      tokenQueue.add(token);
      rightTotal += token.length;
      while (rightTotal - leftTotal > printState.space) {
        tokensToRefine.removeLast().length = Long.MAX_VALUE;
        advanceLeft();
      }
    }
  }

  /**
   * Accept a "string" token containing a given single character.
   *
   * @param c the contents of the token.
   */
  public void string(char c) throws IOException {
    string(String.valueOf(c));
  }

  /**
   * Accept a "string" token containing a given character array.
   *
   * @param chars the contents of the token.
   */
  public void string(char[] chars) throws IOException {
    string(String.valueOf(chars));
  }

  private void advanceLeft() throws IOException {
    while (!tokenQueue.isEmpty() && tokenQueue.peek().length >= 0) {
      final RefinedToken token = tokenQueue.poll();
      token.print(printState);
      if (token instanceof RefinedBlankToken) leftTotal++;
      else if (token instanceof RefinedStringToken) leftTotal += token.length;
    }
  }
}
