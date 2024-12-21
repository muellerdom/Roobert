// LevelView.scala
package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.VBox
import scalafx.scene.text.Font

class LevelView(controller: Controller) extends VBox with Observer {

  spacing = 15
  padding = Insets(20)
  alignment = Pos.Center

  val titleLabel: Label = new Label("Wähle ein Level aus!") {
    style = "-fx-font-size: 18px; -fx-font-weight: bold;"
  }

  private val levelButtonsContainer: VBox = new VBox {
    spacing = 10
    alignment = Pos.Center
  }

  children = Seq(titleLabel, levelButtonsContainer)
  refreshLevels()

  def refreshLevels(): Unit = {
    val levels = controller.getAvailableLevels
    levelButtonsContainer.children.clear()

    if (levels.isEmpty) {
      levelButtonsContainer.children += new Label("Keine Levels verfügbar!") {
        font = Font("Arial", 14)
      }
    } else {
      levelButtonsContainer.children ++= levels.map { levelName =>
        new Button(levelName) {
          font = Font("Arial", 14)
          onAction = _ => {
            controller.startLevel(levelName) match {
              case Right(_) => GUI.switchToGameView(controller)
              case Left(error) => println(error)
            }
          }
        }
      }
    }
  }

  override def update(): Unit = {
    refreshLevels()
  }
}