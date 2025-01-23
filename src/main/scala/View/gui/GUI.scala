// GUI.scala
package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observer
import com.google.inject.Inject
import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.control.Label
import scalafx.scene.paint.Color
import scalafx.geometry.Pos
import scalafx.scene.layout.GridPane

class GUI @Inject() (controller: Controller) extends JFXApp3 with Observer {

  controller.addObserver(this)

  private val gridPane = new GridPane {
    hgap = 5
    vgap = 5
  }

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Help Robert"
      scene = new Scene(400, 300) {
        root = new Pane {
          style = "-fx-background-color: #2B2B2B;"
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
      println("WARNING: Stage is not initialized yet.")
      return
    }
    Platform.runLater(() => {
      stage.scene = new Scene(800, 600) {
        root = newRoot
        newRoot.setStyle("-fx-background-color: #2B2B2B;")
      }
    })
  }

  def switchToLevelView(): Unit = {
    setView(new LevelView(controller, this))
  }

  def switchToGameView(): Unit = {
    setView(new GameView(controller, this))
  }

  private def refreshGrid(): Unit = {
    controller.getLevelConfig.foreach { level =>
      gridPane.children.clear()

      for (y <- 0 until level.height; x <- 0 until level.width) {
        val text = controller.getSpielfeld.getAnPos(x, y) match {
          case 'R' => "R"
          case 'G' => "G"
          case 'J' => "J"
          case 'X' => "X"
          case _ => " "
        }

        val cell = new Label()
        cell.text = text
        cell.prefWidth = 50
        cell.prefHeight = 50
        cell.style = text match {
          case "R" => "-fx-background-color: #FF6F61; -fx-border-color: #6897BB; -fx-border-width: 1px;"
          case "G" => "-fx-background-color: #BBD968; -fx-border-color: #6897BB; -fx-border-width: 1px;"
          case "J" => "-fx-background-color: #D968A6; -fx-border-color: #6897BB; -fx-border-width: 1px;"
          case "X" => "-fx-background-color: #F2E394; -fx-border-color: #6897BB; -fx-border-width: 1px;"
          case _ => "-fx-background-color: #FFFFFF; -fx-border-color: #6897BB; -fx-border-width: 1px;"
        }
        cell.alignment = Pos.Center
        cell.textFill = Color.web("#2B2B2B")

        GridPane.setConstraints(cell, x, level.height - y - 1)
        gridPane.children.add(cell)
      }
    }
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

    refreshGrid()
  }
}