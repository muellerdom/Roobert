/*package test.scala.Model.SpielfeldComponent

import Model.LevelComponent.Jerm
import Model.SpielfeldComponent.Coordinate
import Model.SpielfeldComponent.SpielfeldBaseImpl.{Coordinate, Jerm}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JermSpec extends AnyFlatSpec with Matchers {

  "Jerm" should "initialize with correct position" in {
    val jerm = Jerm(Coordinate(2, 2))
    jerm.position shouldBe Coordinate(2, 2)
  }

  it should "be collectible" in {
    val jerm = Jerm(Coordinate(2, 2))
    jerm.isCollectible shouldBe true
  }
}*/