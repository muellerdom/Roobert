package Model.LevelComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LevelManagerSpec extends AnyFlatSpec with Matchers {

  "LevelManager" should "load a level successfully" in {
    val result = levelManager.loadLevel(1)
    result.isRight shouldBe true
  }

  it should "return an error when loading a non-existent level" in {
    val result = levelManager.loadLevel(7)
    result shouldBe Left("Level mit ID '7' nicht gefunden.")
  }

  it should "return available levels" in {
    val levels = levelManager.getAvailableLevels
    levels should not be empty
  }

  it should "return the current level" in {
    levelManager.loadLevel(1)
    val currentLevel = levelManager.getCurrentLevel
    currentLevel.isDefined shouldBe true
  }
}