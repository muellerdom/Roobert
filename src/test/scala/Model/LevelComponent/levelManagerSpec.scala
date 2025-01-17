package Model.LevelComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LevelManagerSpec extends AnyFlatSpec with Matchers {

  "LevelManager" should "load a level successfully" in {
    val result = levelManager.loadLevel("Level1")
    result.isRight shouldBe true
  }

  it should "return an error when loading a non-existent level" in {
    val result = levelManager.loadLevel("NonExistentLevel")
    result shouldBe Left("Level 'NonExistentLevel' nicht gefunden.")
  }

  it should "return available levels" in {
    val levels = levelManager.getAvailableLevels
    levels should not be empty
  }

  it should "return the current level" in {
    levelManager.loadLevel("Level1")
    val currentLevel = levelManager.getCurrentLevel
    currentLevel.isDefined shouldBe true
  }
}