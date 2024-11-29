package Model

import Util.Observable

// Abstrakte Klasse für TaschenInhalt
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

 // Methode zum Setzen des Objekts auf das Spielfeld
 override def aufsFeldSetzen(): Unit = {
  // Implementierung oder leer lassen, falls noch nicht definiert
 }
}

// Factory-Objekt zum Erstellen von TaschenInhalt-Objekten
object TaschenInhaltFactory {

 // Erstellen eines neuen TaschenInhalts: Süßigkeit, Brücke, Schlüssel oder andere Typen
 def createTaschenInhalt(name: String, groesse: Double, beschreibung: String, farbe: String, typ: String): TaschenInhalt = {
  typ match {
   case "Süßigkeit" => new Suessigkeit(name, groesse, beschreibung, farbe)
   case "Brücke"    => new Bruecke(name, groesse, beschreibung, farbe)
   case "Schlüssel" => new Schluessel(name, groesse, beschreibung, farbe)
   case _           => throw new IllegalArgumentException(s"Unbekannter Typ: $typ")
  }
 }
}

// Beispielhafte Implementierungen von TaschenInhalt (konkrete Subklassen)
class Suessigkeit(name: String, groesse: Double, beschreibung: String, farbe: String)
  extends TaschenInhalt(name, groesse, beschreibung, farbe)

class Bruecke(name: String, groesse: Double, beschreibung: String, farbe: String)
  extends TaschenInhalt(name, groesse, beschreibung, farbe)

class Schluessel(name: String, groesse: Double, beschreibung: String, farbe: String)
  extends TaschenInhalt(name, groesse, beschreibung, farbe)
