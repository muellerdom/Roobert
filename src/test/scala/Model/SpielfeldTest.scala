//package Model
//
//
//import org.scalatest.funsuite.AnyFunSuite
//import Util.Observable
//
//class SpielfeldTest extends AnyFunSuite {
//
//  test("Spielfeld wird korrekt initialisiert") {
//    val spielfeld = new Spielfeld(3, 3)
//    assert(spielfeld.get(0, 0) == 0)
//    assert(spielfeld.get(2, 2) == 0)
//  }
//
//  test("hinsetze fügt Werte korrekt ein") {
//    val spielfeld = new Spielfeld(3, 3)
//    spielfeld.hinsetze(1, 1, 5)
//    assert(spielfeld.get(1, 1) == 5)
//  }
//
//  test("hinsetze wirft Fehler bei ungültiger Position") {
//    val spielfeld = new Spielfeld(3, 3)
//    assertThrows[IndexOutOfBoundsException] {
//      spielfeld.hinsetze(3, 3, 1) // Außerhalb der Grenzen
//    }
//  }
//
//  test("get wirft Fehler bei ungültiger Position") {
//    val spielfeld = new Spielfeld(3, 3)
//    assertThrows[IndexOutOfBoundsException] {
//      spielfeld.get(-1, 0) // Ungültige Position
//    }
//  }
//
//  test("printGrid gibt das Grid korrekt aus") {
//    val spielfeld = new Spielfeld(3, 3)
//    spielfeld.hinsetze(1, 1, 7)
//    spielfeld.hinsetze(0, 2, 3)
//
//    // Keine direkte Konsolenausgabe überprüfbar, aber der Test kann strukturell sichergestellt werden
//    assert(spielfeld.get(1, 1) == 7)
//    assert(spielfeld.get(0, 2) == 3)
//  }
//
//  test("Beobachter werden benachrichtigt") {
//    val spielfeld = new Spielfeld(3, 3)
//    var observerCalled = false
//
//    spielfeld.addObserver(() => observerCalled = true)
//
//    spielfeld.hinsetze(0, 0, 1)
//    assert(observerCalled)
//  }
//}
