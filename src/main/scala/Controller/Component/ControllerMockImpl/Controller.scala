package Controller.Component.ControllerMockImpl

import Model._
import Util.{Observable, UndoManager}

class Controller extends Observable {

  var spielStatus: SpielStatus = InitializationStage

  def startLevel(levelName: String): Either[String, LevelConfig] = {
    levelManager.ladeLevel(levelName) match {
      case Right(level) =>
        notifyObservers()
        initializePlayer(level)
        initializeSpielfeld(level)
        Right(level)
      case Left(error) => Left(error)
    }
  }

  def getAvailableLevels: List[String] = levelManager.getAvailableLevels

  private def initializeSpielfeld(level: LevelConfig): Unit = {
    val grid = Array.fill(level.width, level.height)(' ')
    Spielfeld.initialize(grid)

    Spielfeld.hinsetze(level.goal.x, level.goal.y, 'G')
    level.objects.obstacles.foreach(o => Spielfeld.hinsetze(o.coordinates.x, o.coordinates.y, 'X'))
    level.objects.jerm.foreach(j => Spielfeld.hinsetze(j.coordinates.x, j.coordinates.y, 'J'))
    Spielfeld.hinsetze(Spieler.getPosition.x, Spieler.getPosition.y, 'R')
  }

  private def initializePlayer(level: LevelConfig): Unit =
    Spieler.initialize(level.start.x, level.start.y)

  private val undoManager = new UndoManager

  def movePlayer(action: String): Unit = {
    val command = new SetCommand(action)
    undoManager.doStep(command)
    spielStatus = ProcessMoveStage
    notifyObservers()
  }

  def undo(): Unit = {
    undoManager.undoStep
    println(Spieler.getPosition)
    spielStatus = ProcessMoveStage
    notifyObservers()
  }

  def redo(): Unit = {
    undoManager.redoStep
    println(Spieler.getPosition)
    spielStatus = ProcessMoveStage

    notifyObservers()
  }

  def isLevelComplete: Boolean = {
    val currentLevel = levelManager.getCurrentLevel.get
    Spieler.eingesammelteJerms.size == currentLevel.objects.jerm.size &&
      Spieler.getPosition.x == currentLevel.goal.x &&
      Spieler.getPosition.y == currentLevel.goal.y
  }

  def getGrid(x: Int, y: Int): Char = Spielfeld.get(x, y)

  def getLevelConfig: Option[LevelConfig] = levelManager.getCurrentLevel

  def repl(code: String): Unit = {
    spielStatus = UserInputStage
    REPL.Interpret(code)
    //notifyObservers()
  }
}
