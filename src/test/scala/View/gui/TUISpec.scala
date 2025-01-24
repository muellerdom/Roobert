/*package View

import Controller.Component.ControllerBaseImpl.Controller
import org.mockito.ArgumentMatchers.any
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar

import java.lang.System.Logger.Level

class TUISpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "A TUI" should "start and display available levels" in {
    val controller = mock[Controller]
    val tui = new TUI(controller)

    when(controller.getAvailableLevels).thenReturn(List("Level 1", "Level 2"))

    tui.start()

    verify(controller).getAvailableLevels
  }

  it should "process input and start a level" in {
    val controller = mock[Controller]
    val tui = new TUI(controller)

    when(controller.startLevel("Level 1")).thenReturn(Right(new Level("Level 1", "Test Level", 5, 5)))

    tui.processInputLine("Level 1")

    verify(controller).startLevel("Level 1")
  }

  it should "handle invalid level input" in {
    val controller = mock[Controller]
    val tui = new TUI(controller)

    when(controller.startLevel("Invalid Level")).thenReturn(Left("Level not found"))

    tui.processInputLine("Invalid Level")

    verify(controller).startLevel("Invalid Level")
  }

  it should "display the game grid" in {
    val controller = mock[Controller]
    val tui = new TUI(controller)
    val level = new Level("Level 1", "Test Level", 5, 5)

    when(controller.getLevelConfig).thenReturn(Some(level))
    when(controller.getSpielfeld.getSpielfeld(any[Int], any[Int])).thenReturn(' ')

    tui.displayGrid()

    verify(controller).getLevelConfig
    verify(controller.getSpielfeld, times(25)).getSpielfeld(any[Int], any[Int])
  }

  it should "handle player actions" in {
    val controller = mock[Controller]
    val tui = new TUI(controller)

    tui.processInputLine("moveUp()")
    verify(controller).moveUp()

    tui.processInputLine("moveDown()")
    verify(controller).moveDown()

    tui.processInputLine("moveLeft()")
    verify(controller).moveLeft()

    tui.processInputLine("moveRight()")
    verify(controller).moveRight()

    tui.processInputLine("z")
    verify(controller).undo()

    tui.processInputLine("y")
    verify(controller).redo()
  }

  it should "update the TUI when notified" in {
    val controller = mock[Controller]
    val tui = new TUI(controller)

    tui.update()

    verify(controller).getLevelConfig
  }
}