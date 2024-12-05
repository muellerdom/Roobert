package Galgenmaennchen

import Util.Observable

/**
 * Singleton Galgenmaennchen Game Model
 * Repräsentiert das Spiel Galgenmännchen mit geheimem Wort, Fehlerzählung und Fortschritt.
 */
object Galgenmaennchen extends Observable {

  // Das geheime Wort des Spiels
  private var secretWord: String = ""

  // Maximale Anzahl erlaubter Fehlversuche
  private var maxGuesses: Int = 6

  // Die Buchstaben im geheimen Wort, die bereits korrekt geraten wurden
  private val guessedLetters = scala.collection.mutable.Set[Char]()

  // Zählt die Anzahl der falschen Versuche
  private var incorrectGuesses = 0

  /**
   * Initialisiert das Spiel mit einem neuen geheimen Wort und der maximalen Anzahl an Fehlversuchen.
   * @param word Das geheime Wort des Spiels.
   * @param maxFehlversuche Die maximal erlaubte Anzahl von Fehlversuchen.
   */
  def initialize(word: String, maxFehlversuche: Int = 6): Unit = {
    secretWord = word.toLowerCase
    maxGuesses = maxFehlversuche
    guessedLetters.clear()
    incorrectGuesses = 0
    notifyObservers()
  }

  /**
   * Überprüft, ob ein Buchstabe im geheimen Wort enthalten ist, und aktualisiert den Spielstatus.
   * @param buchstabe Der Buchstabe, der überprüft werden soll.
   * @return true, wenn der Buchstabe korrekt geraten wurde, false bei einem Fehlversuch.
   */
  def pruefeBuchstabe(buchstabe: Char): Boolean = {
    val lowerBuchstabe = buchstabe.toLower
    val isCorrect = if (secretWord.contains(lowerBuchstabe)) {
      guessedLetters += lowerBuchstabe
      true
    } else {
      incorrectGuesses += 1
      false
    }

    // Benachrichtige alle Observer nach der Überprüfung
    notifyObservers()

    isCorrect
  }

  /**
   * Überprüft, ob das Spiel gewonnen wurde.
   * @return true, wenn alle Buchstaben des geheimen Wortes korrekt geraten wurden.
   */
  def isGameWon: Boolean = {
    val won = secretWord.toSet.subsetOf(guessedLetters)
    if (won) notifyObservers()
    won
  }

  /**
   * Überprüft, ob das Spiel verloren wurde.
   * @return true, wenn die Anzahl der Fehlversuche das erlaubte Maximum erreicht oder überschritten hat.
   */
  def isGameLost: Boolean = {
    val lost = incorrectGuesses >= maxGuesses
    if (lost) notifyObservers()
    lost
  }

  /**
   * Gibt das aktuelle Wort mit Platzhaltern für ungeratene Buchstaben zurück.
   * @return Eine Zeichenkette, in der korrekt geratene Buchstaben angezeigt und ungeratene Buchstaben durch "_" ersetzt sind.
   */
  def displayGuessedWord: String = {
    secretWord.map { char =>
      if (guessedLetters.contains(char)) char else '_'
    }.mkString(" ")
  }

  /**
   * Gibt die Anzahl der verbleibenden Fehlversuche zurück.
   * @return Die Anzahl der verbleibenden Fehlversuche.
   */
  def guessesLeft: Int = maxGuesses - incorrectGuesses
}
