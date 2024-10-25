// Objekt, das das Spiel startet
object Spiel {
  def main(args: Array[String]): Unit = {
    // Initialisiere das Spielfeld und setze Hindernisse und den Diamanten
    val spielfeld = new Welt(10, 10)
    spielfeld.setHindernis(3, 3) // Hindernis auf Position (3, 3)
    spielfeld.setHindernis(4, 3) // Hindernis auf Position (4, 3)
    spielfeld.setDiamant(7, 7) // Diamant auf Position (7, 7)

    // Initialisiere den Spieler und das Level
    val spieler = new Spieler(0, 0) // Spieler startet bei (0, 0)
    val diamant = new Gegenstand(7, 7) // Diamant ist ebenfalls bei (7, 7)
    val level = new Level(spielfeld, spieler, diamant)

    println("Willkommen zum Diamantenjagd-Spiel!")
    var gewonnen = false // Flag, um den Gewinnstatus zu speichern

    // Spielschleife, solange der Spieler nicht gewonnen hat
    while (!gewonnen) {
      Welt.printField(spieler) // Drucke das aktuelle Spielfeld

      // Fordere den Spieler zur Eingabe auf
      println("Wohin möchtest du dich bewegen? (oben, unten, links, rechts)")
      val richtung = scala.io.StdIn.readLine() // Lese die Eingabe

      level.bewegeSpieler(richtung) // Bewege den Spieler

      // Überprüfe, ob der Spieler gewonnen hat
      if (level.hatGewonnen) {
        println("Glückwunsch! Du hast den Diamanten gefunden!") // Gewinnmeldung
        gewonnen = true // Setze das Gewinn-Flag
      }
    }
  }


}
