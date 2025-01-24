package Model.SpielfeldComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory

class SpielfeldInterfaceSpec extends AnyFlatSpec with Matchers with MockFactory {

  "SpielfeldInterface" should "remove components correctly" in {
    val spielfeld = mock[SpielfeldInterface]
    (spielfeld.entfernen _).expects(1, 1).once()
    spielfeld.entfernen(1, 1)
  }

  it should "return correct value at position" in {
    val spielfeld = mock[SpielfeldInterface]
    (spielfeld.getAnPos _).expects(1, 1).returning('X').once()
    spielfeld.getAnPos(1, 1) shouldBe 'X'
  }

  it should "return correct Spielfeld grid" in {
    val spielfeld = mock[SpielfeldInterface]
    val grid = Array.ofDim[Char](5, 5)
    (spielfeld.getSpielfeld _).expects().returning(grid).once()
    spielfeld.getSpielfeld shouldBe grid
  }

  it should "set and get player position correctly" in {
    val spielfeld = mock[SpielfeldInterface]
    val position = Coordinate(1, 1)
    (spielfeld.setSpielerPosition _).expects(position).once()
    (spielfeld.getSpielerPosition _).expects().returning(Some(position)).once()
    spielfeld.setSpielerPosition(position)
    spielfeld.getSpielerPosition shouldBe Some(position)
  }
}