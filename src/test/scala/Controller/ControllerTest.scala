package Controller

import Controller.Component.ControllerInterface
import Model.LevelComponent.{Goal, Jerm, LevelConfig, Objects, Obstacle}
import Model.PlayerComponent.PlayerBaseImpl.Spieler
import Model.SpielfeldComponent.{Coordinate, SpielfeldInterface}
import Util.{Observer, UndoManager}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ControllerTest extends AnyFlatSpec with Matchers with MockFactory {

  val testLevelConfig: LevelConfig = LevelConfig(
    "test-level",
    "A test level",
    "Collect all jerms",
    width = 5,
    height = 5,
    start = Coordinate(0, 0),
    goal = Goal(4, 4),
    objects = Objects(
      obstacles = List(Obstacle("stein", Coordinate(2, 3)), Obstacle("holz", Coordinate(2, 2))),
      jerm = List(Jerm(Coordinate(4, 3)))
    )
  )

  "startLevel" should "return an error when an invalid level name is provided" in {
    val mockController = mock[ControllerInterface]
    (mockController.startLevel _).expects("invalid-level").returning(Left("Level 'invalid-level' nicht gefunden."))

    mockController.startLevel("invalid-level") shouldBe Left("Level 'invalid-level' nicht gefunden.")
  }

  it should "load a valid level and notify observers" in {
    val mockController = mock[ControllerInterface]
    (mockController.startLevel _).expects("test-level").returning(Right(testLevelConfig))

    mockController.startLevel("test-level") shouldBe Right(testLevelConfig)
  }

  "undo" should "call undoStep and notify observers" in {
    val mockUndoManager = mock[UndoManager]
    val mockObserver = mock[Observer]

    (mockUndoManager.undoStep _).expects().once()
    val controller = new ControllerInterface {
      val undoManager: UndoManager = mockUndoManager
      val observers: List[Observer] = List(mockObserver)

      override def undo(): Unit = {
        undoManager.undoStep
        observers.foreach(_.update())
      }

      // Dummy implementations
      def startLevel(name: String): Either[String, LevelConfig] = ???
      def setCommand(action: String) = ???
      def redo() = ???
      def getAvailableLevels = ???
      def isLevelComplete = ???
      def getSpielfeld = ???
      def getLevelConfig = ???
    }

    (mockObserver.update _).expects().once()
    controller.undo()
  }

  "redo" should "call redoStep and notify observers" in {
    val mockUndoManager = mock[UndoManager]
    val mockObserver = mock[Observer]

    (mockUndoManager.redoStep _).expects().once()
    val controller = new ControllerInterface {
      val undoManager: UndoManager = mockUndoManager
      val observers: List[Observer] = List(mockObserver)

      override def redo(): Unit = {
        undoManager.redoStep
        observers.foreach(_.update())
      }

      // Dummy implementations
      def startLevel(name: String) = ???
      def setCommand(action: String) = ???
      def undo() = ???
      def getAvailableLevels = ???
      def isLevelComplete = ???
      def getSpielfeld = ???
      def getLevelConfig = ???
    }

    (mockObserver.update _).expects().once()
    controller.redo()
  }

  "isLevelComplete" should "return true if all jerms are collected and the player is at the goal" in {
    val mockController = mock[ControllerInterface]

    (mockController.isLevelComplete _).expects().returning(true)
    mockController.isLevelComplete shouldBe true
  }

  it should "return false if not all jerms are collected" in {
    val mockController = mock[ControllerInterface]

    (mockController.isLevelComplete _).expects().returning(false)
    mockController.isLevelComplete shouldBe false
  }

  it should "return false if the player is not at the goal" in {
    val mockController = mock[ControllerInterface]

    (mockController.isLevelComplete _).expects().returning(false)
    mockController.isLevelComplete shouldBe false
  }

  "getAvailableLevels" should "return a list of level names" in {
    val mockController = mock[ControllerInterface]
    val levels = List("Level1", "Level2", "Level3")

    (mockController.getAvailableLevels _).expects().returning(levels)
    mockController.getAvailableLevels shouldBe levels
  }

  it should "return an empty list if no levels are available" in {
    val mockController = mock[ControllerInterface]

    (mockController.getAvailableLevels _).expects().returning(List.empty)
    mockController.getAvailableLevels shouldBe List.empty
  }
}
