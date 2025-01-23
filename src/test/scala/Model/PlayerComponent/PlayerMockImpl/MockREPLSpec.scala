package Model.REPLComponent.REPLMockImpl

import Controller.Component.ControllerBaseImpl.Controller
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import org.scalamock.clazz.MockImpl.mock

class MockREPLSpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "MockREPL" should "bind the controller correctly" in {
    val controller = mock[Controller]
    noException should be thrownBy MockREPL.replBind(controller)
  }

  it should "interpret safe code correctly" in {
    val safeCode = """println("Hello, World!")"""
    noException should be thrownBy MockREPL.Interpret(safeCode)
  }

  it should "reject unsafe code" in {
    val unsafeCode = """import scala.io.Source"""
    val outputStream = new java.io.ByteArrayOutputStream()
    Console.withOut(outputStream) {
      MockREPL.Interpret(unsafeCode)
    }
    outputStream.toString should include ("Input ist nicht erlaubt. Bitte benutze andere Anweisungen.")
  }
}