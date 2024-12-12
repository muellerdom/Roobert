package Controller

import Model.{Coordinate, Goal, Jerm, LevelConfig, Objects, Obstacle, Spieler, Spielfeld, levelManager}
import Util.{Observer, UndoManager}
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ControllerTest extends AnyFlatSpec with Matchers with MockFactory {

  val levelConfig: LevelConfig = LevelConfig(
    "test-level",
    "A test level",
    "Collect all jerms",
    5, 5,
    Coordinate(0, 0),
    Goal(4, 4),
    Objects(List(Obstacle("stein", Coordinate(2, 3)), Obstacle("holz", Coordinate(2, 2))), List(Jerm(Coordinate(4, 3))))
  )

  "startLevel" should "return an error when an invalid level name is provided" in {
    val controller = new Controller
    controller.startLevel("invalid-level") shouldBe Left("Level 'invalid-level' nicht gefunden.")
  }

  "initializeSpielfeld" should "correctly place all objects on the grid according to the level configuration" in {
    val controller = new Controller

    controller.startLevel("test-level")
    controller.getGrid(4, 4) shouldBe 'G'
    controller.getGrid(2, 3) shouldBe 'X'
    controller.getGrid(2, 2) shouldBe 'X'
    controller.getGrid(4, 3) shouldBe 'J'
    controller.getGrid(0, 0) shouldBe 'R'
  }

  "movePlayer" should "update the spielStatus to ProcessMoveStage and notify observers" in {
    val controller = new Controller
    val observer = mock[Observer]
    controller.addObserver(observer)
    (observer.update _).expects().once()

    controller.movePlayer("some-action")
    controller.spielStatus shouldBe SpielStatus.ProcessMoveStage
  }

//  "getAvailableLevels" should "return a list of level names that are available in the levelManager" in {
//    //val mockLevelManager = levelManager.type]
//    (levelManager.getAvailableLevels _).expects().returning(List("Level1", "Level2"))
//
//    val controller = new Controller {
//      override def getAvailableLevels: List[String] = levelManager.getAvailableLevels
//    }
//
//    controller.getAvailableLevels shouldBe List("Level1", "Level2")
//  }
//
//  it should "return an empty list when no levels are available in the levelManager" in {
//   // val mockLevelManager = mock[levelManager.type]
//    (levelManager.getAvailableLevels _).expects().returning(List())
//
//    val controller = new Controller {
//      override def getAvailableLevels: List[String] = levelManager.getAvailableLevels
//    }
//
//    controller.getAvailableLevels shouldBe List()
//  }
//
//  it should "not modify the state of the Controller or levelManager" in {
//    //val mockLevelManager = mock[levelManager.type]
//    (levelManager.getAvailableLevels _).expects().returning(List("Level1", "Level2"))
//
//    val controller = new Controller {
//      override def getAvailableLevels: List[String] = levelManager.getAvailableLevels
//    }
//
//    val initialState = controller.spielStatus
//    controller.getAvailableLevels
//    controller.spielStatus shouldBe initialState
//  }

  "undo" should "update the spielStatus to ProcessMoveStage and call undoStep" in {
    val mockUndoManager = mock[UndoManager]
    (mockUndoManager.undoStep _).expects().once()

    val controller = new Controller {
      val undoManager: UndoManager = mockUndoManager
    }

    controller.undo()
    controller.spielStatus shouldBe SpielStatus.ProcessMoveStage
  }

  it should "notify all observers after performing the undo operation" in {
    val controller = new Controller
    val observer = mock[Observer]
    controller.addObserver(observer)
    (observer.update _).expects().once()

    controller.undo()
  }

  it should "print the current position of the Spieler after the undo operation" in {
    val controller = new Controller
    val initialPosition = Coordinate(0, 0)
    Spieler.initialize(initialPosition.x, initialPosition.y)

    val output = new java.io.ByteArrayOutputStream()
    Console.withOut(output) {
      controller.undo()
    }

    output.toString should include(initialPosition.toString)
  }

  "redo" should "update the spielStatus to ProcessMoveStage" in {
    val controller = new Controller
    controller.redo()
    controller.spielStatus shouldBe SpielStatus.ProcessMoveStage
  }

  it should "call redoStep on the undoManager" in {
    val mockUndoManager = mock[UndoManager]
    (mockUndoManager.redoStep _).expects().once()

    val controller = new Controller {
      val undoManager: UndoManager = mockUndoManager
    }

    controller.redo()
  }

  it should "notify all observers after performing the redo operation" in {
    val controller = new Controller
    val observer = mock[Observer]
    controller.addObserver(observer)
    (observer.update _).expects().once()

    controller.redo()
  }

  "isLevelComplete" should "return true when all jerms are collected and the player is at the goal position" in {
    val controller = new Controller
    levelManager.ladeLevel("test-level")
    Spieler.eingesammelteJerms = Set(Coordinate(4, 3))
    Spieler.position = Some(Coordinate(4, 4))
    controller.isLevelComplete shouldBe true
  }

  it should "return false if not all jerms are collected, even if the player is at the goal position" in {
    val controller = new Controller
    levelManager.ladeLevel("test-level")
    Spieler.eingesammelteJerms = Set(Coordinate(4, 3))
    Spieler.position = Some(Coordinate(4, 4))
    controller.isLevelComplete shouldBe false
  }

  it should "return false if the player has collected all jerms but is not at the goal position" in {
    val controller = new Controller
    levelManager.ladeLevel("test-level")
    Spieler.eingesammelteJerms = Set(Coordinate(4, 3))
    Spieler.position = Some(Coordinate(3, 3))
    controller.isLevelComplete shouldBe false
  }
}