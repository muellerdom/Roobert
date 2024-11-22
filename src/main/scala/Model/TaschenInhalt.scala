package Model

import java.lang.Object

abstract class TaschenInhalt(val name: String, val groesse: Double, val beschreibung: String, val farbe: String) extends Objekt {
 var eingesammelteObjekte: List[TaschenInhalt] = List()

  //falls es der richtige Gegenstand ist, kann man es einsammeln
  // Implementierung der Methode aus dem Interface
  def einsammeln(objekt: Objekt): Unit = {

   objekt match {
    case obj: TaschenInhalt =>
     if (!eingesammelteObjekte.contains(obj)) {
      eingesammelteObjekte = eingesammelteObjekte :+ obj
      println(s"$name hat das Objekt '${obj.name}' eingesammelt.")
     } else {
      println(s"$name hat das Objekt '${obj.name}' schon eingesammelt.")
     }
    case _ =>
     println(s"$name kann dieses Objekt nicht einsammeln.")
   }
  }



  //wir erstellen ein Objekt, sei es eine Brücke, eine Süßigkeit oder ähnliches.
 // def ObjektErstellen():Unit
  override def aufsFeldSetzen(): Unit = {
   // Implementierung oder leer lassen, falls noch nicht definiert
  }





}
