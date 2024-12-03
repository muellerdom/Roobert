package Model

/**
 * Klasse um Level aus dem JSON File zu laden
 */

import Util.Observable
import io.circe.parser.decode
import io.circe.generic.auto._

case class Coordinate(x: Int, y: Int)

case class Obstacle(`type`: String, coordinates: Coordinate)

case class Goal(x: Int, y: Int)

case class Objects(obstacles: List[Obstacle], jerm: List[Coordinate])

case class LevelConfig(level: String, description: String, instruction: String, width: Int, height: Int, start: Coordinate, goal: Goal, objects: Objects)

case class Levels(levels: List[LevelConfig])

object loadLeveFromJSON extends Observable {

  private val levelFilePath = "src/main/resources/levels.json"

  private val levels: Option[Levels] = loadJsonFromFile(levelFilePath) match {
    case Right(parsedLevels) => Some(parsedLevels)
    case Left(error) =>
      println(s"Fehler beim Laden der Levels: $error")
      None
  }

  def getLevels: Option[Levels] = levels

  def loadJsonFromFile(filePath: String): Either[String, Levels] = {
    val source = scala.io.Source.fromFile(filePath)
    val jsonString = try source.mkString finally source.close()
    decode[Levels](jsonString)match {
      case Right(parsedLevels) => Right(parsedLevels)
      case Left(error) => Left(s"Fehler beim Parsen des JSON: $error")
    }
  }
}
