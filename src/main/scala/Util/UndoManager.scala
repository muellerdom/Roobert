package Util

import scala.collection.mutable



class UndoManager {
  private val undoStack = new mutable.Stack[Command]()
  private val redoStack = new mutable.Stack[Command]()

  def doStep(command: Command): Unit = {
    command.doStep()
    undoStack.push(command)
    redoStack.clear()
  }

  def undoStep(): Unit = {
    if (undoStack.nonEmpty) {
      val command = undoStack.pop()
      command.undoStep()
      redoStack.push(command)
    }
  }

  def redoStep(): Unit = {
    if (redoStack.nonEmpty) {
      val command = redoStack.pop()
      command.redoStep()
      undoStack.push(command)
    }
  }

  def getUndoStack: mutable.Stack[Command] = undoStack
  def getRedoStack: mutable.Stack[Command] = redoStack
}