package Model.GridComponent.GridBaseImpl

import Model.FileIOComponent.FileIoJsonImpl.Logic
import Model.GridComponent.{Coordinate, GridInterface, GridSegmentsInterface}
import Model.LevelComponent.levelManager.getCurrentLevel
import Model.PlayerComponent.PlayerBaseImpl.Player
import Util.Observable

/**
 * Grid repräsentiert das Spielfeld basierend auf dem aktuellen Level.
 * Es verwaltet die Komponenten des Spielfelds (wie Hindernisse, Jerms, Ziel) und die Position des Spielers.
 */


case class Grid(gridSegments: GridSegments) extends GridInterface {

  def displayGrid(): Unit = {
    val level = getCurrentLevel.get.logic

    println("Spielfeld:")
    println("+" + ("---+" * level.gridSize.get.width))
    for (y <- level.gridSize.get.height - 1 to 0 by -1) {
      for (x <- 0 until level.gridSize.get.width) {
        val symbol = gridSegments.findByPosition(Coordinate(x, y)).get.Symbol
        print(s"| $symbol ")
      }
      println("|")
      println("+" + ("---+" * level.gridSize.get.width))

    }
  }


  /**
   * Bewegt den Spieler im Raster basierend auf einer Bewegung (e.g., "forward", "left").
   * Die tatsächliche Logik der Bewegung liegt im Spielerobjekt.
   *
   * @param direction String, der die Bewegung beschreibt (z. B. "forward", "right")
   * @return Eine neue Grid-Instanz mit aktualisierter Spielerposition.
   */
  def movePlayer(direction: String): Grid = {
    val currentPlayerOpt = getPlayer
    currentPlayerOpt.map { player =>
      // Spieler bewegen
      val updatedPlayer = player.move(direction)

      // Prüfen, ob die neue Position gültig ist
      val targetPos = updatedPlayer.position
      val currentSegments = gridSegments.segments
      if (currentSegments.exists(_.getPosition == targetPos)) {
        val updatedSegments = gridSegments.update(player, updatedPlayer)
        new Grid(updatedSegments)
      } else {
        throw new IllegalArgumentException(s"Ungültige Bewegung auf $targetPos!")
      }
    }.getOrElse(throw new NoSuchElementException("Kein Spieler im Grid gefunden!"))
  }

  override def getPlayerPosition: Option[Coordinate] = getPlayer.map(_.getPosition)

  def getPlayer: Option[Player] = {
    gridSegments.segments.collectFirst {
      case p: Player => p
    }
  }

  override def remove(coordinate: Coordinate): Unit = {}

  override def getAnPos(coordinate: Coordinate): Char = ???

  override def getGrid: Option[GridSegments] = Some(gridSegments)

  override def setPlayerPosition(pos: Coordinate): Unit = {

  }
}
