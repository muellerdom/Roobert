package Model.SpielfeldComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory

class KomponentenInterfaceSpec extends AnyFlatSpec with Matchers with MockFactory {

  "KomponentenInterface" should "return correct position" in {
    val component = mock[KomponentenInterface]
    val position = Coordinate(1, 1)
    (component.getPosition _).expects().returning(position).once()
    component.getPosition shouldBe position
  }

  it should "return correct symbol" in {
    val component = mock[KomponentenInterface]
    (component.Symbol _).expects().returning('X').once()
    component.Symbol shouldBe 'X'
  }

  it should "return correct blocking status" in {
    val component = mock[KomponentenInterface]
    (component.isBlocking _).expects().returning(true).once()
    component.isBlocking shouldBe true
  }

  it should "interact with player correctly" in {
    val component = mock[KomponentenInterface]
    (component.interactWithPlayer _).expects().once()
    noException should be thrownBy component.interactWithPlayer()
  }
}