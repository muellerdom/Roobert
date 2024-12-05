package Util

// Observer-Trait
trait Observer {
  // Jede konkrete Implementierung von Observer muss die update-Methode überschreiben
  def update(): Unit
}

// Abstrakte Observable-Klasse mit Template-Methode
abstract class AbstractObservable {
  private var observers: List[Observer] = List()

  // Template-Methode: Definiert den allgemeinen Ablauf für die Benachrichtigung der Observer
  def notifyObservers(): Unit = {
    // Vorbereitungen für die Benachrichtigung (kann von Unterklassen angepasst werden)
    println("Benachrichtigung der Observer beginnt...")

    // Ruft die abstrakte Methode auf, um den spezifischen Benachrichtigungsprozess zu starten
    performNotification()

    // Benachrichtigt alle Observer
    observers.foreach(_.update())

    // Nachbereitungen für die Benachrichtigung (kann von Unterklassen angepasst werden)
    println("Benachrichtigung der Observer abgeschlossen.")
  }

  // Diese Methode kann von Unterklassen überschrieben werden, um spezifische Logik für die Benachrichtigung hinzuzufügen
  protected def performNotification(): Unit = {
    // Hier könnte eine spezifische Logik hinzugefügt werden, z.B. Loggen der Benachrichtigung oder Überprüfen von Bedingungen
    println("Spezifische Benachrichtigungsvorbereitung.")
  }

  // Fügt einen Observer hinzu
  def addObserver(observer: Observer): Unit = {
    observers = observers :+ observer
  }

  // Entfernt einen Observer
  def removeObserver(observer: Observer): Unit = {
    observers = observers.filterNot(_ == observer)
  }
}

// Konkrete Observable-Klasse
class ConcreteObservable extends AbstractObservable {
  // Hier könnte weitere spezifische Logik hinzugefügt werden
  // Die Template-Methode `notifyObservers` wird hier verwendet und aufgerufen
}

// Beispiel einer konkreten Observer-Klasse
class ConcreteObserver(name: String) extends Observer {
  def update(): Unit = {
    println(s"Observer $name wurde benachrichtigt!")
  }
}
