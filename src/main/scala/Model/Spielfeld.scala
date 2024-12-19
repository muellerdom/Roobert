package Model

import Util.Observable

object Spielfeld extends Observable {

  // Char-Array für das Spielfeld
  private var grid: Array[Array[Char]] = Array.fill(10, 10)(' ') // Standardgröße, z.B. 10x10
  private var spielerPosition: Option[Coordinate] = None // Speichert die Position des Spielers

  // Initialisieren des Grids mit einem übergebenen Char-Array
  def initialize(array: Array[Array[Char]]): Unit = {
    grid = Array.ofDim[Char](array.length, if (array.isEmpty) 0 else array(0).length)
    for (i <- array.indices; j <- array(i).indices) {
      grid(i)(j) = array(i)(j)
    }
  }

  // Hinsetzen eines Wertes auf das Spielfeld
  def hinsetze(x: Int, y: Int, value: Char): Unit = {
    if (isValid(x, y)) {
      grid(x)(y) = value
      if (value == 'R') setSpielerPosition(Coordinate(x, y))
    } else {
      throw new IndexOutOfBoundsException("Ungültige Position im Grid.")
    }
  }

  // Methode, um einen Wert abzurufen
  def get(x: Int, y: Int): Char = {
    if (isValid(x, y)) {
      grid(x)(y)
    } else {
      throw new IndexOutOfBoundsException("Ungültige Position im Grid.")
    }
  }

  def getSpielfeld: Array[Array[Char]] = {
    grid
  }

  def setup(newGrid : Array[Array[Char]]): Unit = {
    grid = newGrid
  }

  def getSpielerPos: Option[(Int, Int)] = {
    val spieler = for {
      x <- grid.indices.view
      y <- grid(x).indices.view
      if get(x, y) == 'R'
    } yield (x, y)
    spieler.headOption
  }

  // Methode, um die Spielerposition zu aktualisieren
  def setSpielerPosition(pos: Coordinate): Unit = {
    spielerPosition = Some(pos)
  }

  // Getter für die Spielerposition
  def getSpielerPosition: Option[Coordinate] = spielerPosition



  // Entfernen eines Wertes
  def entfernen(x: Int, y: Int): Unit = {
    if (isValid(x, y)) {
      grid(x)(y) = ' '
      notifyObservers()
    } else {
      throw new IndexOutOfBoundsException("Ungültige Position im Grid.")
    }
  }

  // Methode, um die Gültigkeit der Position zu überprüfen
  private def isValid(x: Int, y: Int): Boolean = {
    x >= 0 && x < grid.length && y >= 0 && y < grid(0).length
  }



  // Optional: Methode, um das Spielfeld zurückzusetzen
//  def reset(): Unit = {
//    grid = Array.fill(10, 10)(' ')  // Reset auf Standardgröße mit Leerzeichen als Standardwert
//    notifyObservers()  // Benachrichtige Observer nach Reset
//  }
}