package View

import Util.Observer
import Model.{GameManager, GameModel}

abstract class GameView extends Observer {
  // Template-Methode, die den Rendering-Ablauf definiert
  final def renderView(): Unit = {
    val board = GameManager.getInstance(5).getBoard // Holen des aktuellen Spielfelds aus dem Modell

    // Phasen des Renderns ausführen
    val stages = List(
      new DisplayBoardStage(board, this),
      new DisplayAdditionalInfoStage(this),
      new DisplayMessageStage("Spiel läuft...", this)
    )

    stages.foreach(_.execute()) // Jede Phase wird nacheinander ausgeführt
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

// Die ViewStage-Abstract-Klasse, die den Ablauf jeder Phase definiert
abstract class ViewStage {
  def execute(): Unit
}

// Die konkrete Implementierung für das Anzeigen des Spielfelds
class DisplayBoardStage(board: Array[Array[Char]], gameView: GameView) extends ViewStage {
  override def execute(): Unit = {
    gameView.displayBoard(board)
  }
}

// Die konkrete Implementierung für das Anzeigen zusätzlicher Informationen
class DisplayAdditionalInfoStage(gameView: GameView) extends ViewStage {
  override def execute(): Unit = {
    gameView.displayAdditionalInfo()
  }
}

// Die konkrete Implementierung für das Anzeigen von Nachrichten
class DisplayMessageStage(message: String, gameView: GameView) extends ViewStage {
  override def execute(): Unit = {
    gameView.displayMessage(message)
  }
}

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
