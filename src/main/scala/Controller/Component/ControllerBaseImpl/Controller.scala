package Controller.Component.ControllerBaseImpl


import Controller.Component.ControllerInterface
import Model.LevelComponent.{LevelConfig, levelManager}
import Model.REPLComponent.REPLBaseImpl.REPL
import Model.SpielerComponent.PlayerBaseImpl.Spieler
import Model.SpielfeldComponent.SpielfeldBaseImpl.Spielfeld
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}
import Util.{Observable, UndoManager}


class Controller extends Observable with ControllerInterface{

  REPL.replBind(this)


  def startLevel(levelName: String): Either[String, LevelConfig] = {
    levelManager.ladeLevel(levelName) match {
      case Right(level) =>
        Spieler.initialize()
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
    println(Spieler.inventory.size)
    Spieler.inventory.size == currentLevel.objects.jerm.size &&
      Spieler.position.get.isEqualTo(Coordinate(currentLevel.goal.x, currentLevel.goal.y))
  }

  def getSpielfeld: SpielfeldInterface = Spielfeld

  def getLevelConfig: Option[LevelConfig] = levelManager.getCurrentLevel
}