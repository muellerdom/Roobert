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

class Controller extends Observable {

  private var levels: Option[Levels] = None
  private val levelFilePath = "src/main/resources/levels.json"
  private var currentLevel: Option[LevelConfig] = None

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

  def validateLevels(levels: List[LevelConfig]): Either[String, List[LevelConfig]] = {
    val invalidCoordinates = levels.flatMap { level =>
      val goalErrors = if (level.goal.x < 0 || level.goal.x >= level.width || level.goal.y < 0 || level.goal.y >= level.height) {
        Some(s"Goal coordinates (${level.goal.x}, ${level.goal.y}) in level ${level.level} are out of bounds")
      } else {
        None
      }

      val obstacleErrors = level.objects.obstacles.collect {
        case obstacle if obstacle.coordinates.x < 0 || obstacle.coordinates.x >= level.width ||
          obstacle.coordinates.y < 0 || obstacle.coordinates.y >= level.height =>
          s"Obstacle '${obstacle.`type`}' at coordinates (${obstacle.coordinates.x}, ${obstacle.coordinates.y}) in level ${level.level} is out of bounds"
      }

      val jermErrors = level.objects.jerm.collect {
        case jerm if jerm.x < 0 || jerm.x >= level.width || jerm.y < 0 || jerm.y >= level.height =>
          s"Jerm at coordinates (${jerm.x}, ${jerm.y}) in level ${level.level} is out of bounds"
      }

      goalErrors.toList ++ obstacleErrors ++ jermErrors
    }

    if (invalidCoordinates.isEmpty) {
      Right(levels)
    } else {
      Left(invalidCoordinates.mkString(", "))
    }
  }

  def processLevels: Either[String, List[LevelConfig]] = {
    levels match {
      case Some(lvl) => validateLevels(lvl.levels)
      case None => Left("Keine Leveldaten geladen.")
    }
  }

  def startLevel(level: String): Either[String, LevelConfig] = {
    levels match {
      case Some(lvl) =>
        lvl.levels.find(_.level == level) match {
          case Some(foundLevel) =>
            currentLevel = Some(foundLevel)
            Right(foundLevel)
          case None => Left(s"Level '$level' nicht gefunden.")
        }
      case None => Left("Bitte laden Sie zuerst die Leveldaten.")
    }
  }

  def getAvailableLevels: List[String] = {
    levels.map(_.levels.map(_.level)).getOrElse(List())
  }

  def getCurrentLevelConfig: Option[LevelConfig] = currentLevel
}