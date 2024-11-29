package View

import Model.GameModel
import Util.Observer

// Abstrakte Klasse für Views
abstract class GameView extends Observer {
  def update(): Unit

  // Diese Methode zeigt das Spielfeld an
  def displayBoard(board: Array[Array[Char]]): Unit

  // Diese Methode zeigt Nachrichten im Spiel an
  def displayMessage(message: String): Unit
}

// Eine konkrete GameView-Implementierung (z. B. eine einfache Darstellung des Spiels)
class SimpleGameView extends GameView {
  override def update(): Unit = {
    val board = GameModel.getBoard // Hier wird das aktuelle Spielfeld aus dem Modell geholt
    displayBoard(board)
  }

  override def displayBoard(board: Array[Array[Char]]): Unit = {
    println("Einfaches Spielfeld:")
    for (row <- board) {
      println(row.mkString(" "))
    }
  }

  override def displayMessage(message: String): Unit = {
    println(s"Nachricht: $message")
  }
}

// Eine detaillierte GameView-Implementierung (für eine erweiterte Ansicht)
class DetailedGameView extends GameView {
  override def update(): Unit = {
    val board = GameModel.getBoard
    displayBoard(board)
  }

  override def displayBoard(board: Array[Array[Char]]): Unit = {
    println("Detaillierte Ansicht des Spielfelds:")
    for (row <- board) {
      println(row.mkString(" | "))
    }
  }

  override def displayMessage(message: String): Unit = {
    println(s"Nachricht: $message (detailliert)")
  }
}

// Factory für die Erstellung von GameView-Instanzen
object GameViewFactory {

  // Methode, um die passende GameView zu erstellen
  def createView(viewType: String): GameView = {
    viewType match {
      case "simple"   => new SimpleGameView()
      case "detailed" => new DetailedGameView()
      case _          => throw new IllegalArgumentException(s"Unbekannter View-Typ: $viewType")
    }
  }
}
