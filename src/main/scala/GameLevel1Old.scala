package Game

abstract class GameTemplate {

  // Template-Methode
  def startGame(): Unit = {
    initializeGame()
    setupBoard()
    startPlaying()
    endGame()
  }

  // Abstrakte Methoden, die von den konkreten Klassen implementiert werden müssen
  protected def initializeGame(): Unit
  protected def setupBoard(): Unit
  protected def startPlaying(): Unit
  protected def endGame(): Unit
}
