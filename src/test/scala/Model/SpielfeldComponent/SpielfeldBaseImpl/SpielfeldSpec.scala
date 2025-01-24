/*package Model.SpielfeldComponent

import Model.SpielfeldComponent.SpielfeldBaseImpl.{ Hindernis, Spielfeld}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SpielfeldSpec extends AnyFlatSpec with Matchers {

  "Spielfeld" should "initialize with correct dimensions" in {
    val spielfeld = Spielfeld(10, 10)
    spielfeld.width shouldBe 10
    spielfeld.height shouldBe 10
  }

  it should "add a component" in {
    val spielfeld = Spielfeld(10, 10)
    val hindernis = Hindernis(Coordinate(1, 1))
    spielfeld.addComponent(hindernis)
    spielfeld.components should contain(hindernis)
  }

  it should "remove a component" in {
    val spielfeld = Spielfeld(10, 10)
    val hindernis = Hindernis(Coordinate(1, 1))
    spielfeld.addComponent(hindernis)
    spielfeld.removeComponent(hindernis)
    spielfeld.components should not contain hindernis
  }
}*/package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.SpielfeldComponent.Coordinate
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SpielfeldSpec extends AnyFlatSpec with Matchers {

  "Spielfeld" should "initialize components correctly" in {
    Spielfeld.resetComponents()
    Spielfeld.components should not be empty
  }

  it should "set and get player position correctly" in {
    val position = Coordinate(1, 1)
    Spielfeld.setSpielerPosition(position)
    Spielfeld.getSpielerPosition shouldBe Some(position)
  }

  it should "remove components correctly" in {
    val position = Coordinate(1, 1)
    Spielfeld.resetComponents()
    Spielfeld.entfernen(position.x, position.y)
    Spielfeld.components.exists(_.getPosition == position) shouldBe false
  }

  it should "return correct symbol at position" in {
    val position = Coordinate(1, 1)
    Spielfeld.setSpielerPosition(position)
    Spielfeld.getAnPos(position.x, position.y) shouldBe 'R'
  }

  it should "return correct Spielfeld grid" in {
    Spielfeld.resetComponents()
    val grid = Spielfeld.getSpielfeld
    grid should not be empty
  }
}