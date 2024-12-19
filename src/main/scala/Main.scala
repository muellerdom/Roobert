import Controller.Controller
import Model.REPL
import View.{GUI, TUI}

import scala.concurrent.Future
import scala.io.StdIn.readLine

object Main {
  val controller = new Controller()
  REPL.replBind(controller)
  val tui = new TUI(controller)
  GUI.setController(controller)
  //GUI.start()
  controller.notifyObservers()

//Das ist ein Kommentar

  def main(args: Array[String]): Unit = {

    // TUI im Hauptthread
    Future {
      GUI.main(args)
    }(scala.concurrent.ExecutionContext.global)

    // GUI im separaten Thread starten (Dadurch wird sichergestellt,dass
    // TUI-Ereignisse weiterhin verarbeitet werden können)
    new Thread(() =>       tui.start())


    if (args.nonEmpty) {
      tui.processInputLine(args(0))
    } else {
      var input: String = ""
      do {
        input = readLine().toString
        tui.processInputLine(input)
      } while (input != "q")
    }
  }
}
