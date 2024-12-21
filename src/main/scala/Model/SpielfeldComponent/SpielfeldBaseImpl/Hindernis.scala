package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.SpielfeldComponent.{Coordinate, KomponentenInterface}

class Hindernis(val position: Coordinate) extends KomponentenInterface {
  override def getPosition: Coordinate = position

  override def Symbol: Char = 'X' // Symbol für Hindernis

  override def isBlocking: Boolean = true

  override def interactWithPlayer(): Unit = {
    println("Der Spieler kann das Hindernis nicht passieren.")
  }
}
