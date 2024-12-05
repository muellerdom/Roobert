package Model

import Util.Observable

// Abstrakte Klasse für TaschenInhalt
abstract class TaschenInhalt(val name: String, val groesse: Double, val beschreibung: String, val farbe: String) extends Observable {
 var eingesammelteObjekte: List[TaschenInhalt] = List()

 // Methode zum Einsammeln eines Objekts
 def einsammeln(objekt: TaschenInhalt): Unit = {
  if (!eingesammelteObjekte.contains(objekt)) {
   eingesammelteObjekte = eingesammelteObjekte :+ objekt
   println(s"${name} hat das Objekt '${objekt.name}' eingesammelt.")
   notifyObservers()  // Benachrichtige alle Beobachter
  } else {
   println(s"${name} hat das Objekt '${objekt.name}' bereits eingesammelt.")
  }
 }

 // Weitere Logik für das Setzen des Objekts auf das Spielfeld
 def aufsFeldSetzen(): Unit = {
  // Implementierung für das Setzen auf das Spielfeld, wenn nötig
 }
}

// Singleton für Suessigkeit
object Suessigkeit extends TaschenInhalt("Suessigkeit", 10.5, "Eine leckere Suessigkeit", "Bunt")

// Singleton für Bruecke
object Bruecke extends TaschenInhalt("Bruecke", 50.0, "Eine Bruecke, die ueber ein Hindernis fuehrt", "Grau")

// Singleton für Schluessel
object Schluessel extends TaschenInhalt("Schluessel", 2.0, "Ein Schluessel, der eine Tuer oeffnet", "Silber")


Suessigkeit.einsammeln(Bruecke)  // Beispiel für das Einsammeln eines Objekts
