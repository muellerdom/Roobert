package Controller

import Model._
import Util.Observable


class Controller extends Observable {

  //private var player: Option[Spieler] = None

  //private val replInstance = repl.getOrElse(new REPL(this))
  //private val repl = new REPL(this)


  /**
   * Startet das angegebene Level.
   */
  def startLevel(levelName: String): Either[String, LevelConfig] = {
    levelManager.ladeLevel(levelName) match {
      case Right(level) =>
        initializePlayer(level)
        initializeSpielfeld(level)
        Right(level)
      case Left(error) => Left(error)
    }
  }

  def getAvailableLevels: List[String] = {
    levelManager.getAvailableLevels
  }

  private def initializeSpielfeld(level: LevelConfig): Unit = {
    val grid = Array.fill(level.width, level.height)(' ')
    Spielfeld.initialize(grid)

    Spielfeld.hinsetze(level.goal.x, level.goal.y, 'G')

    level.objects.obstacles.foreach { obstacle =>
      Spielfeld.hinsetze(obstacle.coordinates.x, obstacle.coordinates.y, 'X')
    }

    level.objects.jerm.foreach { jerm =>
      Spielfeld.hinsetze(jerm.coordinates.x, jerm.coordinates.y, 'J')
    }

    Spielfeld.hinsetze(Spieler.getPosition.x, Spieler.getPosition.y, 'R')
  }

  private def initializePlayer(level: LevelConfig): Unit = {
    Spieler.initialize(level.start.x, level.start.y)
  }

  def movePlayer(action: String): Unit = {
    //player.foreach { p =>
    if(Spieler.getPosition.x == levelManager.getCurrentLevel.get.goal.x && Spieler.getPosition.y == levelManager.getCurrentLevel.get.goal.y) {
      Spielfeld.hinsetze(Spieler.getPosition.x, Spieler.getPosition.y, 'G')
    } else {
      Spielfeld.hinsetze(Spieler.getPosition.x, Spieler.getPosition.y, ' ')
    }
    Spieler.move(action) // Assuming safe get, make sure this is safe
      Spielfeld.hinsetze(Spieler.getPosition.x, Spieler.getPosition.y, 'R')

  }

  def moveForward() = movePlayer("forward")

  def turnRight() = movePlayer("right")

  def turnLeft() = movePlayer("left")


  def isLevelComplete: Boolean = {
    //player.exists { p =>
      val currentLevel = levelManager.getCurrentLevel.get
      Spieler.eingesammelteJerms.size == currentLevel.objects.jerm.size &&
        Spieler.getPosition.x == currentLevel.goal.x &&
        Spieler.getPosition.y == currentLevel.goal.y
    //}
  }

  def getGrid(x: Int, y: Int): Char = {
    Spielfeld.get(x, y)
  }

  def getLevelConfig: Option[LevelConfig] = levelManager.getCurrentLevel

  def repl(code: String) : Unit = REPL.Interpret(code)
}
