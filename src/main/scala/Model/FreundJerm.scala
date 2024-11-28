package Model

import Util.Observable

abstract class FreundJerm(val name: String) extends Observable {

  // Liste der eingesammelten Objekte
  var eingesammelteObjekte: List[FreundJerm] = List()

  // Wenn es der richtige Gegenstand ist, kann man ihn einsammeln
  def einsammeln(objekt: FreundJerm): Unit = {
    if (!eingesammelteObjekte.contains(objekt)) {
      eingesammelteObjekte = eingesammelteObjekte :+ objekt
      println(s"$name hat das Objekt '${objekt.name}' eingesammelt.") // Diese Nachricht sollte eigentlich in der View erscheinen

      // Benachrichtige alle Observer, dass das Objekt eingesammelt wurde
      notifyObservers()  // Diese Zeile sorgt dafür, dass alle registrierten Observer benachrichtigt werden
    } else {
      println(s"$name hat das Objekt '${objekt.name}' schon eingesammelt.")
    }
  }

  // Methode, um ein Objekt zu erstellen (z. B. eine Brücke oder Süßigkeit)
  def ObjektErstellen(): Unit

  // Methode, um das Objekt auf das Spielfeld zu setzen
  def aufsFeldSetzen(): Unit
}
