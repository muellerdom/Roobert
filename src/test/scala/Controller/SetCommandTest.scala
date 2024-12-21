package Controller

import Controller.Component.ControllerBaseImpl.SetCommand
import Model.LevelComponent.Coordinate
import Model.SpielerComponent.PlayerBaseImpl.Spieler
import Model.SpielfeldComponent.SpielfeldBaseImpl.Spielfeld
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class SetCommandTest extends AnyWordSpec with Matchers {
  "A SetCommand" should {
    val action = "forward"
    val setCommand = new SetCommand(action)

    val initialSpielfeld: Array[Array[Char]] = Spielfeld.getSpielfeld.map(_.clone)
    val initialPosition: Coordinate = Spieler.getPosition

    "copies an exact copy of Spielfeld" in {
      setCommand.getMemento() shouldBe initialSpielfeld
    }

    "properly execute doStep method" in {
      setCommand.doStep()
      //memento should be different than previous after doStep
      setCommand.getMemento() shouldNot initialSpielfeld
      Spieler.getPosition shouldBe Spieler.move(action)
      Spielfeld.getSpielfeld(Spieler.getPosition.x)(Spieler.getPosition.y) shouldBe 'R'
    }

    "properly execute undoStep method" in {
      setCommand.undoStep()
      Spieler.getPosition shouldBe initialPosition
      Spielfeld.getSpielfeld shouldEqual initialSpielfeld
    }

    "properly execute redoStep method" in {
      setCommand.redoStep()
      //Spieler.getPosition shouldBe (initialPosition.move(action))
      Spielfeld.getSpielfeld(Spieler.getPosition.x)(Spieler.getPosition.y) shouldBe 'R'
    }
  }
}