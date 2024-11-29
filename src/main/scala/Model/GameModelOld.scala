package Model

import Util.Observable

/**
 * Repräsentiert das Spielfeld und die Spiellogik.
 */
class GameModel private(val size: Int) extends Observable {

  private val board: Array[Array[Char]] = Array.fill(size, size)('-')
  private var figureX: Int = 0
  private var figureY: Int = 0
  private val targetX: Int = 2
  private val targetY: Int = 0

  /**
   * Initialisiert das Spiel und setzt die Figuren.
   */
  def initializeGame(): Unit = {
    placeFigure(figureX, figureY, 'F')
    placeFigure(targetX, targetY, 'Z')
    notifyObservers()  // Benachrichtige Observer nach der Initialisierung des Spiels
  }

  /**
   * Platziert eine Figur auf dem Spielfeld.
   * @param x Die x-Koordinate.
   * @param y Die y-Koordinate.
   * @param symbol Das Symbol der Figur.
   * @return true, wenn die Platzierung erfolgreich war, andernfalls false.
   */
  def placeFigure(x: Int, y: Int, symbol: Char): Boolean = {
    if (isValidPosition(x, y)) {
      board(x)(y) = symbol
      notifyObservers()  // Benachrichtige Observer, wenn eine Figur gesetzt wird
      true
    } else {
      false
    }
  }

  /**
   * Bewegt die Spielfigur in eine bestimmte Richtung.
   * @param direction Die Richtung der Bewegung.
   * @return true, wenn die Bewegung erfolgreich war, andernfalls false.
   */
  def move(direction: String): Boolean = {
    val (newX, newY) = direction match {
      case "right" => (figureX, figureY + 1)
      case "left"  => (figureX, figureY - 1)
      case "up"    => (figureX - 1, figureY)
      case "down"  => (figureX + 1, figureY)
      case _       => return false
    }

    if (isValidPosition(newX, newY) && (board(newX)(newY) == '-' || board(newX)(newY) == 'Z')) {
      board(figureX)(figureY) = '-'
      figureX = newX
      figureY = newY
      board(figureX)(figureY) = 'F'
      notifyObservers()  // Benachrichtige Observer nach jeder gültigen Bewegung
      true
    } else {
      false
    }
  }

  /**
   * Gibt das Spielfeld zurück.
   * @return Das Spielfeld als 2D-Array von Zeichen.
   */
  def getBoard: Array[Array[Char]] = board

  /**
   * Überprüft, ob die Spielfigur das Ziel erreicht hat.
   * @return true, wenn die Spielfigur auf dem Ziel ist.
   */
  def isOnTarget: Boolean = figureX == targetX && figureY == targetY

  /**
   * Überprüft, ob eine Position im Spielfeld gültig ist.
   * @param x Die x-Koordinate.
   * @param y Die y-Koordinate.
   * @return true, wenn die Position gültig ist.
   */
  private def isValidPosition(x: Int, y: Int): Boolean = x >= 0 && x < size && y >= 0 && y < size
}

/**
 * Factory zur Erstellung von GameModel-Instanzen.
 */
object GameModelFactory {

  /**
   * Factory-Methode zur Erstellung eines GameModel-Objekts.
   * @param size Die Größe des Spielfeldes.
   * @return Eine Instanz von GameModel.
   */
  def createGameModel(size: Int): GameModel = {
    new GameModel(size)
  }
}
