package Model.PlayerComponent.PlayerBaseImpl

import Model.GridComponent.{Coordinate, GridSegmentsInterface}
import Model.PlayerComponent.PlayerInterface

// Enum for the direction the player is facing
sealed trait Direction
case object Up extends Direction
case object Right extends Direction
case object Down extends Direction
case object Left extends Direction

case class Player(
                   position: Coordinate,
                   direction: Direction = Up,
                   inventory: Inventory = Inventory()
                 ) extends GridSegmentsInterface with PlayerInterface {

  // Symbol representing the player on the grid
  val symbol: Char = 'R'

  /**
   * Performs a movement based on the given action string.
   * Supported actions: "forward", "right", "left".
   * Returns a new Player instance with the updated state.
   */
  override def move(action: String): Player = action match {
    case "forward" => moveForward()
    case "right"   => turnRight()
    case "left"    => turnLeft()
    case _         => this
  }

  /**
   * Moves the player forward based on their current direction.
   * Returns a new Player instance with the updated position.
   */
  private def moveForward(): Player = {
    val newPosition = direction match {
      case Up    => position.copy(y = position.y + 1)
      case Down  => position.copy(y = position.y - 1)
      case Left  => position.copy(x = position.x - 1)
      case Right => position.copy(x = position.x + 1)
    }
    copy(position = newPosition)
  }

  /**
   * Turns the player to the right.
   * Updates the player's direction clockwise (e.g., Up -> Right).
   */
  private def turnRight(): Player = {
    val newDirection = direction match {
      case Up    => Right
      case Right => Down
      case Down  => Left
      case Left  => Up
    }
    copy(direction = newDirection)
  }

  /**
   * Turns the player to the left.
   * Updates the player's direction counterclockwise (e.g., Up -> Left).
   */
  private def turnLeft(): Player = {
    val newDirection = direction match {
      case Up    => Left
      case Left  => Down
      case Down  => Right
      case Right => Up
    }
    copy(direction = newDirection)
  }

  // Implements required methods from interfaces
  override def getPosition: Coordinate = position
  override def Symbol: Char = symbol
  override def segmentType: String = "player"
  override def subType: Option[String] = None

  override def isBlocking: Boolean = false

  override def interactWithPlayer(): Unit = ???
}

/**
 * Inventory for managing collected entities/items.
 */
case class Inventory(items: Set[Coordinate] = Set()) {

  /**
   * Adds a new item to the inventory and returns a new updated Inventory.
   */
  def addItem(position: Coordinate): Inventory = copy(items = items + position)

  /**
   * Gets the total number of collected items.
   */
  def size: Int = items.size
}