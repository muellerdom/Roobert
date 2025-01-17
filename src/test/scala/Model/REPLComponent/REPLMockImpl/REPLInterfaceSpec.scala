package Model.REPLComponent

import Controller.Component.ControllerBaseImpl.Controller
import Model.LevelComponent.LevelManagerTrait
import org.scalamock.clazz.MockImpl.mock
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class REPLInterfaceSpec extends AnyFlatSpec with Matchers {

  "REPLInterface" should "bind the controller" in {
    val repl: REPLInterface = Model.REPLComponent.REPLBaseImpl.REPL
    val controller = new Controller(mock[LevelManagerTrait])
    noException should be thrownBy repl.replBind(controller)
  }

  it should "interpret code" in {
    val repl: REPLInterface = Model.REPLComponent.REPLBaseImpl.REPL
    noException should be thrownBy repl.Interpret("""println("Hello, World!")""")
  }
}