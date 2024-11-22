package View

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Controller.Controller
import Model.TestLevelConfig
///hmmm
class tuiTest extends AnyFlatSpec with Matchers {

  "A TUI" should "start and select levels correctly" in {
    val controller = new Controller()
    val tui = new TUI(controller)
    val fakeInput = "test-level"

    tui.processInputLine(fakeInput)
    controller.getCurrentLevelConfig should not be empty
  }

  it should "display the grid correctly" in {
    val controller = new Controller()
    val tui = new TUI(controller)
    val testLevel = TestLevelConfig.withJermAt(1, 1)

    tui.initializePlayer(testLevel)
    tui.displayGrid(testLevel)

    // Capture printed grid output and verify it contains player and jerm positions
    // This part might require some additional logic to capture and test console output
  }

    // Add more tests for different user interactions and edge cases
  }
}