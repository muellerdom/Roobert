package Model

import scala.language.postfixOps


/** *
 * Grundsätzlich kann man sich überlegen ein Interface zu schreiben oder Vererbung anzuwenden
 */

trait Objekt {


  val name: String
  val groesse: Double
  val beschreibung: String
  val farbe: String
  val eingesammelte: List[TaschenInhalt] = List()

  

  /*
  * sachen, die in Roberts Tasche reinpassen.
  *
  * */
//wie interagiert Robert mit den Sachen in seiner Tasche


  //falls es der richtige Gegenstand ist, kann man es einsammeln
  def einsammeln(objekt: Objekt): Unit



  //wir erstellen ein Objekt, sei es eine Brücke, eine Süßigkeit oder ähnliches.
  //def ObjektErstellen():Unit

  //aufs ffeld setzen
  def aufsFeldSetzen (): Unit






}



