package Model.PlayerComponent

import Model.GridComponent.Coordinate
import Model.PlayerComponent.PlayerBaseImpl.Player

trait PlayerInterface {

  /**
   * Returns the current position of the player.
   */
  def getPosition: Coordinate

  /**
   * Performs a movement based on an action (e.g., "forward", "right").
   * Returns a new Player instance.
   */
  def move(action: String): Player
}