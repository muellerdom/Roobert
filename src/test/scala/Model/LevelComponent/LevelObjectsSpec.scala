/*package Model.LevelComponent

import Model.SpielfeldComponent.Coordinate
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LevelObjectsSpec extends AnyFlatSpec with Matchers {

  "LevelObjects" should "initialize with a list of obstacles" in {
    val obstacles = List(Obstacle(Coordinate(1, 1)), Obstacle(Coordinate(2, 2)))
    val levelObjects = LevelObjects(obstacles)
    levelObjects.obstacles shouldBe obstacles
  }

  it should "handle an empty list of obstacles" in {
    val levelObjects = LevelObjects(Nil)
    levelObjects.obstacles shouldBe empty
  }
}*/