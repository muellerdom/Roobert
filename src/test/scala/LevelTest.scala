package test.scala

//import main.scala
//import main.scala.{Gegenstand, Level, Spieler, Welt}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers._

class LevelTest extends AnyFunSuite {

//  test("Spieler soll sich korrekt bewegen") {
//    // Erstelle ein Spielfeld mit einer bestimmten Größe
//    val welt = new Welt(5, 5)
//    welt.setHindernis(1, 1)
//    welt.setHindernis(2, 2)
//
//    val spieler = new Spieler(0, 0)
//    val diamant = new Gegenstand(4, 4)
//    val level = new Level(welt, spieler, diamant)
//
//    level.bewegeSpieler("unten")
//    spieler.posX shouldBe 0
//    spieler.posY shouldBe 1
//
//    level.bewegeSpieler("rechts") // Sollte nicht bewegen wegen Hindernis
//    spieler.posX shouldBe 0
//    spieler.posY shouldBe 1
//
//    // Bewege den Spieler nach oben (zu (0,0))
//    level.bewegeSpieler("oben")
//    spieler.posX shouldBe 0
//    spieler.posY shouldBe 0
//
//    // Bewege den Spieler nach links (versuche es)
//    level.bewegeSpieler("links")
//    spieler.posX shouldBe 0
//    spieler.posY shouldBe 0
//  }
//
//  test("Spieler sollte sich nicht in eine Wand bewegen können") {
//    val welt = new Welt(5, 5)
//    welt.setHindernis(1, 1)
//    val spieler = new Spieler(0, 0)
//    val diamant = new Gegenstand(4, 4)
//    val level = new Level(welt, spieler, diamant)
//
//    level.bewegeSpieler("rechts") // Versucht, sich nach (1, 0) zu bewegen
//    spieler.posX shouldBe 0
//    spieler.posY shouldBe 0 // Sollte die Position nicht ändern
//  }
//
//  test("Prüfe, ob der Spieler gewonnen hat") {
//    val welt = new Welt(5, 5)
//    val spieler = new Spieler(4, 4)
//    val diamant = new Gegenstand(4, 4) // Diamant an der gleichen Position
//    val level = new Level(welt, spieler, diamant)
//
//    // Überprüfe, ob der Spieler gewonnen hat
//    level.hatGewonnen shouldBe true
//
//    // Stelle den Spieler an eine andere Position
//    spieler.posX = 0
//    spieler.posY = 0
//
//    // Noch einmal prüfen
//    level.hatGewonnen shouldBe false
//  }
}
