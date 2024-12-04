package Model

import Util.Observable

object Spielfeld extends Observable {

  // Char-Array für das Spielfeld
  private var grid: Array[Array[Char]] = Array.fill(10, 10)(' ') // Standardgröße, z.B. 10x10

  // Initialisieren des Grids mit einem übergebenen Char-Array
  def initialize(array: Array[Array[Char]]): Unit = {
    grid = Array.ofDim[Char](array.length, if (array.isEmpty) 0 else array(0).length)
    for (i <- array.indices; j <- array(i).indices) {
      grid(i)(j) = array(i)(j)
    }
    notifyObservers()  // Benachrichtige Observer nach der Initialisierung
  }

  // Methode, um einen Wert zu setzen
  def hinsetze(x: Int, y: Int, value: Char): Unit = {
    if (isValid(x, y)) {
      grid(x)(y) = value
      notifyObservers()  // Benachrichtige Observer, dass das Grid geändert wurde
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

  def getSpielerPos: Option[(Int, Int)] = {
    val spieler = for {
      x <- grid.indices.view
      y <- grid(x).indices.view
      if get(x, y) == 'R'
    } yield (x, y)
    spieler.headOption
  }

  // Methode, um die Gültigkeit der Position zu überprüfen
  private def isValid(x: Int, y: Int): Boolean = {
    x >= 0 && x < grid.length && y >= 0 && y < grid(0).length
  }



  // Optional: Methode, um das Spielfeld zurückzusetzen
  def reset(): Unit = {
    grid = Array.fill(10, 10)(' ')  // Reset auf Standardgröße mit Leerzeichen als Standardwert
    notifyObservers()  // Benachrichtige Observer nach Reset
  }
}