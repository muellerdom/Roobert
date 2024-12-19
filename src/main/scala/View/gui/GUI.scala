package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observer
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.Pane

object GUI extends JFXApp3 with Observer {

  // Optionaler Controller
  private var controller: Option[Controller] = None

  // Setzt den Controller und registriert sich als Observer
  def setController(controller: Controller): Unit = {
    this.controller = Some(controller)
    controller.addObserver(this)
  }

  // Initialisierung und Start der GUI
  override def start(): Unit = {
    // Hauptstage (bereitgestellt von JFXApp3)
    stage = new JFXApp3.PrimaryStage {
      title = "Hilf Roobert"
      scene = new Scene(400, 300) {
        root = new Pane() // Initial leerer Inhalt
      }
    }

    stage.show() // Zeige das Fenster an

    // Wechsel in die LevelView (wenn Controller verfügbar)
    //controller.foreach(switchToLevelView)

    // Starte erst nach der GUI-Initialisierung
    Platform.runLater(() => {
      controller.foreach(_.notifyObservers())
    })
  }

  // View-Wechsel-Funktion: Tausche den root-Knoten der Scene aus
  private def setView(newRoot: Pane): Unit = {
    Platform.runLater(() => {
      stage.scene = new Scene(800, 600) {
        root = newRoot
      }
    })
  }

  // Wechsle zur Level-Auswahl-Ansicht
  def switchToLevelView(controller: Controller): Unit = {
    println("Wechsle zur LevelView.")
    setView(new LevelView(controller))
  }

  // Wechsle zur Spiel-Ansicht
  def switchToGameView(controller: Controller): Unit = {
    println("Wechsle zur GameView.")
    setView(new GameView(controller))
  }

  // Automatische Aktualisierungen basierend auf dem Controller
  override def update(): Unit = {
    if (!Platform.isFxApplicationThread) {
      println("Toolkit nicht initialisiert, update wird übersprungen.")
      return
    }

    Platform.runLater(() => {
      controller.foreach { ctrl =>
        if (ctrl.getLevelConfig.isDefined) {
          switchToGameView(ctrl)
        } else {
          switchToLevelView(ctrl)
        }
      }
    })
  }
}
