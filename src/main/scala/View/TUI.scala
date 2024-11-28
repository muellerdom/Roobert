package View

import Controller.{Controller, Coordinate, LevelConfig}
import Model.Spieler
import Util.Observer

object TUI extends Observer {

  var player: Option[Spieler] = None
  var remainingJerms: List[Coordinate] = List()
  private var controller: Controller = _

  def initialize(controller: Controller): Unit = {
    this.controller = controller
    controller.addObserver(this)
  }

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

  def waitForLevelInput(): Unit = {
    val scanner = new java.util.Scanner(System.in)
    var input: String = ""

    do {
      input = scanner.nextLine().trim
      processInputLine(input)
    } while (input != "q")
  }

  def processInputLine(input: String): Unit = {
    if (input == "q") {
      println("Beende die Anwendung...")
    } else {
      controller.startLevel(input) match {
        case Right(foundLevel) =>
          println(s"Starte Level ${foundLevel.level}: ${foundLevel.description}")
          initializePlayer(foundLevel)
          remainingJerms = foundLevel.objects.jerm
          displayGrid(foundLevel)
          waitForPlayerActions(foundLevel)

        case Left(errorMessage) =>
          println(errorMessage)
      }
    }
  }

  def initializePlayer(level: LevelConfig): Unit = {
    player = Some(new Spieler(level.start.x, level.start.y, level.width, level.height))
  }

  def displayGrid(level: LevelConfig): Unit = {
    val grid = Array.fill(level.height, level.width)(' ')

    // Set the goal
    grid(level.goal.y)(level.goal.x) = 'G'

    // Set the obstacles
    level.objects.obstacles.foreach { obstacle =>
      grid(obstacle.coordinates.y)(obstacle.coordinates.x) = 'X'
    }

    // Set the jerms
    remainingJerms.foreach { jerm =>
      grid(jerm.y)(jerm.x) = 'J'
    }

    // Set the player
    player match {
      case Some(p) => grid(p.posY)(p.posX) = 'P'
      case None => // No player initialized
    }

    // Print the grid
    println("Spielfeld:")
    println("+" + ("---+" * level.width))
    for (row <- grid) {
      println("| " + row.mkString(" | ") + " |")
      println("+" + ("---+" * level.width))
    }
  }

  def waitForPlayerActions(level: LevelConfig): Unit = {
    val scanner = new java.util.Scanner(System.in)
    var action: String = ""

    println("Gib die Methoden ein um Robert zu bewegen " +
      "(moveForward(), turnRight(), turnLeft(), q zum Beenden):")

    do {
      action = scanner.nextLine().trim
      if (action == "moveForward()" || action == "turnRight()" || action == "turnLeft()") {
        movePlayer(action, level)
        if (isLevelComplete(level)) {
          println("Herzlichen Glückwunsch! Robert ist angekommen!")
          displayGrid(level)
          start()
        } else {
          displayGrid(level)
        }
      } else if (action != "q") {
        println("Unbekannter Befehl. Bitte versuche es erneut.")
      }
    } while (action != "q")

    println("Spiel beendet.")
  }

  def movePlayer(action: String, level: LevelConfig): Unit = {
    player.foreach { p =>
      p.move(action, level)
      remainingJerms = remainingJerms.filterNot(jerm => p.eingesammelteJerms.contains(jerm))
    }
  }

  def isLevelComplete(level: LevelConfig): Boolean = {
    player match {
      case Some(p) =>
        p.eingesammelteJerms.size == level.objects.jerm.size && p.posX == level.goal.x && p.posY == level.goal.y
      case None => false
    }
  }

  // Observer update implementation
  override def update(): Unit = {
    println("Aktualisierung vom Controller erhalten.")
  }
}
