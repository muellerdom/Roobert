package Model

import scala.language.postfixOps

/**
 * Grundsätzlich kann man sich überlegen, ein Interface zu schreiben oder Vererbung anzuwenden.
 */
trait Objekt {

  val name: String
  val groesse: Double
  val beschreibung: String
  val farbe: String
  val eingesammelte: List[TaschenInhalt] = List()

  /*
  * Dinge, die in Roberts Tasche reinpassen.
  *
  * Wie interagiert Robert mit den Sachen in seiner Tasche?
  */

  // Falls es der richtige Gegenstand ist, kann man ihn einsammeln
  def einsammeln(objekt: Objekt): Unit

  // Das Objekt wird aufs Spielfeld gesetzt
  def aufsFeldSetzen(): Unit
}
