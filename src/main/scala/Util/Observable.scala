package Util

// Die Observable-Klasse, die die Observer verwaltet
trait Observable {
  // Liste der Abonnenten (Observer)
  var subscribers: Vector[Observer] = Vector()

  // Methode zum Hinzufügen eines Observer
  def addObserver(s: Observer): Unit = {
    subscribers = subscribers :+ s
  }

  // Methode zum Entfernen eines Observer
  def remove(s: Observer): Unit = {
    subscribers = subscribers.filterNot(o => o == s)
  }

  // Methode, um alle Observer zu benachrichtigen
  def notifyObservers(): Unit = {
    subscribers.foreach(o => o.update())  // rufe update() mit Klammern auf
  }
}

// Das Observer-Trait, das von allen konkreten Observern implementiert werden muss
trait Observer {
  def update(): Unit  // Methode, die von konkreten Observern überschrieben wird
}
