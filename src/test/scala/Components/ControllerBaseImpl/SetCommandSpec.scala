package Controller.Component.ControllerBaseImpl

import Model.LevelComponent.LevelManagerTrait
import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}
import Model.SpielfeldComponent.SpielfeldBaseImpl.Spielfeld
import org.scalamock.clazz.MockImpl.mock
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SetCommandSpec extends AnyFlatSpec with Matchers {

  "SetCommand" should "execute a command" in {
    val controller = new Controller(mock[LevelManagerTrait])
    val command = new SetCommand("move", controller)

    command.doStep()

    // Add assertions to verify the command execution
  }

  it should "undo a command" in {
    val controller = new Controller(mock[LevelManagerTrait])
    val command = new SetCommand("move", controller)

    command.doStep()
    command.undoStep()

    // Add assertions to verify the command undo
  }

  it should "redo a command" in {
    val controller = new Controller(mock[LevelManagerTrait])
    val command = new SetCommand("move", controller)

    command.doStep()
    command.undoStep()
    command.redoStep()

    // Add assertions to verify the command redo
  }
}