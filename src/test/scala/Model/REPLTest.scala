package Controller

import Model.REPLComponent.REPLBaseImpl.MockREPL
import Controller.Component.ControllerBaseImpl.Controller
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class REPLTest extends AnyWordSpec with Matchers {

  "A MockREPL" should {

    val mockController = new Controller

    "bind methods to the REPL" in {
      MockREPL.replBind(mockController)
      // Testen, ob Methoden korrekt gebunden wurden
      MockREPL.Interpret("moveForward()") shouldEqual "Moving Forward"
      MockREPL.Interpret("turnRight()") shouldEqual "Turning Right"
      MockREPL.Interpret("turnLeft()") shouldEqual "Turning Left"
    }

    "reject unsafe input" in {
      MockREPL.Interpret("import java.io.File") shouldEqual "Input ist nicht erlaubt. Bitte benutze andere Anweisungen."
    }

    "accept safe input" in {
      MockREPL.Interpret("moveForward()") shouldEqual "Moving Forward"
    }
  }
}
