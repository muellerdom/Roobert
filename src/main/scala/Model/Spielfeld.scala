package Model

import Util.Observable

object Spielfeld extends Observable {

  private var grid: Array[Array[Int]] = Array.ofDim[Int](10, 10)  // Standardgröße, z.B. 10x10

  // Alternativkonstruktor
  def initialize(array: Array[Array[Int]]): Unit = {
    grid = Array.ofDim[Int](array.length, if (array.isEmpty) 0 else array(0).length)
    for (i <- array.indices; j <- array(i).indices) {
      grid(i)(j) = array(i)(j)
    }
    notifyObservers()  // Benachrichtige Observer nach der Initialisierung
  }

  // Methode, um einen Wert zu setzen
  def hinsetze(x: Int, y: Int, value: Int): Unit = {
    if (isValid(x, y)) {
      grid(x)(y) = value
      notifyObservers()  // Benachrichtige Observer, dass das Grid geändert wurde
    } else {
      throw new IndexOutOfBoundsException("Ungültige Position im Grid.")
    }
  }

  // Methode, um einen Wert abzurufen
  def get(x: Int, y: Int): Int = {
    if (isValid(x, y)) {
      grid(x)(y)
    } else {
      throw new IndexOutOfBoundsException("Ungültige Position im Grid.")
    }
  }

  // Methode, um das Grid auszugeben (für Debugging)
  def printGrid(): Unit = {
    for (row <- grid) {
      println(row.mkString(" "))
    }
  }

  // Methode, um die Gültigkeit der Position zu überprüfen
  private def isValid(x: Int, y: Int): Boolean = {
    x >= 0 && x < grid.length && y >= 0 && y < grid(0).length
  }

  // Beispielhafter Aufruf von notifyObservers(), um Beobachter zu benachrichtigen
  def updateGrid(): Unit = {
    // Änderungen am Grid vornehmen...
    notifyObservers()  // Benachrichtige Observer über das Update
  }

  // Methode, um das Spielfeld zurückzusetzen (optional)
  def reset(): Unit = {
    grid = Array.ofDim[Int](10, 10)  // Reset auf Standardgröße
    notifyObservers()  // Benachrichtige Observer nach Reset
  }

  // Factory-Pattern für Spielfeld
  object SpielfeldFactory {

    // Factory-Methode für ein Spielfeld mit benutzerdefinierter Größe
    def createSpielfeld(rows: Int, cols: Int): Spielfeld.type = {
      val newGrid = Array.ofDim[Int](rows, cols)
      Spielfeld.grid = newGrid
      notifyObservers()  // Benachrichtige Observer nach der Erstellung eines neuen Spielfeldes
      Spielfeld
    }

    // Factory-Methode für ein Spielfeld mit einer benutzerdefinierten Initialisierung
    def createSpielfeldWithInitialization(array: Array[Array[Int]]): Spielfeld.type = {
      Spielfeld.initialize(array)
      Spielfeld
    }
  }
}
