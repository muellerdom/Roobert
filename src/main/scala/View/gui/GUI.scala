package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observer
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.Pane

object GUI extends JFXApp3 with Observer {

  private var controller: Option[Controller] = None

  // Setzt den Controller und registriert sich als Observer
  def setController(controller: Controller): Unit = {
    this.controller = Some(controller)
    controller.addObserver(this)
  }

  // Initialisierung und Start der GUI
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Hilf Roobert"
      scene = new Scene(400, 300) {
        root = new Pane() // Initial leerer Inhalt
      }
    }

    stage.show()

    // Nach der Initialisierung des GUI-Threads Controller benachrichtigen
    Platform.runLater(() => {
      controller.foreach(_.notifyObservers())
    })
  }

  // View-Wechsel-Funktion: Tausche den root-Knoten der Scene aus
  private def setView(newRoot: Pane): Unit = {
    if (stage == null) {
      println("WARNUNG: Stage ist noch nicht initialisiert.")
      return
    }
    Platform.runLater(() => {
      stage.scene = new Scene(800, 600) {
        root = newRoot
      }
    })
  }

  // Wechsel zur Level-Auswahl-Ansicht
  def switchToLevelView(controller: Controller): Unit = {
    println("Wechsle zur LevelView.")
    setView(new LevelView(controller))
  }

  // Wechsel zur Spiel-Ansicht
  def switchToGameView(controller: Controller): Unit = {
    println("Wechsle zur GameView.")
    setView(new GameView(controller))
  }

  override def update(): Unit = {
    if (!Platform.isFxApplicationThread) {
      Platform.runLater(() => update())
      return
    }

    controller.foreach { ctrl =>
      if (stage == null) {
        println("WARNUNG: Stage ist noch nicht bereit für Updates.")
        return
      }

      if (ctrl.getLevelConfig.isDefined) {
        switchToGameView(ctrl)
      } else {
        switchToLevelView(ctrl)
      }
    }
  }
}
