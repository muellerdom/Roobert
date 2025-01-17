package Controller.Component.ControllerBaseImpl

import Controller.Component.ControllerInterface
import Model.LevelComponent.{LevelConfig, LevelManagerTrait}
import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.SpielfeldComponent.SpielfeldBaseImpl.Spielfeld
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}
import Util.{Observable, UndoManager}
import com.google.inject.Inject
import scala.util.Random

class Controller @Inject() (levelManager: LevelManagerTrait) extends Observable with ControllerInterface {

  private val undoManager = new UndoManager
  private var invalidMove = false
  private var jermCollected = false
  private var currentLevelName: String = ""

  def startLevel(levelName: String): Either[String, LevelConfig] = {
    currentLevelName = levelName
    levelManager.loadLevel(levelName) match {
      case Right(level) =>
        Player.initialize()
        notifyObservers()
        Right(level)
      case Left(error) => Left(error)
    }
  }

  def restartLevel(): Unit = {
    randomizeLevel()
    startLevel(currentLevelName)
  }

  def nextLevel(): Unit = {
    val availableLevels = getAvailableLevels
    val currentIndex = availableLevels.indexOf(currentLevelName)
    if (currentIndex >= 0 && currentIndex < availableLevels.size - 1) {
      startLevel(availableLevels(currentIndex + 1))
    } else {
      println("No more levels available.")
    }
  }

  def getAvailableLevels: List[String] = levelManager.getAvailableLevels

  def setCommand(action: String): Unit = {
    val command = new SetCommand(action, this)
    undoManager.doStep(command)
    notifyObservers()
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

  private def randomizeLevel(): Unit = {
    val currentLevel = levelManager.getCurrentLevel.get
    val width = currentLevel.width
    val height = currentLevel.height

    // Randomize player position
    val playerX = Random.nextInt(width)
    val playerY = Random.nextInt(height)
    Player.setPosition(Coordinate(playerX, playerY))

    // Randomize obstacles
    currentLevel.objects.obstacles.foreach { obstacle =>
      val obstacleX = Random.nextInt(width)
      val obstacleY = Random.nextInt(height)
      obstacle.setPosition(Coordinate(obstacleX, obstacleY))
    }

    // Randomize Jerm positions
    currentLevel.objects.jerm.foreach { jerm =>
      val jermX = Random.nextInt(width)
      val jermY = Random.nextInt(height)
      jerm.setPosition(Coordinate(jermX, jermY))
    }
  }
}