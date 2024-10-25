/*
Repräsentiert den Spieler, der durch das Spiel navigiert.
Der name dient zur Identifizierung.
level zeigt an, wie weit der Spieler im Spiel fortgeschritten ist.
score zählt die Punkte, die der Spieler durch das Lösen von Aufgaben sammelt.
inventory speichert alle Gegenstände, die der Spieler gesammelt hat.
knowledge ist ein Maß dafür, wie gut der Spieler die Scala-Konzepte verstanden hat.
 */

// Definition der Spielerklasse
// Die Klasse Spieler repräsentiert den Spieler im Spiel
class Spieler(var posX: Int, var posY: Int) {
  // Methode, um den Spieler zu bewegen
  def bewege(dx: Int, dy: Int): Unit = {
    posX += dx  // X-Position aktualisieren
    posY += dy  // Y-Position aktualisieren
  }

  // String-Darstellung des Spielers
  override def toString: String = s"Spieler($posX, $posY)"
}

//Enum Spieler:


object Movement {

  //Methode zur Bewegung der Figur auf dem Spielfeld
  def moveRight(): Unit = {
    println("Right")
  }

  //Methode zur Bewegung der Figur auf dem Spielfeld
  def moveLeft(): Unit = {
    println("Left ... Left")
  }

  //Methode zur Bewegung der Figur auf dem Spielfeld
  def moveForward(): Unit = {
    println("straight")
  }

  //Methode zur Drehung der Figur auf dem Spielfeld
  def turn(): Unit = {}


}

//Figur-implementierung
object figure {

  val seize = 1.0 // LückenfüllerGröße

  printf("x")

}



//zu implementierende methoden
//addItem: Fügt einen Gegenstand zum Inventar hinzu.
//removeItem: Entfernt einen Gegenstand aus dem Inventar.
//addScore: Erhöht die Punktzahl.
//levelUp: Erhöht das Level des Spielers.


