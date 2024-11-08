import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WeltTest extends AnyFlatSpec with Matchers {

  "Die Welt" should "ein Spielfeld mit den angegebenen Abmessungen erstellen" in {
    val welt = new Welt(5, 5)
    welt.grid.length should be(5)
    welt.grid(0).length should be(5)
  }

  it should "ein Hindernis auf die angegebene Position setzen" in {
    val welt = new Welt(5, 5)
    welt.setHindernis(2, 2)
    welt.grid(2)(2) should be('#')
  }

  it should "einen Diamanten auf die angegebene Position setzen" in {
    val welt = new Welt(5, 5)
    welt.setDiamant(3, 3)
    welt.grid(3)(3) should be('D')
  }

  it should "einen Fehler werfen, wenn versucht wird, einen Diamanten auf ein Hindernis zu setzen" in {
    val welt = new Welt(5, 5)
    welt.setHindernis(1, 1)
    an[IllegalArgumentException] should be thrownBy {
      welt.setDiamant(1, 1)
    }
  }

  it should "einen Fehler werfen, wenn versucht wird, ein Hindernis auf einen Diamanten zu setzen" in {
    val welt = new Welt(5, 5)
    welt.setDiamant(1, 1)
    an[IllegalArgumentException] should be thrownBy {
      welt.setHindernis(1, 1)
    }
  }

  it should "das Spielfeld korrekt mit der Position des Spielers anzeigen" in {
    val welt = new Welt(5, 5)
    val spieler = new Spieler(0, 0)
    welt.setHindernis(2, 2)
    welt.setDiamant(3, 3)
    welt.printField(spieler)
  }
}
