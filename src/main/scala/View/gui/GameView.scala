// GameView.scala
package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Model.LevelComponent.LevelConfig
import Util.Observer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, TextArea}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}
import scalafx.scene.text.Font

class GameView(controller: Controller) extends BorderPane with Observer {

  private val gridPane = new GridPane {
    hgap = 5
    vgap = 5
    padding = Insets(10)
    style = "-fx-background-color: #e0f7fa; -fx-border-color: #00796b; -fx-border-width: 2px;"
  }
  refreshGrid()

  private val codeEditor = new VBox {
    spacing = 10
    padding = Insets(10)
    alignment = Pos.TopCenter
    style = "-fx-background-color: #444243;"

    val textarea = new TextArea {
      prefWidth = 300
      prefHeight = 300
      promptText = "Schreibe deinen Code hier..."
    }

    children = Seq(
      new Label("Code Editor") {
        style = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #d84315;"
      },
      textarea,
      new Button("Meinen Code ausführen") {
        onAction = _ => controller.setCommand(textarea.getText)
      }
    )
  }

  private val topBar = new VBox {
    spacing = 10
    padding = Insets(10)
    alignment = Pos.Center
    children = Seq(
      new Label("Spielansicht") {
        style = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #004d40;"
      },
      new Label(s"Aktuelles Level: ${controller.getLevelConfig.getOrElse("Unbekannt")}") {
        style = "-fx-font-size: 14px;"
      }
    )
  }

  private val bottomBar = new HBox {
    spacing = 20
    padding = Insets(10)
    alignment = Pos.Center
    children = Seq(
      new Button("Zurück zur Level-Auswahl") {
        onAction = _ => GUI.switchToLevelView(controller)
        style = "-fx-background-color: #00796b; -fx-text-fill: white; -fx-font-weight: bold;"
      }
    )
  }

  top = topBar
  center = gridPane
  right = codeEditor
  bottom = bottomBar

  private def refreshGrid(): Unit = {
    controller.getLevelConfig.foreach { level =>
      gridPane.children.clear()

      val playerImage = new Image("file:src/main/resources/crew-member.png")
      val goalImage = new Image("file:src/main/resources/goal.png")
      val rockImage = new Image("file:src/main/resources/rock.png")

      for (y <- 0 until level.height; x <- 0 until level.width) {
        val image = controller.getSpielfeld.getAnPos(x, y) match {
          case 'R' => playerImage
          case 'G' => goalImage
          case _ if level.objects.obstacles.exists(o => o.coordinates.x == x && o.coordinates.y == y) => rockImage
          case _ => new Image("file:src/main/resources/empty.png")
        }

        val cell = new Label {
          graphic = new ImageView(image) {
            fitWidth = 50
            fitHeight = 50
          }
          prefWidth = 50
          prefHeight = 50
        }

        GridPane.setConstraints(cell, x, level.height - y - 1)
        gridPane.children.add(cell)
      }
    }
  }

  override def update(): Unit = {
    refreshGrid()
  }
}