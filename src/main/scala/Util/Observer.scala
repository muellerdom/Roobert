package Util

trait Observer {
  def update: Boolean
}

class Observable {
  var subscribers: Vector[Observer] = Vector()

  def addObserver(s: Observer): Unit = subscribers = subscribers :+ s

  def remove(s: Observer): Unit = subscribers = subscribers.filterNot(o => o == s)

  def notifyObservers: Unit = subscribers.foreach(o => o.update)


//class GameObserver extends Observer {
//  override def update: Unit = {
//    println("Das Spielfeld wurde aktualisiert!")
//  }
}
