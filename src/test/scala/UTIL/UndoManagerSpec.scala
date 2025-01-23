package UTIL

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Util.{Command, UndoManager}

class UndoManagerSpec extends AnyFlatSpec with Matchers {

  "An UndoManager" should "execute doStep and add to undoStack" in {
    val undoManager = new UndoManager
    val command = new TestCommand

    undoManager.doStep(command)
    command.executed should be(true)
    undoManager.undoStack should contain(command)
  }

  it should "undo a command and add to redoStack" in {
    val undoManager = new UndoManager
    val command = new TestCommand

    undoManager.doStep(command)
    undoManager.undoStep
    command.executed should be(false)
    undoManager.redoStack should contain(command)
  }

  it should "redo a command and add back to undoStack" in {
    val undoManager = new UndoManager
    val command = new TestCommand

    undoManager.doStep(command)
    undoManager.undoStep
    undoManager.redoStep
    command.executed should be(true)
    undoManager.undoStack should contain(command)
  }

  class TestCommand extends Command {
    var executed = false

    override def doStep(): Unit = {
      executed = true
    }

    override def undoStep(): Unit = {
      executed = false
    }

    override def redoStep(): Unit = {
      executed = true
    }
  }
}