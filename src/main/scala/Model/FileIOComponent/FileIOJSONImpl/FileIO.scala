package Model.FileIOComponent.FileIoJsonImpl

import Model.FileIOComponent.FileIOInterface
import Model.GridComponent.{Coordinate, GridInterface, GridSegmentsInterface}
import Model.GridComponent.GridBaseImpl.{Goal, Grid, GridSegments, Obstacle, emptyField}
import Model.LevelComponent.levelManager
import Model.PlayerComponent.PlayerBaseImpl.Player
import io.circe.{Encoder, Json}
import io.circe.generic.auto._
import io.circe.generic.semiauto.deriveEncoder
import io.circe.parser.decode
import io.circe.syntax.EncoderOps

import java.io.{File, PrintWriter}

case class Entity(`type`: String, position: Coordinate, subtype: Option[String] = None)

case class Logic(`type`: String, gridSize: Option[GridSize] = None, word: Option[String] = None, entities: List[Entity] = List())

case class GridSize(width: Int, height: Int)

case class LevelConfig(
                        id: Int,
                        title: String,
                        instructions: List[String],
                        logic: Logic
                      )

case class Levels(levels: List[LevelConfig])

class FileIO(val levelFilePath: String) extends FileIOInterface {

  // Encoder für GridSegmentsInterface und seine konkreten Implementierungen
  implicit val gridSegmentsInterfaceEncoder: Encoder[GridSegmentsInterface] = Encoder.instance {
    case player: Player => player.asJson
    case goal: Goal => goal.asJson
    case obstacle: Obstacle => obstacle.asJson
    case empty: emptyField => empty.asJson
  }

  // Encoder für die konkreten Implementierungen
  implicit val playerEncoder: Encoder[Player] = deriveEncoder[Player]
  implicit val goalEncoder: Encoder[Goal] = deriveEncoder[Goal]
  implicit val obstacleEncoder: Encoder[Obstacle] = deriveEncoder[Obstacle]
  implicit val emptyFieldEncoder: Encoder[emptyField] = deriveEncoder[emptyField]

  // Encoder für GridSegments
  implicit val gridSegmentsEncoder: Encoder[GridSegments] = deriveEncoder[GridSegments]

  // Encoder für Grid
  implicit val gridEncoder: Encoder[Grid] = deriveEncoder[Grid]

  // Encoder für GridInterface (leitet an das konkrete Grid weiter)
  implicit val gridInterfaceEncoder: Encoder[GridInterface] = Encoder.instance {
    case grid: Grid => grid.asJson
  }

  //implicit val logicEncoder: Encoder[Logic] = deriveEncoder

  implicit val anyEncoder: Encoder[Any] = Encoder.instance {
    case s: String => Json.fromString(s)
    case i: Int => Json.fromInt(i)
    case b: Boolean => Json.fromBoolean(b)
    case d: Double => Json.fromDoubleOrNull(d)
    case _ => Json.Null
  }

  implicit val objEncoder: Encoder[Object] = Encoder.instance {
    case xs: Map[String, Any] => xs.asJson
    case _ => Json.Null
  }

  implicit val mapEncoder: Encoder[Map[String, Any]] = Encoder.instance { map =>
    Json.fromFields(map.map {
      case (key, value: String) => key -> Json.fromString(value)
      case (key, value: Int) => key -> Json.fromInt(value)
      case (key, value: Boolean) => key -> Json.fromBoolean(value)
      case (key, value: List[_]) => key -> Json.arr(value.map {
        case v: String => Json.fromString(v)
        case v: Int => Json.fromInt(v)
        case v: Boolean => Json.fromBoolean(v)
        case v: Map[_, _] => mapEncoder.apply(v.asInstanceOf[Map[String, Any]])
        case _ => Json.Null
      }: _*)
      case (key, value: Map[_, _]) => key -> mapEncoder.apply(value.asInstanceOf[Map[String, Any]])
      case (key, null) => key -> Json.Null
      case (key, _) => key -> Json.Null // Fallback für unerwartete Typen
    })
  }


  override def save(grid: GridInterface): Unit = {
    val pw = new PrintWriter(new File("src/main/resources/gameState.json"))
    pw.write(gridToJson(grid))
    pw.close()
  }

  override def load: Unit = {
    //val source = Source.fromFile("src/main/resources/gameState.json")
    //val gridJson = try source.mkString finally source.close()
    loadJsonFromFile(levelFilePath)
  }

  // Wandelt das Grid in JSON um
  def gridToJson(grid: GridInterface): String = {

    // JSON-Objekt mit Gitterinformationen und Entitäten erstellen
    Map(
      "levels" -> List(Map(
        "id" -> levelManager.getCurrentLevel.get.id,
        "title" -> levelManager.getCurrentLevel.get.title,
        "instructions" -> levelManager.getCurrentLevel.get.instructions,
        "logic" -> Map(
          "type" -> levelManager.getCurrentLevel.get.logic.`type`,
          "gridSize" -> Map(
            "width" -> levelManager.getCurrentLevel.get.logic.gridSize.get.width,
            "height" -> levelManager.getCurrentLevel.get.logic.gridSize.get.height
          ),
          "entities" -> grid.getGrid.map(_.segments
            .filterNot(_.isInstanceOf[emptyField]) // exclude `emptyField`
            .map { segment =>
              Map(
                "type" -> segment.segmentType,
                "subtype" -> segment.subType,
                "position" -> Map(
                  "x" -> segment.getPosition.x.toString,
                  "y" -> segment.getPosition.y.toString
                )
              )
            }).getOrElse(List.empty)
        )
      ))).asJson.noSpaces
  }

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
    decode[Levels](jsonString) match {
      case Right(parsedLevels) => Right(parsedLevels)
      case Left(error) => Left(s"Fehler beim Parsen des JSON: $error")
    }
  }
}