package Model.GridComponent


// Trait für die Komponenten des Spielfelds
trait GridSegmentsInterface {

  def getPosition: Coordinate // Gibt die Position zurück

  def Symbol: Char // Gibt das Symbol der Komponente zurück

  def segmentType: String

  def subType: Option[String]

  def isBlocking: Boolean // Gibt an, ob die Komponente den Spieler blockiert

  def interactWithPlayer(): Unit // Logik für die Interaktion mit dem Spieler

}
