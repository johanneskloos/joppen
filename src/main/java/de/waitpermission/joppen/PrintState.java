package de.waitpermission.joppen;

import java.io.Writer;
import java.util.Stack;

class PrintState {
  final int margin;
  int space;
  final Stack<Integer> indentStack = new Stack<>();
  final Writer sink;

  public PrintState(int margin, Writer sink) {
    this.margin = margin;
    this.space = margin;
    this.sink = sink;
  }
}
