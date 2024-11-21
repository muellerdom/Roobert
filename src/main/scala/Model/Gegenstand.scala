package Model


/*
Stellt einen Model.Gegenstand dar, den der Model.Spieler sammeln kann.
name ist der Name des Gegenstands.
effect beschreibt, was der Model.Gegenstand bewirkt, z.B. gibt er dem Model.Spieler zusätzliche Leben oder macht ihn schneller.
 */

//// Nun, was willst du nutzen Model.Gegenstand oder Model.Welt.setHindernes || setDiamant


// Die Klasse Diamant repräsentiert den Jerm im Spiel
class Gegenstand(val posX: Int, val posY: Int) {
  // String-Darstellung des Jerm
  override def toString: String = s"Diamant($posX, $posY)"
}

