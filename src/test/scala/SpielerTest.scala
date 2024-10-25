import main.scala.{Spieler, Welt, Gegenstand, Level}
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

// Diese Testklasse überprüft die Funktionalität der Spieler-Klasse und das Spielverhalten
class SpielerTest {

  // Testet, ob der Spieler bei den Startkoordinaten (0, 0) korrekt initialisiert wird.
  @Test
  def testSpielerStartPosition(): Unit = {
    val spielfeld = new Welt(5, 5)
    val spieler = new Spieler(0, 0)

    assertEquals(0, spieler.posX, "Spieler sollte bei (0, 0) starten")
    assertEquals(0, spieler.posY, "Spieler sollte bei (0, 0) starten")
  }

  // Testet, ob der Spieler sich korrekt in verschiedene Richtungen bewegen kann.
  @Test
  def testSpielerBewegung(): Unit = {
    val spielfeld = new Welt(10, 10)
    val spieler = new Spieler(0, 0)
    spieler.bewege("rechts") // Bewegt den Spieler nach rechts
    assertEquals(1, spieler.posX, "Spieler sollte sich nach rechts bewegt haben")
    assertEquals(0, spieler.posY, "Spieler sollte sich nach rechts bewegt haben")

    spieler.bewege("unten") // Bewegt den Spieler nach unten
    assertEquals(1, spieler.posX, "Spieler sollte sich nach unten bewegt haben")
    assertEquals(1, spieler.posY, "Spieler sollte sich nach unten bewegt haben")

    spieler.bewege("links") // Bewegt den Spieler nach links
    assertEquals(0, spieler.posX, "Spieler sollte sich nach links bewegt haben")
    assertEquals(1, spieler.posY, "Spieler sollte sich nach links bewegt haben")

    spieler.bewege("oben") // Bewegt den Spieler nach oben
    assertEquals(0, spieler.posX, "Spieler sollte sich nach oben bewegt haben")
    assertEquals(0, spieler.posY, "Spieler sollte sich nach oben bewegt haben")
  }

  // Testet, ob der Spieler nicht in ein Hindernis hinein bewegen kann.
  @Test
  def testSpielerHindernisse(): Unit = {
    val spielfeld = new Welt(10, 10)
    spielfeld.setHindernis(1, 0)
    val spieler = new Spieler(0, 0)

    spieler.bewege("rechts") // Versucht, sich nach rechts zu bewegen
    assertEquals(0, spieler.posX, "Spieler sollte sich nicht in ein Hindernis bewegen können")
    assertEquals(0, spieler.posY, "Spieler sollte sich nicht in ein Hindernis bewegen können")
  }

  // Testet, ob der Spieler das Spiel gewinnt, wenn er den Diamanten erreicht.
  @Test
  def testSpielGewinnen(): Unit = {
    val spielfeld = new Welt(10, 10)
    spielfeld.setDiamant(1, 0)
    val spieler = new Spieler(0, 0)
    val diamant = new Gegenstand(1, 0)
    val level = new Level(spielfeld, spieler, diamant) // Erstellt das Level

    spieler.bewege("rechts") // Bewegt den Spieler nach rechts
    assertTrue(level.hatGewonnen, "Spieler sollte das Spiel gewonnen haben, wenn er den Diamanten erreicht")
  }
}

