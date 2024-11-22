//package Model
//package Model
//
//import org.scalatest.funsuite.AnyFunSuite
//
//class TaschenInhaltTest extends AnyFunSuite {
//
//  // Testobjekte erstellen
//  class TestTaschenInhalt(name: String, groesse: Double, beschreibung: String, farbe: String)
//    extends TaschenInhalt(name, groesse, beschreibung, farbe)
//
//  test("einsammeln fügt neues Objekt in die Liste ein") {
//    val taschenInhalt = new TestTaschenInhalt("Tasche", 1.0, "Eine Spielertasche", "braun")
//    val obj1 = new TestTaschenInhalt("Apfel", 0.2, "Ein roter Apfel", "rot")
//    val obj2 = new TestTaschenInhalt("Buch", 0.5, "Ein blaues Buch", "blau")
//
//    taschenInhalt.einsammeln(obj1)
//    taschenInhalt.einsammeln(obj2)
//
//    assert(taschenInhalt.eingesammelteObjekte.contains(obj1))
//    assert(taschenInhalt.eingesammelteObjekte.contains(obj2))
//  }
//
//  test("einsammeln fügt kein doppeltes Objekt ein") {
//    val taschenInhalt = new TestTaschenInhalt("Tasche", 1.0, "Eine Spielertasche", "braun")
//    val obj1 = new TestTaschenInhalt("Apfel", 0.2, "Ein roter Apfel", "rot")
//
//    taschenInhalt.einsammeln(obj1)
//    taschenInhalt.einsammeln(obj1) // Versucht, dasselbe Objekt erneut hinzuzufügen
//
//    assert(taschenInhalt.eingesammelteObjekte.size == 1)
//  }
//
//  test("einsammeln gibt Fehlermeldung für ungültiges Objekt aus") {
//    val taschenInhalt = new TestTaschenInhalt("Tasche", 1.0, "Eine Spielertasche", "braun")
//    val invalidObj = new Objekt {
//      val name = "Ungültig"
//      val groesse = 1.0
//      val beschreibung = "Kein TaschenInhalt"
//      val farbe = "grau"
//    }
//
//
//    // Das folgende sollte eine Fehlermeldung ausgeben
//    taschenInhalt.einsammeln(invalidObj)
//
//    // Es wird nichts zur Liste hinzugefügt
//    assert(taschenInhalt.eingesammelteObjekte.isEmpty)
//  }
//
//  test("aufsFeldSetzen wird aufgerufen") {
//    val taschenInhalt = new TestTaschenInhalt("Tasche", 1.0, "Eine Spielertasche", "braun")
//
//    // Da die Methode leer ist, prüfen wir, dass kein Fehler auftritt
//    taschenInhalt.aufsFeldSetzen()
//
//    // Kein spezifisches Verhalten definiert, Test nur auf erfolgreiche Ausführung
//    assert(true)
//  }
//}
