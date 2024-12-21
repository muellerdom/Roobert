package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.SpielerComponent.PlayerBaseImpl.Spieler
import Model.SpielfeldComponent.{Coordinate, KomponentenInterface}


class Ziel(val position: Coordinate) extends KomponentenInterface {
  override def getPosition: Coordinate = position

  override def Symbol: Char = 'G' // Symbol für das Ziel

  override def isBlocking: Boolean = false

  override def interactWithPlayer(): Unit = {
    if (Spieler.getPosition == position) {
      println("Herzlichen Glückwunsch! Du hast das Ziel erreicht!")
    }
  }
}

