package GalgenmaennchenLevel1

import Util.Observable

class Galgenmaennchen private(secretWord: String, maxGuesses: Int = 6) extends Observable {

  // Die Buchstaben im geheimen Wort, die bereits korrekt geraten wurden
  private val guessedLetters = scala.collection.mutable.Set[Char]()

  // Zählt die Anzahl der falschen Versuche
  private var incorrectGuesses = 0

  // Die Singleton-Instanz
  private object Singleton {
    var instance: Option[Galgenmaennchen] = None
  }

  /**
   * Überprüft, ob ein Buchstabe im geheimen Wort enthalten ist, und aktualisiert den Spielstatus.
   * @param buchstabe Der Buchstabe, der überprüft werden soll.
   * @return true, wenn der Buchstabe korrekt geraten wurde, false bei einem Fehlversuch.
   */
  def pruefeBuchstabeOeffentlich(buchstabe: Char): Boolean = {
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
    val won = secretWord.toLowerCase.toSet.subsetOf(guessedLetters)
    // Benachrichtige alle Observer, wenn das Spiel gewonnen wurde
    if (won) notifyObservers()
    won
  }

  /**
   * Überprüft, ob das Spiel verloren wurde.
   * @return true, wenn die Anzahl der Fehlversuche das erlaubte Maximum erreicht oder überschritten hat.
   */
  def isGameLost: Boolean = {
    val lost = incorrectGuesses >= maxGuesses
    // Benachrichtige alle Observer, wenn das Spiel verloren wurde
    if (lost) notifyObservers()
    lost
  }

  /**
   * Gibt das aktuelle Wort mit Platzhaltern für ungeratene Buchstaben zurück.
   * @return Eine Zeichenkette, in der korrekt geratene Buchstaben angezeigt und ungeratene Buchstaben durch "_" ersetzt sind.
   */
  def displayGuessedWord: String = {
    secretWord.map { char =>
      if (guessedLetters.contains(char.toLower)) char else '_'
    }.mkString(" ")
  }

  /**
   * Gibt die Anzahl der verbleibenden Fehlversuche zurück.
   * @return Die Anzahl der verbleibenden Fehlversuche.
   */
  def guessesLeft: Int = maxGuesses - incorrectGuesses

  // Die Singleton-Instanz erhalten
  def getInstance(secretWord: String, maxGuesses: Int = 6): Galgenmaennchen = {
    if (Singleton.instance.isEmpty) {
      Singleton.instance = Some(new Galgenmaennchen(secretWord, maxGuesses))
    }
    Singleton.instance.get
  }
}
