package Model

import Util.Observable

class GameModel(size: Int) extends Observable {

  private val board: Array[Array[Char]] = Array.fill(size, size)('-')
  private var figureX: Int = 0
  private var figureY: Int = 0
  private val targetX: Int = 2
  private val targetY: Int = 0

  def initializeGame(): Unit = {
    placeFigure(figureX, figureY, 'F')
    placeFigure(targetX, targetY, 'Z')
    notifyObservers()  // Benachrichtige Observer nach der Initialisierung des Spiels
  }

  def placeFigure(x: Int, y: Int, symbol: Char): Boolean = {
    if (isValidPosition(x, y)) {
      board(x)(y) = symbol
      notifyObservers()  // Benachrichtige Observer, wenn eine Figur gesetzt wird
      true
    } else {
      false
    }
  }

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

  def getBoard: Array[Array[Char]] = board

  def isOnTarget: Boolean = figureX == targetX && figureY == targetY

  private def isValidPosition(x: Int, y: Int): Boolean = x >= 0 && x < size && y >= 0 && y < size
}
