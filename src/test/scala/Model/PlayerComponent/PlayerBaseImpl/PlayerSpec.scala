package Model.PlayerComponent.PlayerBaseImpl

import Model.GridComponent.Coordinate
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {

  "A Player" should {

    "move forward correctly based on direction" in {
      // Test cases for each possible direction
      val playerUp = Player(Coordinate(0, 0), Up)
      val playerRight = Player(Coordinate(0, 0), Right)
      val playerDown = Player(Coordinate(0, 0), Down)
      val playerLeft = Player(Coordinate(0, 0), Left)

      playerUp.move("forward").position should be(Coordinate(0, 1))
      playerRight.move("forward").position should be(Coordinate(1, 0))
      playerDown.move("forward").position should be(Coordinate(0, -1))
      playerLeft.move("forward").position should be(Coordinate(-1, 0))
    }

    "turn to the right correctly" in {
      // Changing direction clockwise
      val playerUp = Player(Coordinate(0, 0), Up)
      playerUp.move("right").direction should be(Right)

      val playerRight = Player(Coordinate(0, 0), Right)
      playerRight.move("right").direction should be(Down)

      val playerDown = Player(Coordinate(0, 0), Down)
      playerDown.move("right").direction should be(Left)

      val playerLeft = Player(Coordinate(0, 0), Left)
      playerLeft.move("right").direction should be(Up)
    }

    "turn to the left correctly" in {
      // Changing direction counterclockwise
      val playerUp = Player(Coordinate(0, 0), Up)
      playerUp.move("left").direction should be(Left)

      val playerLeft = Player(Coordinate(0, 0), Left)
      playerLeft.move("left").direction should be(Down)

      val playerDown = Player(Coordinate(0, 0), Down)
      playerDown.move("left").direction should be(Right)

      val playerRight = Player(Coordinate(0, 0), Right)
      playerRight.move("left").direction should be(Up)
    }

    "ignore invalid actions" in {
      val player = Player(Coordinate(0, 0), Up)

      val result = player.move("invalidAction")
      result.position should be(Coordinate(0, 0))
      result.direction should be(Up)
    }
  }

  "A Player's Inventory" should {

    "store items and retrieve their count" in {
      val inventory = Inventory()

      val updatedInventory = inventory.addItem(Coordinate(1, 1))
      updatedInventory.items should contain(Coordinate(1, 1))
      updatedInventory.size should be(1)

      // Add another item
      val furtherUpdatedInventory = updatedInventory.addItem(Coordinate(2, 2))
      furtherUpdatedInventory.items should contain allOf (Coordinate(1, 1), Coordinate(2, 2))
      furtherUpdatedInventory.size should be(2)
    }

    "not allow duplicate items" in {
      val inventory = Inventory(Set(Coordinate(1, 1)))

      val updatedInventory = inventory.addItem(Coordinate(1, 1))
      updatedInventory.items.size should be(1) // Item should not be duplicated
    }
  }

  "A Player as a GridSegment" should {

    "return the correct position and symbol" in {
      val player = Player(Coordinate(0, 0), Up)

      player.getPosition should be(Coordinate(0, 0))
      player.Symbol should be('R')
    }

    "return non-blocking by default" in {
      val player = Player(Coordinate(0, 0), Up)

      player.isBlocking should be(false)
    }

    "return the correct segment type" in {
      val player = Player(Coordinate(0, 0), Up)

      player.segmentType should be("player")
      player.subType should be(None)
    }
  }
}