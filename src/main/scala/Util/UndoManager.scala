package Util

class UndoManager {
  private var undoStack: List[Command] = Nil
  private var redoStack: List[Command] = Nil

  def doStep(command: Command): Unit = {
    undoStack = command :: undoStack
    redoStack = Nil // Redo-Stack löschen, weil neuer Zustand erzeugt wurde
    command.doStep()
  }

  def undoStep: Unit = {
    undoStack match {
      case Nil => println("Keine Schritte zum Rückgängigmachen!")
      case head :: tail =>
        head.undoStep()
        undoStack = tail
        redoStack = head :: redoStack
    }
  }

  def redoStep: Unit = {
    redoStack match {
      case Nil => println("Keine Schritte zum Wiederherstellen!")
      case head :: tail =>
        head.redoStep()
        redoStack = tail
        undoStack = head :: undoStack
    }
  }
}
