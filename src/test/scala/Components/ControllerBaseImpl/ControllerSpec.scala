package Controller.Component.ControllerBaseImpl

import Model.FileIOComponent.FileIoJsonImpl.LevelConfig
import Model.GridComponent.GridBaseImpl.{Grid, GridSegments, Obstacle, emptyField}
import Model.GridComponent.Coordinate
import Model.LevelComponent.LevelManagerTrait
import Util.UndoManager
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.mockito.Mockito._
import org.mockito.MockitoSugar.mock

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" should {

    val mockLevelManager = mock[LevelManagerTrait]
    val controller = new Controller(mockLevelManager)

//    "start a level successfully" in {
//      // Mock LevelManager behavior
//      val mockLevelConfig = LevelConfig(1, "test-level", null, null)
//      when(mockLevelManager.loadLevel(1)).thenReturn(Right(mockLevelConfig))
//
//      val result = controller.startLevel(1)
//
//      result should be(Right(mockLevelConfig))
//      //controller.getGrid.gridSegments.segments.rows.size should be >= 0
//    }

    "fail to start a level if LevelManager returns an error" in {
      when(mockLevelManager.loadLevel(999)).thenReturn(Left("Level not found"))

      val result = controller.startLevel(999)

      result should be(Left("Level not found"))
    }

//    "get the list of available levels" in {
//      when(mockLevelManager.getAvailableLevels).thenReturn(List(1, 2, 3))
//
//      val levels = controller.getAvailableLevels
//
//      levels should contain allOf ("Level 1", "Level 2", "Level 3")
//    }
//
//    "undo a command" in {
//      // Ensure undoManager is empty initially
//      val undoManager = mock[UndoManager]
//      //controller.undoManager.undoStep()
//
//      controller.undo()
//      verify(undoManager).undoStep()
//    }
//
//    "redo a command" in {
//      // Mock UndoManager to control behavior
//      val undoManager = mock[UndoManager]
//      controller.redo()
//
//      // Verify that redoStep was called
//      verify(undoManager).redoStep()
//    }
//
//    "verify if a move is valid" in {
//      // Mock basic grid setup
//      controller.updateGrid(Grid(GridSegments(List(
//        // Creating a mocked grid with some blocking and non-blocking elements
//        emptyField(Coordinate(0, 0)),
//        new Obstacle(Coordinate(1, 1), "wall")
//      ))))
//
//    }
//
//    "return if the level is complete" in {
//      // Since isLevelComplete() is mocked to always return true, verify the result
//      controller.isLevelComplete should be(true) // Default implementation always returns true
//    }
//
//    "save and bind the REPL during level start" in {
//      // Mock LevelManager and test REPL behavior
//      val mockLevelConfig = LevelConfig(1, "Test Level", null, null)
//      when(mockLevelManager.loadLevel(1)).thenReturn(Right(mockLevelConfig))
//
//      val result = controller.startLevel(1)
//
//      result should be(Right(mockLevelConfig))
//      // Additional assertions for REPL binding or side effects can go here if required
//    }
//
//    "interpret REPL code" in {
//      // Mock REPL behavior
//      noException should be thrownBy controller.interpret("some test code")
//    }
  }
}
