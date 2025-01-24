package Model.REPLComponent.REPLMockImpl

import Controller.Component.ControllerBaseImpl.Controller
import Model.REPLComponent.REPLInterface
import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.shell.ReplReporterImpl

object MockREPL extends REPLInterface {

  private val settings = new Settings
  settings.usejavacp.value = true
  settings.async.value = true
  settings.deprecation.value  = true
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
    !input.contains("controller.") || !input.contains("import")
  }
}
