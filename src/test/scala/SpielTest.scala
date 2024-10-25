import main.scala.{Spieler, Welt, Gegenstand, Level}
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach // Füge die BeforeEach-Annotation hinzu

class SpielTest {

  var welt: Welt = _
  var spieler: Spieler = _
  var diamant: Gegenstand = _
  var level: Level = _

  // Vor jedem Test: Initialisiere das Spielfeld, den Spieler und den Diamanten
  @BeforeEach
  def before(): Unit = {
    welt = new Welt(10, 10)
    welt.setHindernis(3, 3) // Setze ein Hindernis
    diamant = new Gegenstand(7, 7) // Diamant auf Position (7, 7)
    spieler = new Spieler(0, 0) // Spieler startet bei (0, 0)
    level = new Level(welt, spieler, diamant) // Erstelle das Level
  }

  // Testet, ob der Spieler in leere Felder ziehen kann
  @Test
  def erlaubenDassSpielerInLeereFelderZieht(): Unit = {
    level.bewegeSpieler("rechts") // Bewege nach (1,0)
    assertEquals(1, spieler.posX) // Überprüft die X-Position
    assertEquals(0, spieler.posY) // Überprüft die Y-Position

    level.bewegeSpieler("unten") // Bewege nach (1,1)
    assertEquals(1, spieler.posX) // Überprüft die X-Position
    assertEquals(1, spieler.posY) // Überprüft die Y-Position
  }

  // Testet, ob der Spieler nicht in eine Wand ziehen kann
  @Test
  def nichtErlaubenDassSpielerInEineWandZieht(): Unit = {
    spieler.posX = 2
    spieler.posY = 3 // Spieler startet direkt vor einem Hindernis
    level.bewegeSpieler("rechts") // Versuch, nach (3,3) zu ziehen
    assertEquals(2, spieler.posX) // Position sollte gleich bleiben
    assertEquals(3, spieler.posY) // Position sollte gleich bleiben
  }

  // Testet, ob der Gewinnzustand korrekt erkannt wird
  @Test
  def erkennenWennDerSpielerGewonnenHat(): Unit = {
    spieler.posX = 7
    spieler.posY = 7 // Spieler beginnt direkt auf dem Diamanten
    assertEquals(true, level.hatGewonnen) // Spieler sollte gewonnen haben

    spieler.posX = 0 // Spieler bewegt sich weg
    spieler.posY = 0
    assertEquals(false, level.hatGewonnen) // Spieler sollte nicht mehr gewonnen haben
  }
}

