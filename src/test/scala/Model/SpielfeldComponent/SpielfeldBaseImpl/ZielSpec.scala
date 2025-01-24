/*package Model.SpielfeldComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ZielSpec extends AnyFlatSpec with Matchers {

  "Ziel" should "initialize with correct position" in {
    val ziel = Ziel(Coordinate(3, 3))
    ziel.position shouldBe Coordinate(3, 3)
  }

  it should "be reachable" in {
    val ziel = Ziel(Coordinate(3, 3))
    ziel.isReachable shouldBe true
  }
}*/package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.SpielfeldComponent.Coordinate
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ZielSpec extends AnyFlatSpec with Matchers {

  "Ziel" should "initialize with correct position" in {
    val ziel = new Ziel(Coordinate(3, 3))
    ziel.getPosition shouldBe Coordinate(3, 3)
  }

  it should "have the correct symbol" in {
    val ziel = new Ziel(Coordinate(3, 3))
    ziel.Symbol shouldBe 'G'
  }

  it should "not block movement" in {
    val ziel = new Ziel(Coordinate(3, 3))
    ziel.isBlocking shouldBe false
  }

  it should "interact with player correctly" in {
    val ziel = new Ziel(Coordinate(3, 3))
    noException should be thrownBy ziel.interactWithPlayer()
  }
}