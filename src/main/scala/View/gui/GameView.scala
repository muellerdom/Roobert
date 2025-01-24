// GameView.scala
package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Model.GridComponent.Coordinate
import Util.Observer
import com.google.inject.Inject
import scalafx.application.Platform
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, TextArea}
import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}
import scalafx.scene.paint.Color

class GameView @Inject() (controller: Controller, gui: GUI) extends BorderPane with Observer {
  private val gridPane = new GridPane {
    hgap = 5
    vgap = 5
    padding = Insets(10)
    style = "-fx-background-color: #2B2B2B; -fx-border-color: #6897BB; -fx-border-width: 2px;"
  }
  update()

  //controller.notifyObservers()

  private val instructions = new Label("Anweisungen: Schreiben Sie Ihren Code im Editor und klicken Sie auf 'Meinen Code ausführen', um das Spiel zu aktualisieren.") {
    style = "-fx-font-size: 14px; -fx-text-fill: #2B2B2B;"
    wrapText = true
    maxWidth = 300
  }

  private val codeEditor = new VBox {
    spacing = 10
    padding = Insets(10)
    alignment = Pos.TopCenter
    style = "-fx-background-color: #A9B7C6;"

    val textarea = new TextArea {
      prefWidth = 300
      prefHeight = 300
      promptText = "Schreibe deinen Code hier..."
      style = "-fx-control-inner-background: #2B2B2B; -fx-text-fill: #F5F5F5;"
    }

    children = Seq(
      instructions,
      new Label("Code Editor") {
        style = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0077CC;"
      },
      textarea,
      new Button("Meinen Code ausführen") {
        onAction = _ => {
          controller.interpret(textarea.getText)
          //controller.notifyObservers() // Notify all observers after executing the command
        //update()
        }
        style = "-fx-background-color: #6897BB; -fx-text-fill: #2B2B2B;"
      }
    )
  }

  private val topBar = new VBox {
    spacing = 10
    padding = Insets(10)
    alignment = Pos.Center
    style = "-fx-background-color: #6897BB;"
    children = Seq(
      new Label("Spielansicht") {
        style = "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2B2B2B;"
      },
      new Label(s"Methoden um den Spieler zu bewegen: moveForward(), turnRight(), turnLeft()") {
        style = "-fx-font-size: 14px; -fx-text-fill: #2B2B2B;"
      }
    )
  }

  private val bottomBar = new HBox {
    spacing = 20
    padding = Insets(10)
    alignment = Pos.Center
    style = "-fx-background-color: #6897BB;"
    children = Seq(
      new Button("Zurück zur Level-Auswahl") {
        onAction = _ => gui.switchToLevelView()
        style = "-fx-background-color: #2B2B2B; -fx-text-fill: #6897BB; -fx-font-weight: bold;"
      }
    )
  }

  top = topBar
  center = gridPane
  right = codeEditor
  bottom = bottomBar
  padding = Insets(20)


  private def refreshGrid(): Unit = {
    controller.getLevelConfig.foreach { level =>
      gridPane.children.clear()

      for (y <- 0 until level.logic.gridSize.get.height; x <- 0 until level.logic.gridSize.get.width) {
        //controller.getGrid.gridSegments.findByPosition(Coordinate(x, y)).get.Symbol
        val text = controller.getGrid.gridSegments.findByPosition(Coordinate(x, y)).map(_.Symbol).getOrElse(' ') match {
          case 'R' => "R"
          case 'G' => "G"
          case 'J' => "J"
          case 'X' => "X"
          case ' ' => " "
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

        GridPane.setConstraints(cell, x, level.logic.gridSize.get.height - y - 1)
        gridPane.children.add(cell)
      }
    }
  }

  override def update(): Unit = {
    if (!Platform.isFxApplicationThread) {
      Platform.runLater(() => update())
      return
    }
    // Nur aktualisieren, wenn ein gültiges Level geladen wurde
    println("Update called from GAMEUI")

    if (controller.getLevelConfig.isDefined) refreshGrid()
    else println("No level to refresh")

    controller.notifyObservers()
  }
}