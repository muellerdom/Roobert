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

  def startLevel(levelName: String): Either[String, LevelConfig] = {
    levelManager.loadLevel(levelName) match {
      case Right(level) =>
        Player.initialize()
        notifyObservers()
        Right(level)
      case Left(error) => Left(error)
    }
  }

  def getAvailableLevels: List[String] = levelManager.getAvailableLevels

  private val undoManager = new UndoManager

  def setCommand(action: String): Unit = {
    val command = new SetCommand(action, this)
    undoManager.doStep(command)
    notifyObservers()
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
}