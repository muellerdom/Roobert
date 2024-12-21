package Controller.Component

import Model.LevelComponent.LevelConfig
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}

trait ControllerInterface {

  // Startet ein Level anhand seines Namens
  def startLevel(levelName: String): Either[String, LevelConfig]

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
  def getSpielfeld: SpielfeldInterface

  // Gibt die Konfiguration des aktuellen Levels zurück
  def getLevelConfig: Option[LevelConfig]
}
