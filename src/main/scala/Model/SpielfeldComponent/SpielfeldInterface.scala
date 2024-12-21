package Model.SpielfeldComponent

trait SpielfeldInterface {
//  def hinsetze(x: Int, y: Int, value: Char): Unit       // Wert setzen
  def entfernen(x: Int, y: Int): Unit                  // Wert entfernen
  def getAnPos(x: Int, y: Int): Char                        // Wert abrufen
  def getSpielfeld: Array[Array[Char]]                     // Komplettes Grid abrufen
  def getSpielerPosition: Option[Coordinate]           // Spielerposition abrufen
  def setSpielerPosition(pos: Coordinate): Unit        // Spielerposition setzen

}
