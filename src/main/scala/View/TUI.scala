package View

import Controller.{Coordinate, LevelConfig}
import Model.Spieler
import Util.Observer

import java.lang.ModuleLayer
import java.lang.ModuleLayer.Controller

// Abstrakte TUI-Klasse, die das Template Method Pattern implementiert
abstract class TUITemplate private() extends Observer {

  protected var remainingJerms: List[Coordinate] = List()
  protected var controller: ModuleLayer.Controller = _

  // Template-Methode: Definiert den Ablauf des Spiels
  final def run(): Unit = {
    println("Willkommen bei 'Hilf Robert'!")
    val availableLevels = controller.getAvailableLevels

    if (availableLevels.nonEmpty) {
      showAvailableLevels(availableLevels)
      val level = getLevelFromInput()
      level match {
        case Right(foundLevel) =>
          setupGame(foundLevel) // Setup des Spiels (abstrakte Methode)
          startLevel(foundLevel) // Level-Logik starten (abstrakte Methode)
        case Left(errorMessage) =>
          println(errorMessage)
      }
    } else {
      println("Keine verfügbaren Levels gefunden. Bitte überprüfe die Leveldatei.")
    }
  }

  // Abstrakte Methoden für spezifische Schritte
  protected def showAvailableLevels(levels: List[String]): Unit
  protected def getLevelFromInput(): Either[String, LevelConfig]
  protected def setupGame(level: LevelConfig): Unit
  protected def startLevel(level: LevelConfig): Unit

  // Observer-Methode
  override def update(): Unit = {
    println("Aktualisierung vom Controller erhalten.")
  }
}

// Konkrete TUI-Implementierung
class TUI private() extends TUITemplate {

  // Überschreibe die Methode zum Anzeigen verfügbarer Levels
  override def showAvailableLevels(levels: List[String]): Unit = {
    println("Verfügbare Levels:")
    levels.foreach(level => println(s"- $level"))
    println("Bitte gib ein Level an, mit dem du starten möchtest:")
  }

  // Überschreibe die Methode zur Eingabe des Levels
  override def getLevelFromInput(): Either[String, LevelConfig] = {
    val scanner = new java.util.Scanner(System.in)
    val input = scanner.nextLine().trim
    createLevel(input)
  }

  // Überschreibe die Methode zum Setup des Spiels
  override def setupGame(level: LevelConfig): Unit = {
    initializePlayer(level)
    remainingJerms = level.objects.jerm
    displayGrid(level)
  }

  // Überschreibe die Methode zum Starten des Levels
  override def startLevel(level: LevelConfig): Unit = {
    val scanner = new java.util.Scanner(System.in)
    var action: String = ""

    println("Gib die Methoden ein, um Robert zu bewegen " +
      "(moveForward(), turnRight(), turnLeft(), q zum Beenden):")

    do {
      action = scanner.nextLine().trim
      if (action == "moveForward()" || action == "turnRight()" || action == "turnLeft()") {
        movePlayer(action, level)
        if (isLevelComplete(level)) {
          println("Herzlichen Glückwunsch! Robert ist angekommen!")
          displayGrid(level)
          run() // Neustart des Spiels mit verfügbaren Levels
        } else {
          displayGrid(level)
        }
      } else if (action != "q") {
        println("Unbekannter Befehl. Bitte versuche es erneut.")
      }
    } while (action != "q")

    println("Spiel beendet.")
  }

  // Hilfsmethoden für das konkrete Setup des Spiels
  private def initializePlayer(level: LevelConfig): Unit = {
    Spieler.initializePlayer(level.start.x, level.start.y)
  }

  private def displayGrid(level: LevelConfig): Unit = {
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
    val playerInstance = Spieler.getInstance
    grid(playerInstance.posY)(playerInstance.posX) = 'P'

    // Print the grid
    println("Spielfeld:")
    println("+" + ("---+" * level.width))
    for (row <- grid) {
      println("| " + row.mkString(" | ") + " |")
      println("+" + ("---+" * level.width))
    }
  }

  private def movePlayer(action: String, level: LevelConfig): Unit = {
    Spieler.move(action, level) // Aufruf der move-Methode in der Singleton-Spieler-Instanz
    val playerInstance = Spieler.getInstance
    remainingJerms = remainingJerms.filterNot(jerm => playerInstance.eingesammelteJerms.contains(jerm))
  }

  private def isLevelComplete(level: LevelConfig): Boolean = {
    val playerInstance = Spieler.getInstance
    playerInstance.eingesammelteJerms.size == level.objects.jerm.size && playerInstance.posX == level.goal.x && playerInstance.posY == level.goal.y
  }

  // Methode zur direkten Level-Erstellung ohne Factory
  private def createLevel(input: String): Either[String, LevelConfig] = {
    input match {
      case "Level1" =>
        Right(LevelConfig(
          level = 1,
          description = "Dies ist Level 1",
          width = 5,
          height = 5,
          start = Coordinate(0, 0),
          goal = Coordinate(4, 4),
          objects = new LevelObjects(
            obstacles = List(Coordinate(2, 2), Coordinate(3, 3)),
            jerm = List(Coordinate(1, 1), Coordinate(2, 3)))))
      case "Level2" =>
        Right(LevelConfig(level = 2, description = "Dies ist Level 2", width = 6, height = 6, start = Coordinate(0, 0), goal = Coordinate(5, 5), objects = new LevelObjects(
          obstacles = List(Coordinate(2, 2), Coordinate(4, 4)),
          jerm = List(Coordinate(1, 3), Coordinate(3, 2))
        )))
      case _ =>
        Left("Unbekanntes Level. Bitte Level1 oder Level2 eingeben.")
    }
  }
}

// Companion-Object, das die Singleton-Instanz verwaltet
object TUI {
  private val instance: TUI = new TUI()

  def getInstance(controller: Controller): TUI = {
    instance.controller = controller
    instance
  }
}
