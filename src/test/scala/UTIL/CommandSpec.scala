package UTIL

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Util.{Command, UndoManager}

class CommandSpec extends AnyFlatSpec with Matchers {

  "A Command" should "execute doStep" in {
    val command = new TestCommand
    command.doStep()
    command.executed should be(true)
  }

  it should "execute undoStep" in {
    val command = new TestCommand
    command.doStep()
    command.undoStep()
    command.executed should be(false)
  }

  it should "execute redoStep" in {
    val command = new TestCommand
    command.doStep()
    command.undoStep()
    command.redoStep()
    command.executed should be(true)
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