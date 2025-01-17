// GUI.scala
package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observer
import com.google.inject.Inject
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.Pane

class GUI @Inject() (controller: Controller) extends JFXApp3 with Observer {

  controller.addObserver(this)

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Hilf Roobert"
      scene = new Scene(400, 300) {
        root = new Pane {
          style = "-fx-background-color: #2B2B2B;" // Liver Dogs
        }
      }
    }

    stage.show()

    Platform.runLater(() => {
      controller.notifyObservers()
    })
  }

  private def setView(newRoot: Pane): Unit = {
    if (stage == null) {
      println("WARNUNG: Stage ist noch nicht initialisiert.")
      return
    }
    Platform.runLater(() => {
      stage.scene = new Scene(800, 600) {
        root = newRoot
        newRoot.setStyle("-fx-background-color: #2B2B2B;") // Liver Dogs
      }
    })
  }

  def switchToLevelView(): Unit = {
    println("Wechsle zur LevelView.")
    setView(new LevelView(controller, this))
  }

  def switchToGameView(): Unit = {
    println("Wechsle zur GameView.")
    setView(new GameView(controller, this))
  }

  override def update(): Unit = {
    if (!Platform.isFxApplicationThread) {
      Platform.runLater(() => update())
      return
    }

    if (controller.getLevelConfig.isDefined) {
      switchToGameView()
    } else {
      switchToLevelView()
    }
  }
}