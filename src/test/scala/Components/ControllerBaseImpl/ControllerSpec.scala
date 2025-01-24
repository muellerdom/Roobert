/*package Controller.ControllerBaseImpl

import Controller.Component.ControllerBaseImpl.Controller
import Model.LevelComponent.{LevelConfig, LevelManagerTrait}
import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}
import Util.{Observable, UndoManager}
import org.mockito.Mockito._
import org.scalamock.clazz.MockImpl.mock
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar

import scala.Option.when

class ControllerSpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "Controller" should "start a level successfully" in {
    val levelManager = mock[LevelManagerTrait]
    val levelConfig = LevelConfig("Level1", "Test Level", "Test Level", 10, 10, Coordinate(0, 0), null, null)
    when(levelManager.loadLevel("Level1")).thenReturn(Right(levelConfig))

    val controller = new Controller(levelManager)
    val result = controller.startLevel("Level1")

    result shouldBe Right(levelConfig)
    verify(levelManager).loadLevel("Level1")
  }

  it should "return an error when starting a non-existent level" in {
    val levelManager = mock[LevelManagerTrait]
    when(levelManager.loadLevel("NonExistentLevel")).thenReturn(Left("Level not found"))

    val controller = new Controller(levelManager)
    val result = controller.startLevel("NonExistentLevel")

    result shouldBe Left("Level not found")
    verify(levelManager).loadLevel("NonExistentLevel")
  }

  it should "return available levels" in {
    val levelManager = mock[LevelManagerTrait]
    when(levelManager.getAvailableLevels).thenReturn(List("Level1", "Level2"))

    val controller = new Controller(levelManager)
    val levels = controller.getAvailableLevels

    levels shouldBe List("Level1", "Level2")
    verify(levelManager).getAvailableLevels
  }

  it should "execute a command and notify observers" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)
    val observer = mock[Observer]
    controller.addObserver(observer)

    controller.setCommand("move")

    verify(observer).update()
  }

  it should "undo a command and notify observers" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)
    val observer = mock[Observer]
    controller.addObserver(observer)

    controller.setCommand("move")
    controller.undo()

    verify(observer, times(2)).update()
  }

  it should "redo a command and notify observers" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)
    val observer = mock[Observer]
    controller.addObserver(observer)

    controller.setCommand("move")
    controller.undo()
    controller.redo()

    verify(observer, times(3)).update()
  }

  it should "check if the level is complete" in {
    val levelManager = mock[LevelManagerTrait]
    val levelConfig = LevelConfig("Level1", "Test Level", "Test Level", 10, 10, Coordinate(0, 0), null, null)
    when(levelManager.getCurrentLevel).thenReturn(Some(levelConfig))
    Player.inventory = List.fill(levelConfig.objects.jerm.size)('item')
    Player.position = Some(Coordinate(levelConfig.goal.x, levelConfig.goal.y))

    val controller = new Controller(levelManager)
    val isComplete = controller.isLevelComplete

    isComplete shouldBe true
  }

  it should "return the current Spielfeld" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)
    val spielfeld = controller.getSpielfeld

    spielfeld shouldBe Spielfeld
  }

  it should "return the current level configuration" in {
    val levelManager = mock[LevelManagerTrait]
    val levelConfig = LevelConfig("Level1", "Test Level", "Test Level", 10, 10, Coordinate(0, 0), null, null)
    when(levelManager.getCurrentLevel).thenReturn(Some(levelConfig))

    val controller = new Controller(levelManager)
    val config = controller.getLevelConfig

    config shouldBe Some(levelConfig)
  }
}*/package Controller.Component.ControllerBaseImpl

import Model.LevelComponent.{LevelConfig, LevelManagerTrait}
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ControllerSpec extends AnyFlatSpec with Matchers with MockFactory {

  "Controller" should "start a level successfully" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)
    val levelConfig = LevelConfig("Level1", "Test Level", "Test Level", 10, 10, Coordinate(0, 0), null, null)

    (levelManager.loadLevel _).expects("Level1").returning(Right(levelConfig))

    controller.startLevel("Level1") shouldBe Right(levelConfig)
  }

  it should "return an error when starting a non-existent level" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)

    (levelManager.loadLevel _).expects("NonExistentLevel").returning(Left("Level nicht gefunden."))

    controller.startLevel("NonExistentLevel") shouldBe Left("Level nicht gefunden.")
  }

  it should "return available levels" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)
    val levels = List("Level1", "Level2", "Level3")

    (levelManager.getAvailableLevels _).expects().returning(levels)

    controller.getAvailableLevels shouldBe levels
  }

  it should "execute a command" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)

    controller.setCommand("move")

    // Add assertions to verify the command execution
  }

  it should "undo a command" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)

    controller.setCommand("move")
    controller.undo()

    // Add assertions to verify the command undo
  }

  it should "redo a command" in {
    val levelManager = mock[LevelManagerTrait]
    val controller = new Controller(levelManager)

    controller.setCommand("move")
    controller.undo()
    controller.redo()

    // Add assertions to verify the command redo
  }
}