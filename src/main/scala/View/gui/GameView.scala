// GameView.scala
package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observer
import com.google.inject.Inject
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, TextArea}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}
import scalafx.scene.text.Font

class GameView @Inject() (controller: Controller, gui: GUI) extends BorderPane with Observer {
  private val gridPane = new GridPane {
    hgap = 5
    vgap = 5
    padding = Insets(10)
    style = "-fx-background-color: #2B2B2B; -fx-border-color: #6897BB; -fx-border-width: 2px; -fx-background-radius: 10px; -fx-border-radius: 10px;" // Rounded corners
  }
  refreshGrid()

  private val instructions = new Label("Anweisungen: Schreiben Sie Ihren Code im Editor und klicken Sie auf 'Meinen Code ausführen', um das Spiel zu aktualisieren.") {
    style = "-fx-font-size: 14px; -fx-text-fill: #2B2B2B; -fx-background-radius: 10px;" // Rounded corners
    wrapText = true
    maxWidth = 300
  }

  private val codeEditor = new VBox {
    spacing = 10
    padding = Insets(10)
    alignment = Pos.TopCenter
    style = "-fx-background-color: #A9B7C6; -fx-background-radius: 10px;" // Rounded corners

    val textarea = new TextArea {
      prefWidth = 300
      prefHeight = 300
      promptText = "Schreibe deinen Code hier..."
      style = "-fx-control-inner-background: #2B2B2B; -fx-text-fill: #F5F5F5; -fx-background-radius: 10px;" // Rounded corners
    }

    children = Seq(
      instructions,
      new Label("Code Editor") {
        style = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0077CC;" // Primary color: Blue
      },
      textarea,
      new Button("Meinen Code ausführen") {
        onAction = _ => {
          controller.setCommand(textarea.getText)
          refreshGrid() // Ensure the grid is refreshed after executing the code
        }
        style = "-fx-background-color: #6897BB; -fx-text-fill: #2B2B2B; -fx-background-radius: 10px;" // Rounded corners
      }
    )
  }

  private val topBar = new VBox {
    spacing = 10
    padding = Insets(10)
    alignment = Pos.Center
    style = "-fx-background-color: #6897BB; -fx-background-radius: 10px;" // Rounded corners
    children = Seq(
      new Label("Spielansicht") {
        style = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2B2B2B;" // Neutral color: Light Gray
      },
      new Label(s"Aktuelles Level: ${controller.getLevelConfig.getOrElse("Unbekannt")}") {
        style = "-fx-font-size: 14px; -fx-text-fill: #2B2B2B;" // Neutral color: Light Gray
      }
    )
  }

  private val bottomBar = new HBox {
    spacing = 20
    padding = Insets(10)
    alignment = Pos.Center
    style = "-fx-background-color: #6897BB; -fx-background-radius: 10px;" // Rounded corners
    children = Seq(
      new Button("Zurück zur Level-Auswahl") {
        onAction = _ => gui.switchToLevelView()
        style = "-fx-background-color: #2B2B2B; -fx-text-fill: #6897BB; -fx-font-weight: bold; -fx-background-radius: 10px;" // Rounded corners
      }
    )
  }

  top = topBar
  center = gridPane
  right = codeEditor
  bottom = bottomBar
  padding = Insets(20) // Add padding around the edges

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
          style = "-fx-background-radius: 10px;" // Rounded corners
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