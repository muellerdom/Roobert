package Controller.Component

import Model.FileIOComponent.FileIoJsonImpl.LevelConfig
import Model.GridComponent.{Coordinate, GridInterface}

trait ControllerInterface {

  // Startet ein Level anhand seines Namens
  def startLevel(levelName: Int): Either[String, LevelConfig]

  // Gibt eine Liste aller verfügbaren Level zurück
  def getAvailableLevels: List[String]

  // Führt einen Befehl aus
  def setCommand(action: String): Unit

  // Rückgängig machen
  def undo(): Unit

  // Wiederholen
  def redo(): Unit

  // Prüft, ob das Level abgeschlossen ist
  def isLevelComplete: Boolean

  // Gibt das aktuelle Spielfeld zurück
  def getGrid: GridInterface

  // Gibt die Konfiguration des aktuellen Levels zurück
  def getLevelConfig: Option[LevelConfig]
}
