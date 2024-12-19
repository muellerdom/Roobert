package Controller.Component.ControllerBaseImpl

// Definieren von verschiedenen Phasen des Spiels
//trait SpielStatus

object SpielStatus extends Enumeration {
  type SpielStatus = Value
  val InitializationStage, UserInputStage, ProcessMoveStage, GameEndStage = Value

  val map: Map[SpielStatus, String] = Map[SpielStatus, String](
    InitializationStage -> "Level erfolgreich initialisiert",
    UserInputStage -> "Code entgegengenommen",
    ProcessMoveStage -> "Spieler bewegt",
    GameEndStage -> "Robert ist angekommen!")

  def message(gameStatus: SpielStatus): String = {
    map(gameStatus)
  }

}
