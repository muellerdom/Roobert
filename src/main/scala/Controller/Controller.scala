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

class Controller private() extends Observable {

  private var levels: Option[Levels] = None
  private val levelFilePath = "src/main/resources/levels.json"
  private var currentLevel: Option[LevelConfig] = None

  // Lädst die Levels aus einer JSON-Datei
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

object Controller {
  // Die Singleton-Instanz
  private var instance: Option[Controller] = None

  // Die Methode, um die Instanz zu holen (wird nur eine Instanz zugelassen)
  def getInstance: Controller = {
    if (instance.isEmpty) {
      instance = Some(new Controller())
    }
    instance.get
  }
}
