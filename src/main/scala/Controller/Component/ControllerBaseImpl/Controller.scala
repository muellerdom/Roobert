// Controller.scala
package Controller.Component.ControllerBaseImpl

import Controller.Component.ControllerInterface
import Model.LevelComponent.{LevelConfig, LevelManagerTrait}
import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.SpielfeldComponent.SpielfeldBaseImpl.Spielfeld
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}
import Util.{Observable, UndoManager}
import com.google.inject.Inject

class Controller @Inject() (levelManager: LevelManagerTrait) extends Observable with ControllerInterface {

  private val undoManager = new UndoManager
  private var invalidMove = false
  private var jermCollected = false
  private var currentLevel: Option[String] = None

  def startLevel(levelName: String): Either[String, LevelConfig] = {
    levelManager.loadLevel(levelName) match {
      case Right(level) =>
        Player.initialize()
        currentLevel = Some(levelName)
        notifyObservers()
        Right(level)
      case Left(error) => Left(error)
    }
  }

  def getAvailableLevels: List[String] = levelManager.getAvailableLevels

  def setCommand(action: String): Unit = {
    action.toLowerCase match {
      case "moveup()" => moveUp()
      case "movedown()" => moveDown()
      case "moveleft()" => moveLeft()
      case "moveright()" => moveRight()
      case _ => println(s"Unknown command: $action")
    }
  }

  def setLevel(level: String): Unit = {
    startLevel(level)
  }

  def moveUp(): Unit = {
    Player.moveUp()
    checkInvalidMove()
    checkJermCollected()
    notifyObservers()
  }

  def moveDown(): Unit = {
    Player.moveDown()
    checkInvalidMove()
    checkJermCollected()
    notifyObservers()
  }

  def moveLeft(): Unit = {
    Player.moveLeft()
    checkInvalidMove()
    checkJermCollected()
    notifyObservers()
  }

  def moveRight(): Unit = {
    Player.moveRight()
    checkInvalidMove()
    checkJermCollected()
    notifyObservers()
  }

  private var levelConfig: Option[LevelConfig] = None

  def setLevelConfig(level: LevelConfig): Unit = {
    levelConfig = Some(level)
    notifyObservers() // Notify observers when the level is changed
  }

  def undo(): Unit = {
    undoManager.undoStep
    notifyObservers()
  }

  def redo(): Unit = {
    undoManager.redoStep
    notifyObservers()
  }

  def isLevelComplete: Boolean = {
    val currentLevel = levelManager.getCurrentLevel.get
    Player.inventory.size == currentLevel.objects.jerm.size &&
      Player.position.get.isEqualTo(Coordinate(currentLevel.goal.x, currentLevel.goal.y))
  }

  def getSpielfeld: SpielfeldInterface = Spielfeld

  def getLevelConfig: Option[LevelConfig] = levelManager.getCurrentLevel

  def isInvalidMove: Boolean = invalidMove
  def isJermCollected: Boolean = jermCollected

  private def checkInvalidMove(): Unit = {
    invalidMove = !Player.isValidMove(Player.getPosition)
  }

  private def checkJermCollected(): Unit = {
    jermCollected = Player.inventory.size > 0
  }
}