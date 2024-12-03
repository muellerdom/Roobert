package View

import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.shell.ReplReporterImpl
import scala.tools.nsc.Settings
import Controller.Controller
import Util.Observer


class TUI(controller: Controller) extends Observer {

  controller.addObserver(this)
  private val settings = new Settings
  settings.usejavacp.value = true // Set the class path
  val reporter = new ReplReporterImpl(settings)
  val repl = new IMain(settings, reporter)


  // Binde den Controller in den REPL-Kontext
  repl.bind("controller", "Controller.Controller", controller)
  repl.interpret("""def moveForward() = controller.movePlayer("forward")""")
  repl.interpret("""def turnRight() = controller.movePlayer("right")""")
  repl.interpret("""def turnLeft() = controller.movePlayer("left")""")



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
        for (y <- 0 until level.height) {
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

    do {
      action = scanner.nextLine().trim
      if (action.toLowerCase != "q") {

      //  if (action.contains("moveForward()") || action.contains("turnRight()") || action.contains("turnLeft()")) {
        //  repl.interpret("controller." + action)
        //} else {
          repl.interpret(action)
       // }
        if (controller.isLevelComplete) {
          println("Herzlichen Glückwunsch! Robert ist angekommen!")
          displayGrid()
          start() // Startet das Spiel neu
        } else {
          displayGrid()
        }
      }
    } while (action.toLowerCase != "q")

//    do {
//      println("Gib die Richtung an um Robert zu bewegen (moveForward(), turnRight(), turnLeft(), oder 'q' zum Beenden):")
//      action = scanner.nextLine().trim
//      repl.interpret(action)
//
//      if (action == "moveForward()" || action == "turnRight()" || action == "turnLeft()") {
//        controller.movePlayer(action)
//        if (controller.isLevelComplete) {
//          println("Herzlichen Glückwunsch! Robert ist angekommen!")
//          displayGrid()
//          start() // Startet das Spiel neu
//        } else {
//          displayGrid()
//        }
//      } else if (action != "q") {
//        println("Unbekannter Befehl. Bitte versuche es erneut.")
//      }
//    } while (action != "q")

    println("Spiel beendet.")
  }

  override def update(): Unit = {
    println("Aktualisierung vom Controller erhalten.")
  }
}
