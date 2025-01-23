package Model.LevelComponent

import Model.SpielfeldComponent.Coordinate
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LevelManagerTraitSpec extends AnyFlatSpec with Matchers {

  "LevelManagerTrait" should "load a level" in {
    val levelManager = new LevelManagerTrait {
      override def loadLevel(levelName: String): Either[String, LevelConfig] = Right(LevelConfig(levelName, "", "", 0, 0, Coordinate(0, 0), Goal(0, 0), Objects(Nil, Nil)))
      override def getAvailableLevels: List[String] = List("Level1")
      override def getCurrentLevel: Option[LevelConfig] = Some(LevelConfig("Level1", "", "", 0, 0, Coordinate(0, 0), Goal(0, 0), Objects(Nil, Nil)))
    }
    val result = levelManager.loadLevel("Level1")
    result.isRight shouldBe true
  }

  it should "return available levels" in {
    val levelManager = new LevelManagerTrait {
      override def loadLevel(levelName: String): Either[String, LevelConfig] = Right(LevelConfig(levelName, "", "", 0, 0, Coordinate(0, 0), Goal(0, 0), Objects(Nil, Nil)))
      override def getAvailableLevels: List[String] = List("Level1")
      override def getCurrentLevel: Option[LevelConfig] = Some(LevelConfig("Level1", "", "", 0, 0, Coordinate(0, 0), Goal(0, 0), Objects(Nil, Nil)))
    }
    val levels = levelManager.getAvailableLevels
    levels should not be empty
  }

  it should "return the current level" in {
    val levelManager = new LevelManagerTrait {
      override def loadLevel(levelName: String): Either[String, LevelConfig] = Right(LevelConfig(levelName, "", "", 0, 0, Coordinate(0, 0), Goal(0, 0), Objects(Nil, Nil)))
      override def getAvailableLevels: List[String] = List("Level1")
      override def getCurrentLevel: Option[LevelConfig] = Some(LevelConfig("Level1", "", "", 0, 0, Coordinate(0, 0), Goal(0, 0), Objects(Nil, Nil)))
    }
    levelManager.loadLevel("Level1")
    val currentLevel = levelManager.getCurrentLevel
    currentLevel.isDefined shouldBe true
  }
}