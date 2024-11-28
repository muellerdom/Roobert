package View

import Controller.{Controller, Coordinate, LevelConfig}
import Model.Spieler
import Util.Observer

class TUI(controller: Controller) extends Observer {

  controller.addObserver(this)
  var player: Option[Spieler] = None
  var remainingJerms: List[Coordinate] = List()

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

    grid(level.goal.y)(level.goal.x) = 'G'

    level.objects.obstacles.foreach { obstacle =>
      grid(obstacle.coordinates.y)(obstacle.coordinates.x) = 'X'
    }

    remainingJerms.foreach { jerm =>
      grid(jerm.y)(jerm.x) = 'J'
    }

    player.foreach { p =>
      grid(p.posY)(p.posX) = 'P'
    }

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

    println("Gib die Methoden ein um Robert zu bewegen (moveForward(), turnRight(), turnLeft(), q zum Beenden):")

    do {
      action = scanner.nextLine().trim
      if (action == "moveForward()" || action == "turnRight()" || action == "turnLeft()") {
        movePlayer(action, level)
        if (isLevelComplete(level)) {
          println("Herzlichen Glückwunsch! Robert ist angekommen!")
          displayGrid(level)
          start()  // Nach Levelabschluss starte erneut das Spiel
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

  // Implementierung der update() Methode, die von Observer aufgerufen wird
  override def update(): Unit = {
    println("Aktualisierung erhalten!")
    player.foreach { p =>
      println(s"Spieler Position: (${p.posX}, ${p.posY})")
      println(s"Verbleibende Jerms: ${remainingJerms.size}")
    }
    // Hier könnte auch das Spielfeld oder andere Informationen aktualisiert werden.
  }
}
