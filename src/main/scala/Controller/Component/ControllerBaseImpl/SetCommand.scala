package Controller.Component.ControllerBaseImpl

import Model.REPLComponent.REPLBaseImpl.REPL.Interpret
import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.SpielfeldComponent.Coordinate
import Model.SpielfeldComponent.SpielfeldBaseImpl.Spielfeld
import Util.Command

class SetCommand(action: String, controller: Controller) extends Command {
  private var memento: Array[Array[Char]] = Spielfeld.getSpielfeld.map(_.clone)
  private var playerPosition: (Coordinate) = Player.getPosition

  override def doStep(): Unit = {
    memento = Spielfeld.getSpielfeld.map(_.clone)
    //playerPosition = Spieler.getPosition
    //Spieler.move(action)
    Interpret(action)
  }

  override def undoStep(): Unit = {
    Spielfeld.->(memento) //.setup(memento)
    Player.position = Some(playerPosition)
  }

  override def redoStep(): Unit = doStep()
  
  //Ischt zum testen
  def getMemento(): Array[Array[Char]] = {
    memento
  }
}
