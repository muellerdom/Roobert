package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.SpielfeldComponent.{Coordinate, KomponentenInterface}


class Ziel(val position: Coordinate) extends KomponentenInterface {
  override def getPosition: Coordinate = position

  override def Symbol: Char = 'G' // Symbol für das Ziel

  override def isBlocking: Boolean = false

  override def interactWithPlayer(): Unit = {
    if (Player.getPosition == position) {
      println("Herzlichen Glückwunsch! Du hast das Ziel erreicht!")
    }
  }
}

