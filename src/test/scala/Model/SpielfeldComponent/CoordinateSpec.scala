package Model.SpielfeldComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CoordinateSpec extends AnyFlatSpec with Matchers {

  "Coordinate" should "check equality correctly" in {
    val coord1 = Coordinate(1, 1)
    val coord2 = Coordinate(1, 1)
    val coord3 = Coordinate(2, 2)
    coord1.isEqualTo(coord2) shouldBe true
    coord1.isEqualTo(coord3) shouldBe false
  }

  it should "calculate distance correctly" in {
    val coord1 = Coordinate(0, 0)
    val coord2 = Coordinate(3, 4)
    coord1.distanceTo(coord2) shouldBe 5.0
  }

  it should "add coordinates correctly" in {
    val coord1 = Coordinate(1, 1)
    val coord2 = Coordinate(2, 2)
    coord1 + coord2 shouldBe Coordinate(3, 3)
  }

  it should "subtract coordinates correctly" in {
    val coord1 = Coordinate(3, 3)
    val coord2 = Coordinate(1, 1)
    coord1 - coord2 shouldBe Coordinate(2, 2)
  }

  it should "check if coordinate is in list" in {
    val coord = Coordinate(1, 1)
    val list = List(Coordinate(1, 1), Coordinate(2, 2))
    coord.isIn(list) shouldBe true
  }
}