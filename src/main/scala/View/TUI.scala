package View

import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.shell.ReplReporterImpl
import scala.tools.nsc.Settings
import Controller.Controller
import Util.Observer


class TUI(controller: Controller) extends Observer {

  controller.addObserver(this)

  def start(): Unit = {
    val availableLevels = controller.getAvailableLevels

    println("Hilf Roobert!")
    if (availableLevels.nonEmpty) {
      println("Verfügbare Levels:")
      availableLevels.foreach(level => println(s"- $level"))

      println("Bitte gib ein Level an, mit dem du starten möchtest:")
      waitForLevelInput()
    } else {
      println("Keine verfügbaren Levels gefunden. Bitte überprüfe die Leveldatei.")
    }
  }

  private def waitForLevelInput(): Unit = {
    val scanner = new java.util.Scanner(System.in)

    var levelName = ""
    do {
      println("Bitte wähle ein Level oder 'q' zum Beenden:")
      levelName = scanner.nextLine().trim
      processInputLine(levelName)
    } while (levelName != "q")
  }

  def processInputLine(input: String): Unit = {
    if (input == "q") {
      println("Beende die Anwendung...")
    } else {
      controller.startLevel(input) match {
        case Right(foundLevel) =>
          println(s"Starte Level ${foundLevel.level}: ${foundLevel.description}")
          displayGrid()
          waitForPlayerActions()
        case Left(errorMessage) =>
          println(errorMessage)
      }
    }
  }

  def displayGrid(): Unit = {
    controller.getLevelConfig match {
      case Some(level) =>
        println("Spielfeld:")
        println("+" + ("---+" * level.width))
        for (y <- level.height - 1 to 0 by -1) {
          for (x <- 0 until level.width) {
            val symbol = controller.getGrid(x, y)
            print(s"| $symbol ")
          }
          println("|")
          println("+" + ("---+" * level.width))
        }
      case None =>
        println("Kein aktuelles Level geladen.")
    }
  }

  def waitForPlayerActions(): Unit = {
    val scanner = new java.util.Scanner(System.in)
    var action = ""
    val codeBlock = new StringBuilder


    do {
      action = scanner.nextLine().trim

      action.toLowerCase match {
        case "q" =>
          println("Spiel beendet.")
        case "z" => controller.undo()
          displayGrid()

        case "y" => controller.redo()
          displayGrid()
        case "compile" =>
          // Führe den gesammelten Codeblock aus
          val code = codeBlock.toString()
          //repl.interpret(code)
          controller.repl(code)
          // Lösche den Codeblock für die nächste Runde
          codeBlock.clear()

          if (controller.isLevelComplete) {
            println("Herzlichen Glückwunsch! Robert ist angekommen!")
            displayGrid()
            start() // Startet das Spiel neu
          } else {
            displayGrid()
          }

        case _ =>
          // Sammle Eingaben
          codeBlock.append(action).append("\n")
      }

    } while (action.toLowerCase != "q")

    println("Spiel beendet.")
  }

  override def update(): Unit = {
    println("Aktualisierung vom Controller erhalten.")
  }
}
