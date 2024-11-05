

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class GegenstandTest extends AnyFunSuite {

  test("Gegenstand sollte korrekt initialisiert werden") {
    // Initialisiere den Gegenstand
    val diamant = new Gegenstand(5, 10)
    diamant.posX shouldBe 5
    diamant.posY shouldBe 10
  }

  test("Gegenstand sollte eine korrekte String-Darstellung haben") {
    val diamant = new Gegenstand(3, 4)
    diamant.toString shouldBe "Diamant(3, 4)"
  }
}
