package Model.REPLComponent.REPLBaseImpl

import Controller.Component.ControllerBaseImpl.Controller
import Model.REPLComponent.REPLInterface
import Util.Observable

import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.tools.nsc.interpreter.shell.ReplReporterImpl

object REPL extends Observable with REPLInterface {

  private val settings = new Settings
  settings.usejavacp.value = true // Set the class path settings.classpath.append("target/scala-2.13/classes")
  settings.async.value = true
  settings.deprecation.value  = true
  private val reporter = new ReplReporterImpl(settings)
  private val repl = new IMain(settings, reporter)



  def replBind(controller: Controller) : Unit = {
    reporter.totalSilence = true //nur während Controller eingebunden wird.
    // Binde den Controller in den REPL-Kontext
    repl.bind("controller", "Controller.Component.ControllerBaseImpl.Controller", controller)
    //repl.bind("spieler", "Model.SpielerComponent.Spieler")

    //notifyObservers()
    reporter.totalSilence = false
//    repl.interpret("""def moveForward() = controller.setCommand("forward")""")
//    repl.interpret("""def turnRight() = controller.setCommand("right")""")
//    repl.interpret("""def turnLeft() = controller.setCommand("left")""")
    repl.interpret("""def moveForward() = Model.SpielerComponent.Spieler.move("forward")""")
    repl.interpret("""def turnRight() = Model.SpielerComponent.Spieler.move("right")""")
    repl.interpret("""def turnLeft() = Model.SpielerComponent.Spieler.move("left")""")

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
