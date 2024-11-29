package Model

import Controller.{Coordinate, LevelConfig}
import Util.Observable

object Spieler extends Observable {

  // Die Position des Spielers
  var posX: Int = 0
  var posY: Int = 0
  val maxX: Int = 10
  val maxY: Int = 10

  sealed trait Direction
  case object Oben extends Direction
  case object Rechts extends Direction
  case object Unten extends Direction
  case object Links extends Direction

  var direction: Direction = Oben
  var eingesammelteJerms: Set[Coordinate] = Set()

  def move(action: String, level: LevelConfig): Unit = {
    action match {
      case "moveForward()" => moveForward(level)
      case "turnRight()" => turnRight()
      case "turnLeft()" => turnLeft()
      case _ =>
    }
  }

  def turnRight(): Unit = {
    direction = direction match {
      case Oben   => Rechts
      case Rechts => Unten
      case Unten  => Links
      case Links  => Oben
    }
    notifyObservers()
  }

  def turnLeft(): Unit = {
    direction = direction match {
      case Oben   => Links
      case Links  => Unten
      case Unten  => Rechts
      case Rechts => Oben
    }
    notifyObservers()
  }

  def moveForward(level: LevelConfig): Unit = {
    val newPos = direction match {
      case Oben    => (posX, posY - 1)
      case Rechts  => (posX + 1, posY)
      case Unten   => (posX, posY + 1)
      case Links   => (posX - 1, posY)
    }

    if (isValidMove(newPos._1, newPos._2, level)) {
      posX = newPos._1
      posY = newPos._2
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
        eingesammelteJerms += jerm
        println(s"Jerm an Position (${jerm.x}, ${jerm.y}) eingesammelt.")
        notifyObservers()
      case None =>
    }
  }

  override def toString: String = s"Model.Spieler($posX, $posY)"
}

// Factory für den Spieler
object SpielerFactory {

  // Factory-Methode zur Erstellung eines Spielers mit Startposition
  def createPlayer(startX: Int, startY: Int): Spieler.type = {
    Spieler.posX = startX
    Spieler.posY = startY
    Spieler.eingesammelteJerms = Set()
    Spieler.direction = Spieler.Oben // Optional: initiale Richtung
    Spieler
  }

  // Weitere Factory-Methode, die einen Spieler basierend auf Level-Konfiguration erstellt
  def createPlayerFromLevel(level: LevelConfig): Spieler.type = {
    // Hier könnte man auch eine Startposition aus der Level-Konfiguration ziehen
    createPlayer(0, 0)  // Beispiel: Erstelle den Spieler bei Position (0, 0)
  }
}
