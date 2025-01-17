
package Model.PlayerComponent.PlayerBaseImpl

import Model.LevelComponent.LevelManagerTrait
import Model.SpielfeldComponent.{Coordinate, KomponentenInterface}
import Model.SpielfeldComponent.SpielfeldBaseImpl.Jerm
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.mockito.Mockito._

class PlayerSpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "Player" should "initialize correctly" in {
    val levelManager = mock[LevelManagerTrait]
    when(levelManager.getCurrentLevel).thenReturn(Some(LevelConfig("Level1", "", "", 10, 10, Coordinate(0, 0), null, null)))
    Player.initialize()
    Player.getPosition shouldBe Coordinate(0, 0)
    Player.direction shouldBe Player.Oben
  }

  it should "move forward correctly" in {
    val levelManager = mock[LevelManagerTrait]
    when(levelManager.getCurrentLevel).thenReturn(Some(LevelConfig("Level1", "", "", 10, 10, Coordinate(0, 0), null, null)))
    Player.initialize()
    Player.move("forward")
    Player.getPosition shouldBe Coordinate(0, 1)
  }

  it should "turn right correctly" in {
    Player.initialize()
    Player.turnRight()
    Player.direction shouldBe Player.Rechts
  }

  it should "turn left correctly" in {
    Player.initialize()
    Player.turnLeft()
    Player.direction shouldBe Player.Links
  }

  it should "collect a Jerm" in {
    val jerm = mock[Jerm]
    when(jerm.getPosition).thenReturn(Coordinate(1, 1))
    Spielfeld.components = List(jerm)
    Player.initialize()
    Player.move("forward")
    Player.move("right")
    Player.einsammeln(Coordinate(1, 1))
    Player.inventory.containsItem(jerm) shouldBe true
  }

  it should "not move out of bounds" in {
    val levelManager = mock[LevelManagerTrait]
    when(levelManager.getCurrentLevel).thenReturn(Some(LevelConfig("Level1", "", "", 1, 1, Coordinate(0, 0), null, null)))
    Player.initialize()
    Player.move("forward")
    Player.getPosition shouldBe Coordinate(0, 0)
  }

  it should "not move into an obstacle" in {
    val levelManager = mock[LevelManagerTrait]
    when(levelManager.getCurrentLevel).thenReturn(Some(LevelConfig("Level1", "", "", 10, 10, Coordinate(0, 0), null, List(Obstacle(Coordinate(0, 1))))))
    Player.initialize()
    Player.move("forward")
    Player.getPosition shouldBe Coordinate(0, 0)
  }
}