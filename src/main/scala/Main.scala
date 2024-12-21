import Controller.Component.ControllerBaseImpl.Controller
import View.TUI
import View.gui.GUI

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn.readLine

object Main {
  val controller = new Controller()
  val tui = new TUI(controller)
  GUI.setController(controller)

  def main(args: Array[String]): Unit = {
    // GUI im Hauptthread starten
    Future {
      GUI.main(args)
    }

    // TUI in einem separaten Thread ausführen
    Future {
      startTUI(args)
    }

    // Initiale Observer-Benachrichtigung
    controller.notifyObservers()
  }

  /** Startet die TUI und verarbeitet Eingaben */
  private def startTUI(args: Array[String]): Unit = {
    tui.start() // TUI initialisieren

    if (args.nonEmpty) {
      tui.processInputLine(args(0)) // Argumente direkt verarbeiten
    } else {
      var input: String = ""
      do {
        input = readLine()
        tui.processInputLine(input)
      } while (input != "q") // Beenden bei "q"
    }
  }
}
