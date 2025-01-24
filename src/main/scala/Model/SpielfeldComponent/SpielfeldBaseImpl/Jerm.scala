/*package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.SpielfeldComponent.{Coordinate, KomponentenInterface}

class Jerm(val position: Coordinate) extends KomponentenInterface {
  override def getPosition: Coordinate = position

  override def Symbol: Char = 'J' // Symbol für Jerm

  override def isBlocking: Boolean = false

  override def interactWithPlayer(): Unit = {
    //Player.einsammeln(position) // Spieler sammelt Jerm ein
    println(s"Jerm an ${position} wurde eingesammelt!")
  }
}
*/