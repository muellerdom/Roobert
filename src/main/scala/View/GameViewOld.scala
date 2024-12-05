package View

import Util.Observer
import Model.{GameManager, GameModel}

// Abstrakte Klasse für Views mit Template Method Pattern
abstract class GameView extends Observer {
  // Template-Methode, die den Ablauf definiert
  final def renderView(): Unit = {
    val board = GameManager.getInstance(5).getBoard // Holen des aktuellen Spielfelds aus dem Modell
    displayBoard(board) // Darstellung des Spielfelds
    displayAdditionalInfo() // Optionale zusätzliche Informationen, durch Subklassen definiert
  }

  // Diese Methode zeigt das Spielfeld an
  def displayBoard(board: Array[Array[Char]]): Unit

  // Diese Methode zeigt zusätzliche Informationen an (kann optional in Subklassen implementiert werden)
  def displayAdditionalInfo(): Unit = {}

  // Diese Methode zeigt Nachrichten im Spiel an
  def displayMessage(message: String): Unit

  // Update-Methode der Observer-Schnittstelle
  override def update(): Unit = renderView()
}

// Eine konkrete GameView-Implementierung (einfache Darstellung des Spiels)
class SimpleGameView extends GameView {
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

// Eine detaillierte GameView-Implementierung (erweiterte Darstellung)
class DetailedGameView extends GameView {
  override def displayBoard(board: Array[Array[Char]]): Unit = {
    println("Detaillierte Ansicht des Spielfelds:")
    for (row <- board) {
      println(row.mkString(" | "))
    }
  }

  override def displayAdditionalInfo(): Unit = {
    println("Zusätzliche Informationen: Hier könnten z. B. Spielstatistiken angezeigt werden.")
  }

  override def displayMessage(message: String): Unit = {
    println(s"Nachricht: $message (detailliert)")
  }
}

// Singleton-Objekt für GameView
object GameViewSingleton {
  // Private Variable zur Verwaltung der aktuellen View (nur innerhalb des Objekts zugänglich)
  private var currentView: Option[GameView] = None

  // Methode zum Festlegen der aktuellen View
  def setView(viewType: String): Unit = synchronized {
    if (currentView.isEmpty) { // Überprüfen, ob die View bereits gesetzt ist
      viewType match {
        case "simple"   => currentView = Some(new SimpleGameView())
        case "detailed" => currentView = Some(new DetailedGameView())
        case _          => throw new IllegalArgumentException(s"Unbekannter View-Typ: $viewType")
      }
    } else {
      println("Die View wurde bereits gesetzt und wird nicht überschrieben.")
    }
  }

  // Methode, um die aktuelle View zu erhalten
  def getCurrentView: Option[GameView] = currentView

  // Optional: Methode zum Zurücksetzen der View (nützlich für Tests)
  def resetView(): Unit = synchronized {
    currentView = None
  }
}
