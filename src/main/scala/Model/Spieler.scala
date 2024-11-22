package Model

import Controller.{Coordinate, LevelConfig}

class Spieler(var posX: Int, var posY: Int, val maxX: Int, val maxY: Int) {

  // Enum-like sealed trait for directions
  sealed trait Direction
  case object Oben extends Direction
  case object Rechts extends Direction
  case object Unten extends Direction
  case object Links extends Direction

  var direction: Direction = Rechts
  var eingesammelteJerms: Set[Coordinate] = Set()

  // Move method to handle different actions
  def move(action: String, level: LevelConfig): Unit = {
    action match {
      case "moveForward()" => moveForward(level)
      case "turnRight()" => turnRight()
      case "turnLeft()" => turnLeft()
      case _ => // No action for unknown commands
    }
  }

  // Turn right method
  def turnRight(): Unit = {
    direction = direction match {
      case Oben   => Rechts
      case Rechts => Unten
      case Unten  => Links
      case Links  => Oben
    }
  }

  // Turn left method
  def turnLeft(): Unit = {
    direction = direction match {
      case Oben   => Links
      case Links  => Unten
      case Unten  => Rechts
      case Rechts => Oben
    }
  }

  // Move forward method based on direction with boundary and obstacle checks
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
    }
  }

  // Check if the new move is within bounds and not an obstacle
  private def isValidMove(x: Int, y: Int, level: LevelConfig): Boolean = {
    val withinBounds = x >= 0 && x < maxX && y >= 0 && y < maxY
    val isObstacle = level.objects.obstacles.exists(obs => obs.coordinates.x == x && obs.coordinates.y == y)
    withinBounds && !isObstacle
  }

  // Collect jerms
  private def einsammeln(pos: (Int, Int), level: LevelConfig): Unit = {
    level.objects.jerm.find(jerm => jerm.x == pos._1 && jerm.y == pos._2) match {
      case Some(jerm) =>
        eingesammelteJerms += jerm
        println(s"Jerm an Position (${jerm.x}, ${jerm.y}) eingesammelt.")

      case None => // No jerm at this position
    }
  }


  // String representation for testing
  override def toString: String = s"Model.Spieler($posX, $posY)"
}