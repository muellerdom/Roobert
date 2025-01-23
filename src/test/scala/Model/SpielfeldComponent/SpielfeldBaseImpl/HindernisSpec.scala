package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.SpielfeldComponent.Coordinate
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class HindernisSpec extends AnyFlatSpec with Matchers {

  "Hindernis" should "initialize with correct position" in {
    val hindernis = new Hindernis(Coordinate(1, 1))
    hindernis.getPosition shouldBe Coordinate(1, 1)
  }

  it should "have the correct symbol" in {
    val hindernis = new Hindernis(Coordinate(1, 1))
    hindernis.Symbol shouldBe 'X'
  }

  it should "block movement" in {
    val hindernis = new Hindernis(Coordinate(1, 1))
    hindernis.isBlocking shouldBe true
  }

  it should "interact with player correctly" in {
    val hindernis = new Hindernis(Coordinate(1, 1))
    noException should be thrownBy hindernis.interactWithPlayer()
  }
}