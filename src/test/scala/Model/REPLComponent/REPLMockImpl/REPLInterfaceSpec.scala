package Model.REPLComponent

import Controller.Component.ControllerBaseImpl.Controller
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory

class REPLInterfaceSpec extends AnyFlatSpec with Matchers with MockFactory {

  "REPLInterface" should "bind the controller" in {
    val repl = mock[REPLInterface]
    val controller = mock[Controller]
    (repl.replBind _).expects(controller).once()
    repl.replBind(controller)
  }

  it should "interpret code" in {
    val repl = mock[REPLInterface]
    val code = """println("Hello, World!")"""
    (repl.Interpret _).expects(code).once()
    repl.Interpret(code)
  }
}