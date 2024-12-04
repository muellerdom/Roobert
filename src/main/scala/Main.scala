import Controller.Controller
import Model.REPL
import View.TUI

import scala.io.StdIn.readLine

object Main {
  val controller = new Controller
  REPL.replBind(controller)
  val tui = new TUI(controller)
  controller.notifyObservers()
  tui.start()

  def main(args: Array[String]): Unit = {
    if (args.nonEmpty) {
      tui.processInputLine(args(0))
    } else {
      var input: String = ""
      do {
        input = readLine()
        tui.processInputLine(input)
      } while (input != "q")
    }
  }
}
