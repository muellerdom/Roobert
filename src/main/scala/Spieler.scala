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

  var direction: String = "oben"

  // Methode, um den Spieler zu bewegen
  def move(movement: String): Unit = {
    //posX += dx  // X-Position aktualisieren
    //posY += dy  // Y-Position aktualisieren
    movement match {
      case "moveForward()" => moveForward()
      case "turnRight()" => turnRight()
      case "turnLeft()" => turnLeft()
      case _ => "other"
    }

    //Methode zur Bewegung der Figur auf dem Spielfeld
    def turnRight(): Unit = {
      direction match {
        case "oben" => direction = "rechts"
        case "rechts" => direction = "unten"
        case "unten" => direction = "links"
        case "links" => direction = "oben"
      }
    }

    //Methode zur Bewegung der Figur auf dem Spielfeld
    def turnLeft(): Unit = {
      direction match {
        case "oben" => direction = "links"
        case "rechts" => direction = "oben"
        case "unten" => direction = "rechts"
        case "links" => direction = "unten"
      }
    }

    //Methode zur Bewegung der Figur auf dem Spielfeld
    def moveForward(): Unit = {
      
      //Irgendwie muss man hier das SPielfeld übergeben, 
      // denn man möchte ja überprüfen ob der Spieler sich überhaupt in besagte Richtung bewegen darf
      
      direction match {
        case "oben" => posY += 1
        case "rechts" => posX += 1
        case "unten" => posY -= 1
        case "links" => posX -= 1
      }
    }
  }

  // String-Darstellung des Spielers
  override def toString: String = s"Spieler($posX, $posY)"
}

//Enum Spieler:

//Warum Object Movement und dann Funktionen ?
object Movement {

  //Methode zur Bewegung der Figur auf dem Spielfeld
  def turnRight(): Unit = {
  }

  //Methode zur Bewegung der Figur auf dem Spielfeld
  def turnLeft(): Unit = {
    println("Left ... Left")
  }

  //Methode zur Bewegung der Figur auf dem Spielfeld
  def moveForward(): Unit = {
    println("straight")
  }

  //Methode zur Drehung der Figur auf dem Spielfeld
  //def turn(): Unit = {}


}

//Figur-implementierung
object figure {

  //SEIZE HIM!!! :D
  val seize = 1.0 // LückenfüllerGröße

  printf("x")

}



//zu implementierende methoden
//addItem: Fügt einen Gegenstand zum Inventar hinzu.
//removeItem: Entfernt einen Gegenstand aus dem Inventar.
//addScore: Erhöht die Punktzahl.
//levelUp: Erhöht das Level des Spielers.


