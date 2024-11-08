
/*
Stellt einen Gegenstand dar, den der Spieler sammeln kann.
name ist der Name des Gegenstands.
effect beschreibt, was der Gegenstand bewirkt, z.B. gibt er dem Spieler zusätzliche Leben oder macht ihn schneller.


ALS INTERFACE!

 */

//// Nun, was willst du nutzen Gegenstand oder Welt.setHindernes || setDiamant


// Die Klasse Diamant repräsentiert den Jerm im Spiel
class Gegenstand(val posX: Int, val posY: Int) {
  // String-Darstellung des Jerm
  override def toString: String = s"Diamant($posX, $posY)"
}

