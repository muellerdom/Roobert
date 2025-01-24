package Controller.Component.ControllerBaseImpl

import Model.FileIOComponent.FileIoJsonImpl.{Entity, GridSize, LevelConfig, Logic}
import Model.GridComponent.GridBaseImpl.{Goal, Grid, GridSegments, Obstacle, emptyField}
import Model.GridComponent.Coordinate
import Model.LevelComponent.LevelManagerTrait
import Model.PlayerComponent.PlayerBaseImpl.Player
import Util.UndoManager
import org.mockito.ArgumentMatchers.any
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.mockito.Mockito._
import org.mockito.MockitoSugar.mock

class ControllerSpec extends AnyWordSpec with Matchers {

  "A Controller" should {

    val mockLevelManager = mock[LevelManagerTrait]
    val controller = new Controller(mockLevelManager)
    val undoManager = new UndoManager

//    "successfully start a level with a valid level ID and create grid with correct entities" in {
//      // Mock LevelManager
//      val logic = Logic(
//        "basic",
//        Some(GridSize(3, 3)),
//        entities = List(
//          Entity("player", Coordinate(0, 0)),
//          Entity("goal", Coordinate(2, 2)),
//          Entity("obstacle", Coordinate(1, 1))
//        )
//      )
//      val levelConfig = LevelConfig(1, "Test Level", List("Instruction 1"), logic)
//
//      when(mockLevelManager.loadLevel(1)).thenReturn(Right(levelConfig))
//
//      // Start Level
//      val result = controller.startLevel(1)
//      result shouldBe Right(levelConfig)
//
//      // Grid Validierung
//      val grid = controller.getGrid.asInstanceOf[Grid]
//      grid.gridSegments.segments.size shouldBe 9 // 3x3 Grid
//      grid.gridSegments.findByPosition(Coordinate(0, 0)).get shouldBe a[Player]
//      grid.gridSegments.findByPosition(Coordinate(2, 2)).get shouldBe a[Goal]
//    }

    "return an error when trying to start an invalid level" in {
      // Mock LevelManager
      val mockLevelManager = mock[LevelManagerTrait]
      val controller = new Controller(mockLevelManager)

      // Mock invalid level ID
      val invalidLevelId = 999
      when(mockLevelManager.loadLevel(invalidLevelId)).thenReturn(Left("Level not found"))

      // Call the startLevel method
      val result = controller.startLevel(invalidLevelId)

      // Assert
      result should be(Left("Level not found"))
    }

//    "create grid with correct dimensions from level config" in {
//      // Mock LevelManager
//      val mockLevelManager = mock[LevelManagerTrait]
//      val controller = new Controller(mockLevelManager)
//
//      // Mock Level configuration with 4x4 grid
//      val logic = Logic(
//        "basic",
//        Some(GridSize(4, 4)),
//        entities = List.empty // No entities for this test
//      )
//      val levelConfig = LevelConfig(1, "Test Level", List.empty, logic)
//
//      when(mockLevelManager.loadLevel(any[Int])).thenReturn(Right(levelConfig))
//
//      // Call the startLevel method
//      controller.startLevel(1)
//      val grid = controller.getGrid.asInstanceOf[Grid]
//
//      // Validate grid dimensions
//      grid.gridSegments.segments.size should be(16) // 4x4 grid
//    }
//
//    "place player entity at the specified starting position" in {
//      // Mock LevelManager
//      val mockLevelManager = mock[LevelManagerTrait]
//      val controller = new Controller(mockLevelManager)
//
//      // Mock Level configuration with a player at position (1,1)
//      val playerEntity = Entity("player", Coordinate(1, 1))
//      val logic = Logic(
//        "basic",
//        Some(GridSize(3, 3)),
//        entities = List(playerEntity)
//      )
//      val levelConfig = LevelConfig(1, "Test Level", List.empty, logic)
//
//      when(mockLevelManager.loadLevel(any[Int])).thenReturn(Right(levelConfig))
//
//      // Call the startLevel method
//      controller.startLevel(1)
//      val grid = controller.getGrid.asInstanceOf[Grid]
//
//      // Validate player position
//      val player = grid.gridSegments.findByPosition(Coordinate(1, 1)).get
//      player shouldBe a[Player]
//    }

//    "convert level entities to appropriate grid segments" in {
//      // Mock LevelManager
//      val mockLevelManager = mock[LevelManagerTrait]
//      val controller = new Controller(mockLevelManager)
//
//      // Mock level configuration with different entities
//      val entities = List(
//        Entity("player", Coordinate(0, 0)),
//        Entity("goal", Coordinate(1, 1)),
//        Entity("obstacle", Coordinate(2, 2), subtype = Some("rock"))
//      )
//      val logic = Logic(
//        "basic",
//        Some(GridSize(3, 3)),
//        entities = entities
//      )
//      val levelConfig = LevelConfig(1, "Entity Test Level", List.empty, logic)
//
//      when(mockLevelManager.loadLevel(any[Int])).thenReturn(Right(levelConfig))
//
//      // Call the startLevel method
//      controller.startLevel(1)
//      val grid = controller.getGrid.asInstanceOf[Grid]
//
//      // Validate conversion
//      grid.gridSegments.findByPosition(Coordinate(0, 0)).get shouldBe a[Player]
//      grid.gridSegments.findByPosition(Coordinate(1, 1)).get shouldBe a[Goal]
//      grid.gridSegments.findByPosition(Coordinate(2, 2)).get shouldBe an[Obstacle]
//    }

    "fail to start a level if LevelManager returns an error" in {
      when(mockLevelManager.loadLevel(999)).thenReturn(Left("Level not found"))

      val result = controller.startLevel(999)

      result should be(Left("Level not found"))
    }

    "get the list of available levels" in {
      when(mockLevelManager.getAvailableLevels).thenReturn(List(1, 2, 3))

      val levels = controller.getAvailableLevels

      levels should contain allOf ("1", "2", "3")
    }

    "execute player movement commands through setCommand with valid actions" should {

//      "create and execute a SetCommand with valid movement action" in {
//        val initialPlayerPosition = Coordinate(1, 1)
//        val targetPosition = Coordinate(1, 2)
//
//        // Stellen Sie sicher, dass die Initialposition des Spielers auf ein Feld gesetzt ist
//        controller.updateGrid(Grid(GridSegments(List(
//          Player(initialPlayerPosition),
//          emptyField(targetPosition)
//        ))))
//
//        // Erstellen und ausführen einer MoveCommand (z.B. Bewegung nach rechts)
//        controller.setCommand("move-right")
//
//        // Überprüfen Sie, ob die Position des Spielers aktualisiert wurde
//        val updatedPosition = controller.getGrid.gridSegments.findByPosition(controller.getGrid.getPlayerPosition.get).get.getPosition
//        updatedPosition shouldBe targetPosition
//      }


//      "update grid state after successful movement" in {
//        val initialGrid = controller.getGrid
//
//        // Führe eine Bewegung aus
//        controller.setCommand("move-down")
//
//        // Verifiziere, dass sich der Grid-Zustand tatsächlich geändert hat
//        val updatedGrid = controller.getGrid
//        initialGrid should not be updatedGrid
//      }
    }

    "perform undo/redo operations on previous commands" should {

//      "restore previous grid state on undo" in {
//        val initialGrid = controller.getGrid
//
//        // Simuliere eine Bewegung
//        controller.setCommand("move-left")
//
//        // Führe eine Undo-Operation aus
//        controller.undo()
//
//        // Überprüfen Sie, ob der Grid-Zustand zum ursprünglichen Zustand zurückgekehrt ist
//        controller.getGrid shouldBe initialGrid
//      }
//
//      "reapply command on redo" in {
//        controller.setCommand("move-up")
//        val gridAfterMove = controller.getGrid
//
//        // Undo und Redo
//        controller.undo()
//        controller.redo()
//
//        // Das Grid sollte nach Redo wieder denselben Zustand haben wie nach dem ursprünglichen Move
//        controller.getGrid shouldBe gridAfterMove
//      }
//
//      "handle undo/redo with multiple stacked commands" in {
//        // Führe mehrere Befehle aus
//        controller.setCommand("move-up")
//        val gridAfterFirstMove = controller.getGrid
//
//        controller.setCommand("move-right")
//        val gridAfterSecondMove = controller.getGrid
//
//        // Undo zweimal ausführen
//        controller.undo()
//        controller.getGrid shouldBe gridAfterFirstMove
//
//        controller.undo()
//        // Grid sollte wieder im ursprünglichen Zustand sein
//
//        // Redo zweimal ausführen
//        controller.redo()
//        controller.getGrid shouldBe gridAfterFirstMove
//
//        controller.redo()
//        controller.getGrid shouldBe gridAfterSecondMove
//      }
    }
    "get the list of available levels as strings" should {

      "return formatted list of level IDs from level manager" in {
        // Simuliere verfügbare Level aus levelManager
        when(mockLevelManager.getAvailableLevels).thenReturn(List(1, 2, 3))

        val availableLevels = controller.getAvailableLevels
        availableLevels should contain allOf ("1", "2", "3")
      }

      "handle empty levels list" in {
        // Simuliere keine verfügbaren Level
        when(mockLevelManager.getAvailableLevels).thenReturn(Nil)

        val availableLevels = controller.getAvailableLevels
        availableLevels shouldBe empty
      }

      "convert level IDs to string representation" in {
        when(mockLevelManager.getAvailableLevels).thenReturn(List(10, 20))

        val availableLevels = controller.getAvailableLevels
        availableLevels shouldBe List("10", "20")
      }
    }

  }
}
