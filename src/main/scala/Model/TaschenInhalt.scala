package Model

import Util.Observable

abstract class TaschenInhalt(val name: String, val groesse: Double, val beschreibung: String, val farbe: String) extends Objekt with Observable {

 // Eine Liste von eingesammelten Objekten
 var eingesammelteObjekte: List[TaschenInhalt] = List()

 // Falls es der richtige Gegenstand ist, kann man es einsammeln
 def einsammeln(objekt: Objekt): Unit = {
  objekt match {
   case obj: TaschenInhalt =>
    if (!eingesammelteObjekte.contains(obj)) {
     eingesammelteObjekte = eingesammelteObjekte :+ obj
     println(s"$name hat das Objekt '${obj.name}' eingesammelt.")  // Diese Nachricht könnte in der View angezeigt werden

     // Benachrichtige alle registrierten Beobachter
     notifyObservers()  // Benachrichtige alle Beobachter über die Änderung
    } else {
     println(s"$name hat das Objekt '${obj.name}' schon eingesammelt.")
    }
   case _ =>
    println(s"$name kann dieses Objekt nicht einsammeln.")
  }
 }

 // Wir erstellen ein Objekt, sei es eine Brücke, eine Süßigkeit oder ähnliches.
 // Diese Methode ist momentan auskommentiert, aber sie könnte zur weiteren Funktionalität dienen
 // def ObjektErstellen(): Unit = {
 //   // Logik zum Erstellen eines Objekts
 // }

 // Methode zum Setzen des Objekts auf das Spielfeld
 override def aufsFeldSetzen(): Unit = {
  // Implementierung oder leer lassen, falls noch nicht definiert
 }
}
