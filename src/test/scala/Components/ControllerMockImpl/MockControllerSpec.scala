package Controller.Component.ControllerMockImpl

import Model.LevelComponent.{LevelConfig, Objects, Goal}
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MockControllerSpec extends AnyFlatSpec with Matchers {

  "MockController" should "start a level successfully" in {
    val controller = new MockController
    val result = controller.startLevel("Level1")

    result shouldBe Right(LevelConfig("Level1", "Test Level", "Test Level", 10, 10, Coordinate(0, 0), controller.goal, controller.objects))
  }

  it should "return an error when starting a non-existent level" in {
    val controller = new MockController
    val result = controller.startLevel("NonExistentLevel")

    result shouldBe Left("Level nicht gefunden.")
  }

  it should "return available levels" in {
    val controller = new MockController
    val levels = controller.getAvailableLevels

    levels shouldBe List("Level1", "Level2", "Level3")
  }

  it should "execute a command" in {
    val controller = new MockController
    controller.setCommand("move")

    controller.commands should contain("move")
  }

  it should "undo a command" in {
    val controller = new MockController
    controller.setCommand("move")
    controller.undo()

    controller.commands should not contain "move"
  }

  it should "check if the level is complete" in {
    val controller = new MockController
    controller.inventorySize = 3
    controller.playerPosition = controller.goalPosition

    val isComplete = controller.isLevelComplete

    isComplete shouldBe true
  }

  it should "return the current Spielfeld" in {
    val controller = new MockController
    val spielfeld = controller.getSpielfeld

    spielfeld shouldBe a[SpielfeldInterface]
  }

  it should "return the current level configuration" in {
    val controller = new MockController
    controller.startLevel("Level1")
    val config = controller.getLevelConfig

    config shouldBe Some(LevelConfig("Level1", "Test Level", "Test Level", 10, 10, Coordinate(0, 0), controller.goal, controller.objects))
  }
}