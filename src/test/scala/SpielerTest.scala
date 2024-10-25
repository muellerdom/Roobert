

import main.scala.{Spieler, Welt, Gegenstand, Level}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class SpielerTest extends AnyFunSuite {

  // Testet, ob der Spieler bei den Startkoordinaten (0, 0) korrekt initialisiert wird.
  test("Spieler sollte bei den Startkoordinaten (0, 0) korrekt initialisiert werden") {
    val spielfeld = new Welt(5, 5)
    val spieler = new Spieler(0, 0)

    spieler.posX shouldBe 0
    spieler.posY shouldBe 0
  }

  // Testet, ob der Spieler sich korrekt in verschiedene Richtungen bewegen kann.
  test("Spieler sollte sich korrekt in verschiedene Richtungen bewegen können") {
    val spielfeld = new Welt(10, 10)
    val spieler = new Spieler(0, 0)

    spieler.bewege("rechts") // Bewegt den Spieler nach rechts
    spieler.posX shouldBe 1
    spieler.posY shouldBe 0

    spieler.bewege("unten") // Bewegt den Spieler nach unten
    spieler.posX shouldBe 1
    spieler.posY shouldBe 1

    spieler.bewege("links") // Bewegt den Spieler nach links
    spieler.posX shouldBe 0
    spieler.posY shouldBe 1

    spieler.bewege("oben") // Bewegt den Spieler nach oben
    spieler.posX shouldBe 0
    spieler.posY shouldBe 0
  }

  // Testet, ob der Spieler nicht in ein Hindernis hinein bewegen kann.
  test("Spieler sollte sich nicht in ein Hindernis hinein bewegen können") {
    val spielfeld = new Welt(10, 10)
    spielfeld.setHindernis(1, 0)
    val spieler = new Spieler(0, 0)

    spieler.bewege("rechts") // Versucht, sich nach rechts zu bewegen
    spieler.posX shouldBe 0
    spieler.posY shouldBe 0
  }

  // Testet, ob der Spieler das Spiel gewinnt, wenn er den Diamanten erreicht.
  test("Spieler sollte das Spiel gewinnen, wenn er den Diamanten erreicht") {
    val spielfeld = new Welt(10, 10)
    spielfeld.setDiamant(1, 0)
    val spieler = new Spieler(0, 0)
    val diamant = new Gegenstand(1, 0)
    val level = new Level(spielfeld, spieler, diamant) // Erstellt das Level

    spieler.bewege("rechts") // Bewegt den Spieler nach rechts
    level.hatGewonnen shouldBe true
  }
}
