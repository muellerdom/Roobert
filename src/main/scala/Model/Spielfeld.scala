package Model
import Util.Observable

class Spielfeld(val width: Int, val height: Int) extends Observable {
  private val grid: Array[Array[Int]] = Array.ofDim[Int](width, height)

  // Alternativkonstruktor
  def this(array: Array[Array[Int]]) = {
    this(array.length, if (array.isEmpty) 0 else array(0).length)
    for (i <- array.indices; j <- array(i).indices) {
      grid(i)(j) = array(i)(j)
    }
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
    x >= 0 && x < width && y >= 0 && y < height
  }

  // Beispielhafter Aufruf von notifyObservers(), um Beobachter zu benachrichtigen
  def updateGrid(): Unit = {
    // Änderungen am Grid vornehmen...
    notifyObservers()  // Benachrichtige Observer über das Update
  }
}
