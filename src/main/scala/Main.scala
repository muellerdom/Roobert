object Main {
  val model = new Model.GameModel()
  val view = new View.GameView()
  val controller = GameController.getInstance(model, view)

  // Aktuelle Spielphase
  var currentStage: GameStage = InitializationStage

  // Main-Methode, um das Spiel zu starten und durch die Phasen zu steuern
  def main(args: Array[String]): Unit = {
    // Initialisierung
    controller.startGame() // Startet das Spiel mit dem Template Method Pattern

    // Spielsteuerung durch die Phasen
    while (currentStage != GameEndStage) {
      currentStage match {
        case InitializationStage =>
          initializeGame()
        case UserInputStage =>
          promptForMove()
        case ProcessMoveStage =>
          processMove()
        case GameEndStage =>
          endGame()
      }
    }
  }

  // Spiel initialisieren und auf Benutzerinput umschalten
  def initializeGame(): Unit = {
    println("Spiel wird initialisiert...")
    controller.startGame() // Initialisierung durchführen
    currentStage = UserInputStage // Wechsel zur nächsten Phase: Benutzerinput
  }

  // Benutzer nach einer Bewegung fragen
  def promptForMove(): Unit = {
    println("Bitte gib 'right', 'down', 'up' oder 'left' ein, um die Figur zu bewegen:")
    currentStage = ProcessMoveStage // Wechsel zur nächsten Phase: Bewegungsverarbeitung
  }

  // Benutzereingabe verarbeiten
  def processMove(): Unit = {
    val input = scala.io.StdIn.readLine()

    if (controller.isValidMove(input)) {
      controller.moveCharacter(input) // Bewegung durchführen
      controller.updateView() // Ansicht aktualisieren
      if (controller.isLevelCompleted) {
        println("Hurrah! Das Ziel wurde erreicht!")
        currentStage = GameEndStage // Wechsel zur Endphase
      } else {
        currentStage = UserInputStage // Zurück zur Eingabeaufforderung
      }
    } else {
      println("Ungültiger Befehl. Bitte versuche es erneut.")
      currentStage = UserInputStage // Wieder zurück zur Eingabeaufforderung
    }
  }

  // Das Spiel beenden
  def endGame(): Unit = {
    println("Das Spiel ist zu Ende!")
  }
}
