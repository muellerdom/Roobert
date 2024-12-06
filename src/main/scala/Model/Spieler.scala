/*package Model

import Controller.{Coordinate, LevelConfig}
import Util.Observable

// Die Case Class für den Zustand des Spielers
case class SpielerState(posX: Int, posY: Int, direction: Direction, eingesammelteJerms: Set[Coordinate])

sealed trait Direction
case object Oben extends Direction
case object Rechts extends Direction
case object Unten extends Direction
case object Links extends Direction

// Singleton für den Spieler
object Spieler extends Observable {

  // Die private Instanz des Spielers (nur eine Instanz existiert)
  private var instance: Option[SpielerState] = None

  val maxX: Int = 10
  val maxY: Int = 10

  // Getter für die Instanz
  def getInstance: SpielerState = {
    if (instance.isEmpty) {
      // Initialisierung der Instanz, wenn sie noch nicht existiert
      instance = Some(SpielerState(0, 0, Oben, Set()))
    }
    instance.get // Gibt die einzige Instanz zurück
  }

  // Initialisierung des Spielers
  def initializePlayer(startX: Int, startY: Int): Unit = {
    val currentState = getInstance
    instance = Some(currentState.copy(posX = startX, posY = startY, direction = Oben, eingesammelteJerms = Set()))
    notifyObservers()
  }

  def move(action: String, level: LevelConfig): Unit = {
    action match {
      case "moveForward()" => moveForward(level)
      case "turnRight()" => turnRight()
      case "turnLeft()" => turnLeft()
      case _ =>
    }
  }

  def turnRight(): Unit = {
    val currentState = getInstance
    val newDirection = currentState.direction match {
      case Oben   => Rechts
      case Rechts => Unten
      case Unten  => Links
      case Links  => Oben
    }
    instance = Some(currentState.copy(direction = newDirection))
    notifyObservers()
  }

  def turnLeft(): Unit = {
    val currentState = getInstance
    val newDirection = currentState.direction match {
      case Oben   => Links
      case Links  => Unten
      case Unten  => Rechts
      case Rechts => Oben
    }
    instance = Some(currentState.copy(direction = newDirection))
    notifyObservers()
  }

  def moveForward(level: LevelConfig): Unit = {
    val currentState = getInstance
    val newPos = currentState.direction match {
      case Oben    => (currentState.posX, currentState.posY - 1)
      case Rechts  => (currentState.posX + 1, currentState.posY)
      case Unten   => (currentState.posX, currentState.posY + 1)
      case Links   => (currentState.posX - 1, currentState.posY)
    }

    if (isValidMove(newPos._1, newPos._2, level)) {
      val updatedState = currentState.copy(posX = newPos._1, posY = newPos._2)
      instance = Some(updatedState)
      einsammeln(newPos, level)
      notifyObservers()
    }
  }

  private def isValidMove(x: Int, y: Int, level: LevelConfig): Boolean = {
    val withinBounds = x >= 0 && x < maxX && y >= 0 && y < maxY
    val isObstacle = level.objects.obstacles.exists(obs => obs.coordinates.x == x && obs.coordinates.y == y)
    withinBounds && !isObstacle
  }

  private def einsammeln(pos: (Int, Int), level: LevelConfig): Unit = {
    level.objects.jerm.find(jerm => jerm.x == pos._1 && jerm.y == pos._2) match {
      case Some(jerm) =>
        val currentState = getInstance
        val updatedJerms = currentState.eingesammelteJerms + jerm
        instance = Some(currentState.copy(eingesammelteJerms = updatedJerms))
        println(s"Jerm an Position (${jerm.x}, ${jerm.y}) eingesammelt.")
        notifyObservers()
      case None =>
    }
  }

  override def toString: String = s"Model.Spieler(${getInstance.posX}, ${getInstance.posY})"
}*/
