/*package Controller.Component.ControllerMockImpl

import Controller.Component.ControllerInterface
import Model.LevelComponent.{Goal, LevelConfig, Objects}
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}

class MockController extends ControllerInterface {

  var levels: List[String] = List("Level1", "Level2", "Level3")
  var currentLevel: Option[LevelConfig] = None
  var commands: List[String] = List()
  var inventorySize: Int = 0
  var playerPosition: Coordinate = Coordinate(0, 0)
  var goalPosition: Coordinate = Coordinate(5, 5)
  val goal : Goal = Goal(5, 5)
  val objects : Objects = Objects(List(), List())

  override def startLevel(levelName: String): Either[String, LevelConfig] = {
    if (levels.contains(levelName)) {
      currentLevel = Some(LevelConfig(levelName, "Test Level", "Test Level", 10, 10, Coordinate(0, 0), goal, objects))
      Right(currentLevel.get)
    } else {
      Left("Level nicht gefunden.")
    }
  }

  override def getAvailableLevels: List[String] = levels

  override def setCommand(action: String): Unit = {
    commands = commands :+ action
  }

  override def undo(): Unit = {
    if (commands.nonEmpty) commands = commands.init
  }

  override def redo(): Unit = {
    // Mock-Logik für Redo (optional)
  }

  override def isLevelComplete: Boolean = {
    inventorySize == 3 && playerPosition == goalPosition
  }

  override def getSpielfeld: SpielfeldInterface = new SpielfeldInterface {
    // Mock-Spielfeld
    override def entfernen(x: Int, y: Int): Unit = {}
    override def getAnPos(x: Int, y: Int): Char = ' '
    override def getSpielfeld: Array[Array[Char]] = Array.ofDim[Char](10, 10)
    override def getSpielerPosition: Option[Coordinate] = Some(playerPosition)
    override def setSpielerPosition(pos: Coordinate): Unit = {
      playerPosition = pos
    }
  }

  override def getLevelConfig: Option[LevelConfig] = currentLevel
}
*/