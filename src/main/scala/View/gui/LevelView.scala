// LevelView.scala
package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observer
import com.google.inject.Inject
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Font

class LevelView @Inject() (controller: Controller, gui: GUI) extends VBox with Observer {

  spacing = 15
  padding = Insets(20)
  alignment = Pos.Center
  style = "-fx-background-color: #A9B7C6;" // Secondary color: Light Gray
  prefWidth = 600 // Set preferred width
  prefHeight = 400 // Set preferred height

  val titleLabel: Label = new Label("Wähle ein Level aus!") {
    style = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2B2B2B;" // Dark Gray
  }

  val welcomeText: Label = new Label("Willkommen zu Roobert. Zusammen lernen wir Scala und entdecken die Welt der Programmierung!") {
    style = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2B2B2B;" // Dark Gray
    wrapText = true
    maxWidth = 500 // Increase max width
  }

  private val levelButtonsContainer: HBox = new HBox {
    spacing = 20
    alignment = Pos.Center
  }

  // Add the labels and container to the VBox children
  children = Seq(titleLabel, welcomeText, levelButtonsContainer)
  refreshLevels()

  def refreshLevels(): Unit = {
    val levels = controller.getAvailableLevels
    levelButtonsContainer.children.clear()

    if (levels.isEmpty) {
      levelButtonsContainer.children += new Label("Keine Levels verfügbar!") {
        font = Font("Arial", 14)
        style = "-fx-text-fill: #2B2B2B;" // Dark Gray
      }
    } else {
      levelButtonsContainer.children ++= levels.map { levelName =>
        new Button(levelName) {
          font = Font("Arial", 16)
          style = "-fx-background-color: #2B2B2B; -fx-text-fill: #6897BB;" // Dark Gray, Blue
          effect = new DropShadow {
            color = Color.Gray
            radius = 5
            spread = 0.2
          }
          prefWidth = 150
          prefHeight = 50
          onAction = _ => {
            controller.startLevel(levelName) match {
              case Right(_) => gui.switchToGameView()
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