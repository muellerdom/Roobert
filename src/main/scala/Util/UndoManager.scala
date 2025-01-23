package Util

import scala.collection.mutable.Stack

trait Command {
  def doStep(): Unit
  def undoStep(): Unit
  def redoStep(): Unit
}

class UndoManager {
  private val undoStack = new Stack[Command]()
  private val redoStack = new Stack[Command]()

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

  def getUndoStack: Stack[Command] = undoStack
  def getRedoStack: Stack[Command] = redoStack
}