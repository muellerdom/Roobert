package Model.FileIOComponent.FileIoXmlImpl

import Model.FileIOComponent.FileIOInterface
import Model.GridComponent.{Coordinate, GridInterface, GridSegmentsInterface}
import Model.GridComponent.GridBaseImpl.{Goal, Grid, GridSegments, Obstacle, emptyField}
import Model.LevelComponent.levelManager
import Model.PlayerComponent.PlayerBaseImpl.Player

import scala.xml.{Node, PrettyPrinter, XML}

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

  override def save(grid: GridInterface): Unit = {
    val gridXml = gridToXml(grid)
    val printer = new PrettyPrinter(80, 2) // Für besser lesbares XML
    val formattedXml = printer.format(gridXml)
    val pw = new java.io.PrintWriter("src/main/resources/gameState.xml")
    pw.write(formattedXml)
    pw.close()
  }

  override def load: Unit = {
    // Lese die Levels aus der definierten XML-Datei
    loadXmlFromFile(levelFilePath)
  }

  def gridToXml(grid: GridInterface): Node = {
    <levels>
      <level>
        <id>{levelManager.getCurrentLevel.get.id}</id>
        <title>{levelManager.getCurrentLevel.get.title}</title>
        <instructions>
          {levelManager.getCurrentLevel.get.instructions.map(instr => <instruction>{instr}</instruction>)}
        </instructions>
        <logic>
          <type>{levelManager.getCurrentLevel.get.logic.`type`}</type>
          <gridSize>
            <width>{levelManager.getCurrentLevel.get.logic.gridSize.get.width}</width>
            <height>{levelManager.getCurrentLevel.get.logic.gridSize.get.height}</height>
          </gridSize>
          <entities>
            {
            grid.getGrid.map(_.segments
              .filterNot(_.isInstanceOf[emptyField]) // `emptyField` ausschließen
              .map { segment =>
                <entity>
                  <type>{segment.segmentType}</type>
                  {segment.subType.map(st => <subtype>{st}</subtype>).getOrElse(scala.xml.Null)}
                  <position>
                    <x>{segment.getPosition.x}</x>
                    <y>{segment.getPosition.y}</y>
                  </position>
                </entity>
              }
            ).getOrElse(List.empty[Node])
            }
          </entities>
        </logic>
      </level>
    </levels>
  }

  def loadXmlFromFile(filePath: String): Either[String, List[LevelConfig]] = {
    try {
      val xml = XML.loadFile(filePath)
      val levels = (xml \\ "level").map { levelNode =>
        val id = (levelNode \ "id").text.toInt
        val title = (levelNode \ "title").text
        val instructions = (levelNode \ "instructions" \ "instruction").map(_.text).toList
        val logicNode = levelNode \ "logic"
        val gridType = (logicNode \ "type").text
        val gridSizeNode = logicNode \ "gridSize"

        val gridSize = if (gridSizeNode.nonEmpty) {
          Some(GridSize(
            (gridSizeNode \ "width").text.toInt,
            (gridSizeNode \ "height").text.toInt
          ))
        } else None

        val entities = (logicNode \ "entities" \ "entity").map { entityNode =>
          val entityType = (entityNode \ "type").text
          val positionNode = entityNode \ "position"
          val position = Coordinate(
            (positionNode \ "x").text.toInt,
            (positionNode \ "y").text.toInt
          )
          val subtype = (entityNode \ "subtype").headOption.map(_.text)
          Entity(entityType, position, subtype)
        }.toList

        val logic = Logic(gridType, gridSize, None, entities) // Setze das Wort für Hangman auf None
        LevelConfig(id, title, instructions, logic)
      }.toList
      Right(levels)
    } catch {
      case e: Exception => Left(s"Fehler beim Laden der XML-Datei: ${e.getMessage}")
    }
  }

  private val levels: Option[Levels] = loadXmlFromFile(levelFilePath) match {
    case Right(parsedLevels) => Some(Levels(parsedLevels))
    case Left(error) =>
      println(s"Fehler beim Laden der Levels: $error")
      None
  }

  def getLevels: Option[Levels] = levels
}