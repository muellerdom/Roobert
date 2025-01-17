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
}*/