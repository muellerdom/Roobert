package Model.LevelComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LoadLevelFromJSONSpec extends AnyFlatSpec with Matchers {

  "LoadLevelFromJSON" should "load levels from JSON file" in {
    val levels = loadLeveFromJSON.getLevels
    levels.isDefined shouldBe true
  }

  it should "return an error when JSON file is invalid" in {
    val result = loadLeveFromJSON.loadJsonFromFile("invalid/path")
    result.isLeft shouldBe true
  }
}