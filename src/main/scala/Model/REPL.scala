package Model

import Controller.Controller
import Util.Observable
import ammonite.repl.Repl

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.shell.ReplReporterImpl

object REPL extends Observable {

  private val settings = new Settings
  settings.usejavacp.value = true // Set the class path settings.classpath.append("target/scala-2.13/classes")
  settings.async.value = true
  settings.deprecation.value  = true
  private val reporter = new ReplReporterImpl(settings)
  private val repl = new IMain(settings, reporter)


  def replBind(controller: Controller) : Unit = {
    reporter.totalSilence = true //nur während Controller eingebunden wird.
    // Binde den Controller in den REPL-Kontext
    repl.bind("controller", "Controller.Controller", controller)
    reporter.totalSilence = false
    repl.interpret("""def moveForward() = controller.movePlayer("forward")""")
    repl.interpret("""def turnRight() = controller.movePlayer("right")""")
    repl.interpret("""def turnLeft() = controller.movePlayer("left")""")
  }

  def Interpret(code: String): Unit = {
    if (isInputSafe(code)) {
      repl.interpret(code)
    } else {
      println("Input ist nicht erlaubt. Bitte benutze andere Anweisungen um Roobert zu bewegen")
    }
  }

  //hier ist sehr viel mehr hinzuzufügen
  private def isInputSafe(input: String): Boolean = {
    !input.contains("controller.") || !input.contains("import")
  }

}
