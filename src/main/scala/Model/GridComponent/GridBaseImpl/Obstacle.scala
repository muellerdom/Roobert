package Model.GridComponent.GridBaseImpl

import Model.GridComponent.{Coordinate, GridSegmentsInterface}
import Model.LevelComponent.levelManager

class Obstacle(val position: Coordinate, val name: String) extends GridSegmentsInterface {
  override def getPosition: Coordinate = position

  override def Symbol: Char = 'X' // Symbol für Hindernis

  override def isBlocking: Boolean = true

  override def interactWithPlayer(): Unit = {
    println("Der Spieler kann das Hindernis nicht passieren.")
  }

  override def segmentType: String = "obstacle"

  override def subType: Option[String] = Some(name)
}
