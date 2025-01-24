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
}*/package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.SpielfeldComponent.Coordinate
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JermSpec extends AnyFlatSpec with Matchers {

  "Jerm" should "initialize with correct position" in {
    val jerm = new Jerm(Coordinate(2, 2))
    jerm.getPosition shouldBe Coordinate(2, 2)
  }

  it should "have the correct symbol" in {
    val jerm = new Jerm(Coordinate(2, 2))
    jerm.Symbol shouldBe 'J'
  }

  it should "not block movement" in {
    val jerm = new Jerm(Coordinate(2, 2))
    jerm.isBlocking shouldBe false
  }

  it should "interact with player correctly" in {
    val jerm = new Jerm(Coordinate(2, 2))
    noException should be thrownBy jerm.interactWithPlayer()
  }
}