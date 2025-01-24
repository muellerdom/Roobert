/*package Model.REPLComponent.REPLBaseImpl

import Controller.Component.ControllerBaseImpl.Controller
import org.scalamock.clazz.MockImpl.mock
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class REPLSpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "REPL" should "bind the controller successfully" in {
    val controller = mock[Controller]
    noException should be thrownBy REPL.replBind(controller)
  }

  it should "interpret safe code" in {
    noException should be thrownBy REPL.Interpret("""println("Hello, World!")""")
  }

  it should "not interpret unsafe code" in {
    noException should be thrownBy REPL.Interpret("""import java.io._""")
  }

  it should "check if input is safe" in {
    REPL.isInputSafe("""println("Hello, World!")""") shouldBe true
    REPL.isInputSafe("""import java.io._""") shouldBe false
  }
}*/package Model.REPLComponent.REPLMockImpl

import Controller.Component.ControllerBaseImpl.Controller
import Model.REPLComponent.REPLInterface
import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.shell.ReplReporterImpl

object MockREPL extends REPLInterface {

  private val settings = new Settings
  settings.usejavacp.value = true
  settings.async.value = true
  settings.deprecation.value = true
  private val reporter = new ReplReporterImpl(settings)
  private val repl = new IMain(settings, reporter)

  override def replBind(controller: Controller): Unit = {
    reporter.totalSilence = true
    repl.bind("controller", "Controller.Component.ControllerBaseImpl.Controller", controller)
    reporter.totalSilence = false
    repl.interpret("""def moveForward() = println("Moving Forward")""")
    repl.interpret("""def turnRight() = println("Turning Right")""")
    repl.interpret("""def turnLeft() = println("Turning Left")""")
  }

  override def Interpret(code: String): Unit = {
    if (isInputSafe(code)) {
      repl.interpret(code)
    } else {
      println("Input ist nicht erlaubt. Bitte benutze andere Anweisungen.")
    }
  }

  private def isInputSafe(input: String): Boolean = {
    !input.contains("controller.") && !input.contains("import")
  }
}