package Controller

import Util.Observable
import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import java.io._

case class Coordinate(x: Int, y: Int)
case class Obstacle(`type`: String, coordinates: Coordinate)
case class Goal(x: Int, y: Int)
case class Objects(obstacles: List[Obstacle], jerm: List[Coordinate])
case class LevelConfig(level: String, description: String, instruction: String, width: Int, height: Int, start: Coordinate, goal: Goal, objects: Objects)
case class Levels(levels: List[LevelConfig])

/**
 * Hauptcontroller der Anwendung
 * Der Controller wird als Singleton implementiert.
 */
class Controller private(levelFilePath: String) extends Observable {

  private var levels: Option[Levels] = None
  private var currentLevel: Option[LevelConfig] = None

  // Lädt die Levels aus einer JSON-Datei
  loadJsonFromFile(levelFilePath) match {
    case Right(parsedLevels) => levels = Some(parsedLevels)
    case Left(error) => println(s"Fehler beim Laden der Levels: $error")
  }

  def loadJsonFromFile(filePath: String): Either[String, Levels] = {
    val source = scala.io.Source.fromFile(filePath)
    val jsonString = try source.mkString finally source.close()
    decode[Levels](jsonString) match {
      case Right(parsedLevels) => Right(parsedLevels)
      case Left(error) => Left(s"Fehler beim Parsen des JSON: $error")
    }
  }

  // Startet das Level und informiert die View
  def startLevel(level: String): Either[String, LevelConfig] = {
    levels match {
      case Some(lvl) =>
        lvl.levels.find(_.level == level) match {
          case Some(foundLevel) =>
            currentLevel = Some(foundLevel)
            notifyObservers()  // Benachrichtige die View, dass ein neues Level gestartet wurde
            Right(foundLevel)
          case None => Left(s"Level '$level' nicht gefunden.")
        }
      case None => Left("Bitte laden Sie zuerst die Leveldaten.")
    }
  }

  // Holt alle verfügbaren Levels
  def getAvailableLevels: List[String] = {
    levels.map(_.levels.map(_.level)).getOrElse(List())
  }

  def getCurrentLevelConfig: Option[LevelConfig] = currentLevel
}

/**
 * Companion-Objekt für das Singleton-Muster des Controllers
 */
object Controller {

  // Die einzige Instanz des Controllers (Singleton)
  private var instance: Option[Controller] = None

  // Zugriff auf die Singleton-Instanz des Controllers
  def getInstance(levelFilePath: String): Controller = {
    instance match {
      case Some(ctrl) => ctrl  // Wenn bereits eine Instanz existiert, wird diese zurückgegeben
      case None =>
        val newInstance = new Controller(levelFilePath)  // Eine neue Instanz wird erstellt
        instance = Some(newInstance)
        newInstance
    }
  }

  // Factory-Methode für die Erstellung von Controllern
  def createController(levelFilePath: String): Controller = {
    // Wenn du sicherstellen willst, dass immer nur eine Instanz erstellt wird, könnte diese Methode ebenfalls das Singleton verwenden.
    getInstance(levelFilePath) // Gibt die Singleton-Instanz zurück.
  }
}

/**
 * Beispiel für die Verwendung des Singleton- und Factory Patterns
 */
object MainApp {
  def main(args: Array[String]): Unit = {
    // Controller über das Singleton-Muster erstellen
    val levelFilePath = "src/main/resources/levels.json"
    val controller = Controller.getInstance(levelFilePath)

    // Beispielaktionen
    println(s"Verfügbare Levels: ${controller.getAvailableLevels.mkString(", ")}")
    controller.startLevel("level1") match {
      case Right(levelConfig) => println(s"Level gestartet: ${levelConfig.description}")
      case Left(error) => println(s"Fehler: $error")
    }

    // Alternativ: Controller über die Factory-Methode erstellen
    val anotherController = Controller.createController(levelFilePath)
    println(s"Verfügbare Levels: ${anotherController.getAvailableLevels.mkString(", ")}")
  }
}
