package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Model.LevelConfig
import Util.Observer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, TextArea}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}
import scalafx.scene.shape.SVGPath
import scalafx.scene.text.Font

class GameView(controller: Controller) extends BorderPane with Observer {

  // Linke Seite: Spielfeld
  private val gridPane = new GridPane {
    hgap = 5
    vgap = 5
    padding = Insets(10)
    style = "-fx-background-color: #e0f7fa; -fx-border-color: #00796b; -fx-border-width: 2px;"
  }
  refreshGrid()

  // Rechte Seite: IDE / Platzhalter für Code-Editor
  private val codeEditor = new VBox {
    spacing = 10
    padding = Insets(10)
    alignment = Pos.TopCenter
    style = "-fx-background-color: #444243; -fx-border-color: #444243; -fx-border-width: 2px;"

    val textarea = new TextArea {
      prefWidth = 300
      prefHeight = 300
      promptText = "Schreibe deinen Code hier..."
    }

    children = Seq(
      new Label("Code Editor") {
        style = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #d84315;"
      },
      new VBox {
        prefWidth = 300
        prefHeight = 300
      },
      textarea,
      new Button("Meinen Code ausführen") {
        onAction = _ =>
        {
          controller.repl(textarea.getText)
        }
      }
    )
  }

  // Obere Leiste: Titel und Level-Info
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

  // Untere Leiste: Navigation
  private val bottomBar = new HBox {
    spacing = 20
    padding = Insets(10)
    alignment = Pos.Center
    children = Seq(
      new Button("Zurück zur Level-Auswahl") {
        font = Font("Arial", 14)
        onAction = _ => GUI.switchToLevelView(controller)
        style = "-fx-background-color: #00796b;" +
          " -fx-text-fill: white;" +
          " -fx-font-weight: bold;"
      }
    )
  }

  // Layout setzen
  top = topBar
  center = gridPane
  right = codeEditor
  bottom = bottomBar


  // Aktualisiere das Spielfeld
  private def refreshGrid(): Unit = {
    controller.getLevelConfig.foreach { level =>
      gridPane.children.clear()

      // Lade die Bilder
      val playerImage = new Image("file:src/main/resources/crew-member-5701346_1280.png")
      val rockImage = new Image("file:src/main/resources/rocks-576661_1280.png")
      val woodImage = new Image("file:src/main/resources/tree-576145_1280.png")
      val jermImage = new Image("file:src/main/resources/stone-576371_1280.png")
      val goalImage = new Image("file:src/main/resources/grand-prix-7561576_1280.png")

      // Iteriere über alle Zellen des Spielfelds
      for (y <- 0 until level.height) {
        for (x <- 0 until level.width) {
          val cell = new Label {
            // Bestimme das Bild für diese Zelle
            val cellImage = getCellImage(x, y, level, playerImage, goalImage, rockImage, woodImage, jermImage)

            val imageView = new ImageView(cellImage) {
              fitWidth = 50
              fitHeight = 50
            }

            graphic = imageView
            style = "-fx-border-color: black; -fx-alignment: center; " +
              "-fx-background-color: #ffffff;"
            prefWidth = 50
            prefHeight = 50
          }

          // Setze die Position der Zelle im GridPane
          GridPane.setConstraints(cell, x, level.height - y - 1)
          gridPane.children += cell
        }
      }
    }
  }

  // Bestimme das Bild für jedes Objekt
  def getCellImage(x: Int, y: Int, level: LevelConfig, playerImage: Image, goalImage: Image, rockImage: Image, woodImage: Image, jermImage: Image): Image = {
    // Überprüfen, ob der Spieler an dieser Position ist
    if (controller.getGrid(x, y) == 'R') {
      playerImage
    }
    else if (controller.getGrid(x, y) == 'G') {
      goalImage
    }
    // Überprüfen, ob ein Hindernis an dieser Position ist
    else if (level.objects.obstacles.exists(obj => obj.coordinates.x == x
      && obj.coordinates.y == y)) {
      val obstacle = level.objects.obstacles.find(obj => obj.coordinates.x == x
        && obj.coordinates.y == y)
      obstacle match {
        case Some(obj) => obj.`type` match {
          case "stein" => rockImage
          case "holz" => woodImage
          case _ => new Image("file:src/main/resources/grass-157739_1280.png")
        }
        case None => new Image("file:src/main/resources/grass-157739_1280.png")
      }
    }
    // Überprüfen, ob ein Jerm an dieser Position ist
    else if (controller.getGrid(x, y) == 'J') {
      jermImage
    }
    else {
      new Image("file:src/main/resources/path-575594_640.png") // Leere Zelle, wenn nichts anderes vorhanden
    }
  }


  override def update(): Unit = refreshGrid()
}
