package Model

import Util.Observable

/**
 * Abstrakte Klasse für FreundJerm.
 * Repräsentiert ein spielbares Objekt mit Sammel- und Erstellungsfähigkeiten.
 */
abstract class FreundJerm(val name: String) extends Observable {

  // Liste der eingesammelten Objekte
  var eingesammelteObjekte: List[FreundJerm] = List()

  /**
   * Einsammeln eines Objekts.
   * @param objekt Das Objekt, das eingesammelt werden soll.
   */
  def einsammeln(objekt: FreundJerm): Unit = {
    if (!eingesammelteObjekte.contains(objekt)) {
      eingesammelteObjekte = eingesammelteObjekte :+ objekt
      println(s"$name hat das Objekt '${objekt.name}' eingesammelt.")

      // Benachrichtige alle Observer
      notifyObservers()
    } else {
      println(s"$name hat das Objekt '${objekt.name}' schon eingesammelt.")
    }
  }

  // Abstrakte Methode: Erstellung eines Objekts (z. B. Brücke oder Süßigkeit)
  def ObjektErstellen(): Unit

  // Abstrakte Methode: Objekt auf das Spielfeld setzen
  def aufsFeldSetzen(): Unit
}



