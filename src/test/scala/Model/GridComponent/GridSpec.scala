package Model.GridComponent

import Model.GridComponent.GridBaseImpl.{Goal, Grid, GridSegments, Jerm, Obstacle, emptyField}
import Model.GridComponent.{Coordinate, GridInterface}
import Model.PlayerComponent.PlayerBaseImpl.Player
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.mockito.Mockito._
import org.mockito.MockitoSugar.mock

class GridSpec extends AnyWordSpec with Matchers {

  "A Grid" should {

    "return the player's current position" in {
      // Mock components of the grid
      val player = Player(Coordinate(1, 1))
      val gridSegments = GridSegments(List(
        player,
        emptyField(Coordinate(0, 0)),
        new Obstacle(Coordinate(2, 2), "wall")
      ))
      val grid = Grid(gridSegments)

      grid.getPlayerPosition should be(Some(Coordinate(1, 1)))
    }

    "return None if there is no player in the grid" in {
      val gridSegments = GridSegments(List(
        emptyField(Coordinate(0, 0)),
        new Obstacle(Coordinate(2, 2), "wall")
      ))
      val grid = Grid(gridSegments)

      grid.getPlayerPosition should be(None)
    }

    "successfully move the player to an empty field" in {
      val player = Player(Coordinate(1, 1))
      val gridSegments = GridSegments(List(
        player,
        emptyField(Coordinate(1, 2))
      ))
      val grid = Grid(gridSegments)

      val updatedGrid = grid.movePlayer("forward") // Assuming "right" maps to (2, 1)

      updatedGrid.getPlayerPosition should be(Some(Coordinate(1, 2)))
    }

    "block the player's movement when an obstacle is present" in {
      val player = Player(Coordinate(1, 1))
      val obstacle = new Obstacle(Coordinate(2, 1), "wall")
      val gridSegments = GridSegments(List(
        player,
        obstacle
      ))
      val grid = Grid(gridSegments)

      val updatedGrid = grid.movePlayer("right") // Assuming "right" maps to (2, 1)

      updatedGrid.getPlayerPosition should be(Some(Coordinate(1, 1)))
    }

    "handle collecting a Jerm entity" in {
      val player = Player(Coordinate(1, 1))
      val jerm = new Jerm(Coordinate(1, 2))
      val gridSegments = GridSegments(List(
        player,
        jerm
      ))
      val grid = Grid(gridSegments)

      val updatedGrid = grid.movePlayer("forward") // Assuming "right" maps to (2, 1)

      // The player collects the Jerm
      updatedGrid.getPlayerPosition should be(Some(Coordinate(1, 2)))
      // Optionally verify inventory updates if inventory handling is implemented
    }

    "trigger goal completion when reaching the goal segment" in {
      val player = Player(Coordinate(1, 1))
      val goal = Goal(Coordinate(1, 2))
      val gridSegments = GridSegments(List(
        player,
        goal
      ))
      val grid = Grid(gridSegments)

      val updatedGrid = grid.movePlayer("forward") // Assuming "right" maps to (2, 1)

      updatedGrid.getPlayerPosition should be(Some(Coordinate(1, 2)))
      println("Herzlichen Glückwunsch! Du hast das Ziel erreicht!")
      // Additional logic would go here to verify game completion behavior
    }

    "throw an exception if no player is present when trying to move" in {
      val gridSegments = GridSegments(List(
        emptyField(Coordinate(0, 0)),
        new Obstacle(Coordinate(2, 2), "wall")
      ))
      val grid = Grid(gridSegments)

      val exception = intercept[NoSuchElementException] {
        grid.movePlayer("anyDirection")
      }
      exception.getMessage should be("Kein Spieler im Grid gefunden!")
    }

    "leave the grid unchanged when moving outside its bounds" in {
      val player = Player(Coordinate(1, 1))
      val gridSegments = GridSegments(List(
        player,
        emptyField(Coordinate(2, 1))
      ))
      val grid = Grid(gridSegments)

      val updatedGrid = grid.movePlayer("up") // Assuming "up" moves out of bounds

      updatedGrid.getPlayerPosition should be(Some(Coordinate(1, 1)))
    }
  }
}