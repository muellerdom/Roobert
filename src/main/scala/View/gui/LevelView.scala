package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color._
import scalafx.scene.text.Font

class LevelView(controller: Controller) extends VBox with Observer {
  // Layout-Einstellungen
  spacing = 15
  padding = Insets(20)
  alignment = Pos.Center

  // Überschrift der Level-Auswahl
  val titleLabel: Label = new Label("Wähle ein Level aus!") {
    style = "-fx-font-size: 18px; -fx-font-weight: bold;"
    textFill = Black
  }

  // Container für die Buttons
  private val levelButtonsContainer: VBox = new VBox() {
    spacing = 10
    alignment = Pos.Center
  }

  // Dynamische Elemente zur Ansicht hinzufügen
  children = Seq(titleLabel, levelButtonsContainer)

  // Initialer Aufbau der Ansicht
  refreshLevels()

  /** Aktualisiert die Liste der Levels dynamisch */
  def refreshLevels(): Unit = {
    val levels = controller.getAvailableLevels

    // Lege die Buttons neu an
    levelButtonsContainer.children.clear()
    if (levels.isEmpty) {
      // Zeige Nachricht an, wenn keine Levels vorhanden sind
      levelButtonsContainer.children += new Label("Keine Levels verfügbar!") {
        font = Font("Arial", 14)
        textFill = Black
      }
    } else {
      // Dynamische Buttons für jedes Level erstellen
      levelButtonsContainer.children ++= levels
        .filter(_ != "test-level")
        .map { levelName =>
          new Button(levelName) {
            font = Font("Arial", 14)
            prefWidth = 50
            prefHeight = 50

            onAction = _ => {
              //println(s"Level '$levelName' ausgewählt")

              controller.startLevel(levelName) // Starte das Level
              GUI.switchToGameView(controller) // Zur GameView wechseln
            }
          }
        }
    }
  }

  override def update(): Unit = ???
}