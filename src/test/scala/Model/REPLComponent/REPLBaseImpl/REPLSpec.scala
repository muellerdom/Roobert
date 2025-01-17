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
}*/