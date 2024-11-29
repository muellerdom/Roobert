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

/**
 * Factory zur Erstellung von FreundJerm-Instanzen.
 */
object FreundJermFactory {

  /**
   * Factory-Methode zur Erstellung einer spezifischen FreundJerm-Instanz.
   * @param subclassName Der Name der konkreten Unterklasse, die erstellt werden soll.
   * @param name Der Name des FreundJerm-Objekts.
   * @return Eine Instanz von FreundJerm (konkrete Unterklasse).
   */
  def createFreundJerm(subclassName: String, name: String): FreundJerm = {
    subclassName match {
      case "BrueckenBauer" => new BrueckenBauer(name) // Beispiel einer konkreten Implementierung
      case "SuessigkeitenSammler" => new SuessigkeitenSammler(name) // Weitere Implementierung
      case _ =>
        throw new IllegalArgumentException(s"Unbekannte Unterklasse: $subclassName")
    }
  }
}

/**
 * Konkrete Implementierung von FreundJerm: Brückenbauer.
 */
class BrueckenBauer(name: String) extends FreundJerm(name) {
  override def ObjektErstellen(): Unit = {
    println(s"$name erstellt eine Brücke.")
  }

  override def aufsFeldSetzen(): Unit = {
    println(s"$name setzt die Brücke auf das Spielfeld.")
  }
}

/**
 * Konkrete Implementierung von FreundJerm: Süßigkeiten-Sammler.
 */
class SuessigkeitenSammler(name: String) extends FreundJerm(name) {
  override def ObjektErstellen(): Unit = {
    println(s"$name erstellt eine Süßigkeit.")
  }

  override def aufsFeldSetzen(): Unit = {
    println(s"$name setzt die Süßigkeit auf das Spielfeld.")
  }
}
