package View

import Controller.Controller
import Util.Observer
import scalafx.Includes.add
import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.{GridPane, HBox, Pane, Priority, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.paint.Color
import scalafx.scene.text.Text


object GameView extends JFXApp3 with Observer {

  var controller: Option[Controller] = None

  def setController(controller: Controller): Unit = {
    this.controller = Some(controller)
    controller.addObserver(this)
  }

  override def update(): Unit = {
    println("Aktualisierung vom Controller erhalten.")
    refreshGrid() // Aktualisiere das Spielfeld in der GUI
  }

  // Referenz auf das Spielfeld (GridPane)
  private val gridPane = new GridPane {
    hgap = 5 // horizontaler Abstand zwischen Feldern
    vgap = 5 // vertikaler Abstand zwischen Feldern
    padding = Insets(10) // Abstand innerhalb des Spielfeldes
  }


  /**
   * Aktualisiert das Spielfeld basierend auf dem Level aus dem Controller.
   */
  private def refreshGrid(): Unit = {
    controller.foreach { ctrl =>
      gridPane.children.clear() // Entferne vorherige Felder
      ctrl.getLevelConfig match {
        case Some(level) =>
          // Spielfeld konfigurieren
          for (y <- level.height - 1 to 0 by -1) {
            for (x <- 0 until level.width) {
              val symbol = ctrl.getGrid(x, y) // Symbol vom Controller abrufen

              val cell = new Label {
                text = symbol.toString // Setze das Symbol im Feld
                style = "-fx-border-color: black; -fx-alignment: center; -fx-font-size: 16pt;"
                prefWidth = 50 // Breite der Zelle
                prefHeight = 50 // Höhe der Zelle
              }

              // Füge die Zelle an die korrekten x/y-Koordinaten im GridPane ein
              GridPane.setConstraints(cell, x, level.height - 1 - y)
              gridPane.children.add(cell)
            }
          }
        case None =>
          // Falls kein Level geladen ist
          gridPane.children.add(new Label("Kein aktuelles Level geladen.") {
            style = "-fx-font-size: 14pt; -fx-alignment: center;"
          })
      }
    }
  }

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Hilf Roobert"
      scene = new Scene {
        fill = Color.rgb(38, 38, 38)
        root = new HBox { // Horizontale Anordnung
          padding = Insets(10)
          spacing = 5

          // Linkes Panel: Spielfeld
          children = Seq(
            new VBox {
              alignment = Pos.TopCenter
              style = "-fx-background-color: lightblue;" // Hintergrundeigenschaften
              padding = Insets(10)
              VBox.setVgrow(this, Priority.Always) // Vertikal dehnbar
              HBox.setHgrow(this, Priority.Always) // Horizontal dehnbar
              children = Seq(
                // Hier kannst du später GridPane für das Spielfeld hinzufügen

                gridPane // Das dynamische GridPane für das Spielfeld
              )
            },

            // Rechtes Panel: Eingaben und Buttons
            new VBox {
              alignment = Pos.TopCenter
              style = "-fx-background-color: lightgray;" // Hintergrundeigenschaften
              padding = Insets(10)
              VBox.setVgrow(this, Priority.Always) // Vertikal dehnbar
              HBox.setHgrow(this, Priority.Always) // Horizontal dehnbar
              children = Seq(
                new TextField {
                  promptText = "Gib einen Spielzug ein"
                },
                new Button("Compile") {
                  onAction = _ => {
                    println("Eingabe gesendet!")
                    // Hier könntest du die Eingabe an den Controller weitergeben
                  }
                }
              )
            }
          )
        }
      }
    }
    refreshGrid() // Initiale Darstellung des Spielfeldes
  }
}