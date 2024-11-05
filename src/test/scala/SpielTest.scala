
// src/test/scala/test/scala/SpielTest.scala

//import main.scala.{Spieler, Welt, Gegenstand, Level}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.matchers.should.Matchers._

class SpielTest extends AnyFunSuite with Matchers {

  var welt: Welt = _
  var spieler: Spieler = _
  var diamant: Gegenstand = _
  var level: Level = _

  // Vor jedem Test: Initialisiere das Spielfeld, den Spieler und den Diamanten
  def beforeEach(): Unit = {
    welt = new Welt(10, 10)
    welt.setHindernis(3, 3) // Setze ein Hindernis
    diamant = new Gegenstand(7, 7) // Diamant auf Position (7, 7)
    spieler = new Spieler(0, 0) // Spieler startet bei (0, 0)
    level = new Level(welt, spieler, diamant) // Erstelle das Level
  }

  // Testet, ob der Spieler in leere Felder ziehen kann
  test("Spieler sollte sich in leere Felder bewegen können") {
    beforeEach() // Initialisiert den Testkontext

    level.bewegeSpieler("rechts") // Bewege nach (1,0)
    spieler.posX shouldBe 1
    spieler.posY shouldBe 0

    level.bewegeSpieler("unten") // Bewege nach (1,1)
    spieler.posX shouldBe 1
    spieler.posY shouldBe 1
  }

  // Testet, ob der Spieler nicht in eine Wand ziehen kann
  test("Spieler sollte nicht in eine Wand ziehen können") {
    beforeEach() // Initialisiert den Testkontext

    spieler.posX = 2
    spieler.posY = 3 // Spieler startet direkt vor einem Hindernis
    level.bewegeSpieler("rechts") // Versuch, nach (3,3) zu ziehen
    spieler.posX shouldBe 2
    spieler.posY shouldBe 3
  }

  // Testet, ob der Gewinnzustand korrekt erkannt wird
  test("Spieler sollte gewinnen, wenn er den Diamanten erreicht") {
    beforeEach() // Initialisiert den Testkontext

    spieler.posX = 7
    spieler.posY = 7 // Spieler beginnt direkt auf dem Diamanten
    level.hatGewonnen shouldBe true

    spieler.posX = 0 // Spieler bewegt sich weg
    spieler.posY = 0
    level.hatGewonnen shouldBe false
  }

  // Testet, ob das Spielfeld leer ist (Beispiel für Emptiness-Tests)
  test("Spielfeld sollte Hindernisse enthalten und nicht leer sein") {
    beforeEach() // Initialisiert den Testkontext

    //Hier vielleicht mit Schleife? -> Also man könnte überprüfen ob im Spielfeld '#' eingetragen ist
    welt.grid.exists(row => row.contains('#')) should be
    //welt.getHindernisse should not be empty
    //welt.getHindernisse should contain ((3, 3)) // enthält das Hindernis auf (3, 3)
    // das zweite wird bereits in WeltTest überprüft
  }
}
