import main.scala.{Spieler, Welt, Gegenstand, Level}
import org.junit.jupiter.api.Assertions.{assertEquals, assertTrue}
import org.junit.jupiter.api.Test
import sbt.testing

import main.scala.{Gegenstand}
import org.scalatest.funsuite.AnyFunSuite

class GegenstandTest extends AnyFunSuite {

  test("Gegenstand sollte korrekt initialisiert werden") {
    // Initialisiere den Gegenstand
    val diamant = new Gegenstand(5, 10)
    assert(diamant.posX == 5)
    assert(diamant.posY == 10)
  }

  test("Gegenstand sollte eine korrekte String-Darstellung haben") {
    val diamant = new Gegenstand(3, 4)
    assert(diamant.toString == "Diamant(3, 4)")
  }
}
