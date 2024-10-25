import main.scala.{Gegenstand, Level, Spieler, Welt}
import org.junit.jupiter.api.Assertions.{assertEquals, assertFalse, assertTrue}
import org.junit.jupiter.api.Test
class LevelTest {

  @Test
  def testMovePlayerCorrectly(): Unit = {
    // Erstelle ein Spielfeld mit einer bestimmten Größe
    val welt = new Welt(5, 5)

    welt.setHindernis(1, 1)
    welt.setHindernis(2, 2)

    val spieler = new Spieler(0, 0)
    val diamant = new Gegenstand(4, 4)
    //desch Level
    val level = new Level(welt, spieler, diamant)


    level.bewegeSpieler("unten")
    assertEquals(0, spieler.posX)
    assertEquals(1, spieler.posY)


    level.bewegeSpieler("rechts")
    assertEquals(0, spieler.posX)
    assertEquals(1, spieler.posY)

    // Bewege den Spieler nach oben (zu (0,0))
    level.bewegeSpieler("oben")
    assertEquals(0, spieler.posX)
    assertEquals(0, spieler.posY)

    // Bewege den Spieler nach links (versuche es)
    level.bewegeSpieler("links")
    assertEquals(0, spieler.posX)
    assertEquals(0, spieler.posY)
  }

  @Test
  def testNotMovePlayerIntoWall(): Unit = {

    val welt = new Welt(5, 5)
    welt.setHindernis(1, 1)
    val spieler = new Spieler(0, 0)
    val diamant = new Gegenstand(4, 4)
    val level = new Level(welt, spieler, diamant)


    level.bewegeSpieler("rechts")
    assertEquals(0, spieler.posX) // Sollte die Position nicht ändern
    assertEquals(0, spieler.posY)
  }

  @Test
  def testCheckIfPlayerHasWon(): Unit = {

    val welt = new Welt(5, 5)
    val spieler = new Spieler(4, 4)
    val diamant = new Gegenstand(4, 4) // Diamant an der gleichen Position
    val level = new Level(welt, spieler, diamant)

    // Überprüfe, ob der Spieler gewonnen hat
    assertTrue(level.hatGewonnen)

    // Stelle den Spieler an eine andere Position
    spieler.posX = 0
    spieler.posY = 0

   //nochmal luege
    assertFalse(level.hatGewonnen)
  }
}


