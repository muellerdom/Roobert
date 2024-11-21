package Model

class Spieler(var posX: Int, var posY: Int) {

  // Enum-like sealed trait for directions
  sealed trait Direction
  case object Oben extends Direction
  case object Rechts extends Direction
  case object Unten extends Direction
  case object Links extends Direction

  var direction: Direction = Oben

  // Move method to handle different actions
  def move(action: String): Unit = {
    action match {
      case "moveForward()" => moveForward()
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

  // Move forward method based on direction
  def moveForward(): Unit = {
    direction match {
      case Oben    => posY -= 1 // Moving up decreases Y
      case Rechts  => posX += 1 // Moving right increases X
      case Unten   => posY += 1 // Moving down increases Y
      case Links   => posX -= 1 // Moving left decreases X
    }
  }

  // String representation for testing
  override def toString: String = s"Model.Spieler($posX, $posY)"
}
