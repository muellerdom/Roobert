package Controller.Component.ControllerBaseImpl

import Model.REPLComponent.REPLBaseImpl.REPL.Interpret
import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.GridComponent.Coordinate
import Model.GridComponent.GridBaseImpl.Grid
import Util.Command

class SetCommand(action: String, controller: Controller) extends Command {
  private var memento: Grid = controller.getGrid.asInstanceOf[Grid] // Vorherigen Zustand speichern
  // private var playerPosition: (Coordinate) = Player(position = ???, direction = ???, inventory = ???).getPosition

  override def doStep(): Unit = {
    //    memento = Grid.getGrid.map(_.clone)
    //    //playerPosition = Spieler.getPosition
    //    //controller.getPlayer.move(action)
    //    controller.getPlayer.move(action)

    //try {

    memento = controller.getGrid.asInstanceOf[Grid] // Memento des aktuellen Grids

    //Interpret(action)

    println(action)

    val updatedGrid = controller.getGrid.asInstanceOf[Grid].movePlayer(action)
    controller.updateGrid(updatedGrid) // Aktuellen Grid im Controller aktualisieren
    //    } catch {
    //      case e: IllegalArgumentException => println(s"Bewegung fehlgeschlagen: ${e.getMessage}")
    //    }
  }

  override def undoStep(): Unit = {
    //Grid.->(memento) //.setup(memento)
    controller.updateGrid(memento)

  }

  override def redoStep(): Unit = doStep()

  //Ischt zum testen
  def getMemento(): Grid = {
    memento
  }

}
