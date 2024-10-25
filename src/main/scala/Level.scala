


import scala.io.StdIn.readLine
/*
Definiert eine einzelne Spielstufe.
Der name gibt dem Level einen Namen.
difficulty bestimmt, wie schwierig das Level ist.
tasks enthält eine Liste aller Aufgaben, die der Spieler in diesem Level lösen muss.
rewards sind die Belohnungen, die der Spieler erhält, wenn er das Level erfolgreich abschließt.
 */


class Level(val spielfeld: Welt, val spieler: Spieler, val diamant: Gegenstand) {
  def bewegeSpieler(richtung: String): Unit = {
    val (dx, dy) = richtung match {
      case "oben" => (0, -1)
      case "unten" => (0, 1)
      case "links" => (-1, 0)
      case "rechts" => (1, 0)
      case _ => (0, 0)
    }

    val neuePosX = spieler.posX + dx
    val neuePosY = spieler.posY + dy

    if (istGueltigeBewegung(neuePosX, neuePosY)) {
      spieler.bewege(dx, dy)
    }
  }

  def istGueltigeBewegung(x: Int, y: Int): Boolean = {
    x >= 0 && x < spielfeld.width && y >= 0 && y < spielfeld.height && spielfeld.grid(y)(x) != '#'
  }

  def hatGewonnen: Boolean = {
    spieler.posX == diamant.posX && spieler.posY == diamant.posY
  }
}
