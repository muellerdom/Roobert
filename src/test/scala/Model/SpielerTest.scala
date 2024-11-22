package Model

import Controller.{Coordinate, Goal, LevelConfig, Objects, Obstacle}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SpielerSpec extends AnyFlatSpec with Matchers {

  "A Spieler" should "initialize with correct position" in {
    val spieler = new Spieler(0, 0, 5, 5)
    spieler.posX should be (0)
    spieler.posY should be (0)
  }

  it should "turn right correctly" in {
    val spieler = new Spieler(0, 0, 5, 5)

    spieler.turnRight()
    spieler.direction should be (spieler.Rechts)

    spieler.turnRight()
    spieler.direction should be (spieler.Unten)

    spieler.turnRight()
    spieler.direction should be (spieler.Links)

    spieler.turnRight()
    spieler.direction should be (spieler.Oben)
  }

  it should "turn left correctly" in {
    val spieler = new Spieler(0, 0, 5, 5)

    spieler.turnLeft()
    spieler.direction should be (spieler.Links)

    spieler.turnLeft()
    spieler.direction should be (spieler.Unten)

    spieler.turnLeft()
    spieler.direction should be (spieler.Rechts)

    spieler.turnLeft()
    spieler.direction should be (spieler.Oben)
  }

  it should "move forward correctly and collect Jerms" in {
    val spieler = new Spieler(0, 0, 5, 5)
    val testLevel = TestLevelConfig.withJermAt(1, 0)

    spieler.turnRight()
    spieler.move("moveForward", testLevel)
    spieler.posX should be (1)
    spieler.posY should be (0)

    spieler.eingesammelteJerms should contain (Coordinate(1, 0))
  }

  it should "not move into obstacles or out of bounds" in {
    val spieler = new Spieler(0, 0, 5, 5)
    val testLevel = TestLevelConfig.withObstacleAt(1, 0)

    spieler.turnRight()
    spieler.move("moveForward", testLevel)
    spieler.posX should be (0)
    spieler.posY should be (0) // Movement blocked by obstacle

    spieler.turnLeft()
    spieler.move("moveForward", testLevel)
    spieler.posX should be (0)
    spieler.posY should be (0) // Movement out of bounds blocked
  }
}

// Test helpers
object TestLevelConfig {
  val empty: LevelConfig = LevelConfig("test", "", "", 5, 5, Coordinate(0, 0), Goal(4, 4), Objects(List(), List()))

  def withJermAt(x: Int, y: Int): LevelConfig = empty.copy(objects =
    empty.objects.copy(jerm = List(Coordinate(x, y))))

  def withObstacleAt(x: Int, y: Int): LevelConfig = empty.copy(objects =
    empty.objects.copy(obstacles = List(Obstacle("Stein", Coordinate(x,y)))))
}