package Model

abstract class FreundJerm extends Objekt {



  //falls es der richtige Gegenstand ist, kann man es einsammeln

  /*
  *
  * */
  var eingesammelteObjekte: List[FreundJerm] = List() // Eine Liste von eingesammelten Objekten


  //falls es der richtige Gegenstand ist, kann man es einsammeln
  def einsammeln(objekt: FreundJerm): Unit =
    if (!eingesammelteObjekte.contains(objekt)) {
      eingesammelteObjekte = eingesammelteObjekte :+ objekt
      println(s"$name hat das Objekt '${objekt.name}' eingesammelt.") //sollte in view
    } else {
      println(s"$name hat das Objekt '${objekt.name}' schon eingesammelt.")
    }


  //wir erstellen ein Objekt, sei es eine Brücke, eine Süßigkeit oder ähnliches.
  def ObjektErstellen(): Unit

  //aufs ffeld setzen
  def aufsFeldSetzen(): Unit


}
