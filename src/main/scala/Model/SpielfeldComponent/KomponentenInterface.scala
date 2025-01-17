package Model.SpielfeldComponent


// Trait für die Komponenten des Spielfelds
trait KomponentenInterface {

  def getPosition: Coordinate // Gibt die Position zurück

  def Symbol: Char // Gibt das Symbol der Komponente zurück

  def isBlocking: Boolean // Gibt an, ob die Komponente den Spieler blockiert

  def interactWithPlayer(): Unit // Logik für die Interaktion mit dem Spieler

}
