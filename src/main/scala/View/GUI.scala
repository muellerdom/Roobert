package View

import Controller.Controller
import Util.Observer
import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.{HBox, Priority, VBox}
import scalafx.scene.paint.Color._
import scalafx.scene.paint.Color
import scalafx.scene.text.Text

object GUI extends JFXApp3 with Observer {

  var controller: Option[Controller] = None

  def setController(controller: Controller): Unit = {
    this.controller = Some(controller)
    controller.addObserver(this)
  }

  override def update(): Unit = {
    println("Aktualisierung vom Controller erhalten.")
    // Hier könnten Änderungen in GUI erfolgen, z.B. Spielfeld aktualisieren
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
              )
            },

            // Rechtes Panel: Eingaben und Buttons
            new VBox {
              spacing = 10
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
  }
}