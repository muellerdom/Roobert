//package Controller
//
//import Model.{Coordinate, Spieler, Spielfeld}
//
//case class SpielStatus(grid: Array[Array[Char]], playerPosition: Coordinate)
//
//class Spiel {
//  private var currentState: SpielStatus = _
//  private val undoStack = collection.mutable.Stack[SpielStatus]()
//  private val redoStack = collection.mutable.Stack[SpielStatus]()
//
//  def saveState(): Unit = {
//    currentState = SpielStatus(Spielfeld.getSpielfeld.map(_.clone), Spieler.getPosition)
//    undoStack.push(currentState)
//    redoStack.clear()
//  }
//
//  def undo(): Unit = {
//    if (undoStack.nonEmpty) {
//      redoStack.push(currentState)
//      currentState = undoStack.pop()
//      restoreState()
//    } else println("Keine Schritte zum Rückgängigmachen!")
//  }
//
//  def redo(): Unit = {
//    if (redoStack.nonEmpty) {
//      undoStack.push(currentState)
//      currentState = redoStack.pop()
//      restoreState()
//    } else println("Keine Schritte zum Wiederherstellen!")
//  }
//
//  private def restoreState(): Unit = {
//    Spielfeld.setup(currentState.grid.map(_.clone))
//    Spieler.position = Some(currentState.playerPosition)
//    Spielfeld.notifyObservers()
//  }
//
//  def movePlayer(action: String): Unit = {
//    saveState() // Zustand vor der Bewegung speichern
//    Spieler.move(action)
//    Spielfeld.hinsetze(Spieler.getPosition.x, Spieler.getPosition.y, 'R')
//    Spielfeld.notifyObservers()
//  }
//}
