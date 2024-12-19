package Controller

import Util.{Command, Observable, Observer}
import Model.{Coordinate, Spieler, Spielfeld}

class SetCommand(action: String) extends Command {
  private var memento: Array[Array[Char]] = Spielfeld.getSpielfeld.map(_.clone)
  private var playerPosition: (Coordinate) = Spieler.getPosition

  override def doStep(): Unit = {
    memento = Spielfeld.getSpielfeld.map(_.clone)
    playerPosition = Spieler.getPosition
    Spieler.move(action)
    Spielfeld.hinsetze(Spieler.getPosition.x, Spieler.getPosition.y, 'R')
  }

  override def undoStep(): Unit = {
    Spielfeld.setup(memento)
    Spieler.position = Some(playerPosition)
  }

  override def redoStep(): Unit = doStep()
  
  //Ischt zum testen
  def getMemento(): Array[Array[Char]] = {
    memento
  }
}
