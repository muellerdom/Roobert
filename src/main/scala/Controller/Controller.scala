package Controller

import Model.{Coordinate, LevelConfig, Spieler, Spielfeld, levelManager}
import Util.Observable

class Controller extends Observable {

  private var player: Option[Spieler] = None

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
      Spielfeld.hinsetze(jerm.x, jerm.y, 'J')
    }

    player.foreach(p => Spielfeld.hinsetze(p.posX, p.posY, 'R'))
  }

  private def initializePlayer(level: LevelConfig): Unit = {
    player = Some(new Spieler(level.start.x, level.start.y, level.width, level.height))
  }

  def movePlayer(action: String): Unit = {
    player.foreach { p =>
      Spielfeld.hinsetze(p.posX, p.posY, ' ')
      p.move(action) // Assuming safe get, make sure this is safe
      Spielfeld.hinsetze(p.posX, p.posY, 'R')
    }
  }

  def moveForward() = movePlayer("forward")

  def turnRight() = movePlayer("right")

  def turnLeft() = movePlayer("left")


  def isLevelComplete: Boolean = {
    player.exists { p =>
      val currentLevel = levelManager.getCurrentLevel.get
      p.eingesammelteJerms.size == currentLevel.objects.jerm.size &&
        p.posX == currentLevel.goal.x &&
        p.posY == currentLevel.goal.y
    }
  }

  def getGrid(x: Int, y: Int): Char = {
    Spielfeld.get(x, y)
  }

  def getLevelConfig: Option[LevelConfig] = levelManager.getCurrentLevel
}
