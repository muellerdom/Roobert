// Main.scala
import com.google.inject.Guice
import Controller.Component.ControllerBaseImpl.Controller
import View.TUI
import View.gui.{GUI, GameView}
import javafx.application.Platform

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]): Unit = {
    Platform.startup(() => {
      val injector = Guice.createInjector(new GameModule()) // Create injector
      val controller = injector.getInstance(classOf[Controller])
      val gui = injector.getInstance(classOf[GUI])
      val gameView = injector.getInstance(classOf[GameView])
      controller.addObserver(gameView)

      // Start GUI in the main thread
      Future {
        gui.main(args)
      }

      // Run TUI in a separate thread
      Future {
        startTUI(args, controller)
      }

      // Initial observer notification
      controller.notifyObservers()
    })
  }

  private def startTUI(args: Array[String], controller: Controller): Unit = {
    val tui = new TUI(controller)
    tui.start() // Initialize TUI

    if (args.nonEmpty) {
      tui.processInputLine(args(0)) // Process arguments directly
    } else {
      var input: String = ""
      do {
        input = readLine()
        tui.processInputLine(input)
      } while (input != "q") // Exit on "q"
    }
  }
}