package Model.GridComponent.GridBaseImpl

import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.GridComponent.{Coordinate, GridSegmentsInterface}

class Jerm(val position: Coordinate) extends GridSegmentsInterface {
  override def getPosition: Coordinate = position

  override def Symbol: Char = 'J' // Symbol für Jerm

  override def isBlocking: Boolean = false

  override def interactWithPlayer(): Unit = {
    //Player.collectJerm(position) // Spieler sammelt Jerm ein
    println(s"Jerm an $position wurde eingesammelt!")
  }

  override def segmentType: String = "jerm"

  override def subType: Option[String] = None
}
