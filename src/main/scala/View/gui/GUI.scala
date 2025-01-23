// GUI.scala
package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observer
import com.google.inject.Inject
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.Pane

// Die GUI-Klasse erweitert JFXApp3 und implementiert das Observer-Interface
class GUI @Inject() (controller: Controller) extends JFXApp3 with Observer {

  // Registriert die GUI als Observer des Controllers
  controller.addObserver(this)

  // Die start-Methode initialisiert das Hauptfenster der Anwendung
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Hilf Roobert" // Setzt den Titel des Fensters
      scene = new Scene(400, 300) { // Erstellt eine neue Szene mit den angegebenen Abmessungen
        root = new Pane { // Setzt das Root-Pane der Szene
          style = "-fx-background-color: #2B2B2B;" // Setzt die Hintergrundfarbe des Panes
        }
      }
    }

    stage.show() // Zeigt die Bühne an

    // Benachrichtigt die Observer, um den Anfangszustand zu aktualisieren
    Platform.runLater(() => {
      controller.notifyObservers()
    })
  }

  // Methode zum Setzen einer neuen Ansicht in der Bühne
  private def setView(newRoot: Pane): Unit = {
    if (stage == null) { // Überprüft, ob die Bühne initialisiert ist
      println("WARNUNG: Stage ist noch nicht initialisiert.") // Gibt eine Warnung aus, falls nicht
      return
    }
    // Aktualisiert die Szene mit dem neuen Root-Pane
    Platform.runLater(() => {
      stage.scene = new Scene(800, 600) { // Erstellt eine neue Szene mit den angegebenen Abmessungen
        root = newRoot // Setzt das neue Root-Pane
        newRoot.setStyle("-fx-background-color: #2B2B2B;") // Setzt die Hintergrundfarbe des neuen Root-Panes
      }
    })
  }

  // Methode zum Wechseln zur Level-Ansicht
  def switchToLevelView(): Unit = {
     setView(new LevelView(controller, this)) // Setzt die Ansicht auf LevelView
  }

  // Methode zum Wechseln zur Spiel-Ansicht
  def switchToGameView(): Unit = {

    setView(new GameView(controller, this)) // Setzt die Ansicht auf GameView
  }

  // Die update-Methode wird aufgerufen, wenn der Controller seine Observer benachrichtigt
  override def update(): Unit = {
    if (!Platform.isFxApplicationThread) { // Überprüft, ob der aktuelle Thread der JavaFX Application Thread ist
      Platform.runLater(() => update()) // Führt die update-Methode im JavaFX Application Thread aus
      return
    }

    // Wechselt zur entsprechenden Ansicht basierend auf dem Zustand des Controllers
    if (controller.getLevelConfig.isDefined) {
      switchToGameView() // Wechselt zur Spiel-Ansicht, wenn ein Level geladen ist
    } else {
      switchToLevelView() // Wechselt zur Level-Ansicht, wenn kein Level geladen ist
    }
  }
}