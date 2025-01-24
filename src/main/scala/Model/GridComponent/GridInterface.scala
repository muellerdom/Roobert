package Model.GridComponent

import Model.GridComponent.GridBaseImpl.GridSegments


trait GridInterface {
  def remove(coordinate: Coordinate): Unit                  // Wert entfernen
  def getAnPos(coordinate: Coordinate): Char                        // Wert abrufen
  def getGrid: Option[GridSegments]              // Komplettes Grid abrufen
  def gridSegments: GridSegments     // Zugriff auf alle Segmente

  def getPlayerPosition: Option[Coordinate]           // Spielerposition abrufen
  def setPlayerPosition(pos: Coordinate): Unit        // Spielerposition setzen

}
