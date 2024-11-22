package Controller

import Controller.Controller
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ControllerTest extends AnyFlatSpec with Matchers {

  "A Controller" should "load levels correctly" in {
    val controller = new Controller()
    val result = controller.startLevel("test-level")

    result should be ('right)
  }

  it should "validate levels correctly" in {
    val controller = new Controller()

    val faultyLevel = LevelConfig("faulty", "", "", 3, 3, Coordinate(0, 0), Goal(4, 4), Objects(List(), List()))
    val validLevel = LevelConfig("valid", "", "", 3, 3, Coordinate(0, 0), Goal(2, 2), Objects(List(), List()))

    controller.validateLevels(List(faultyLevel)) should be ('left)
    controller.validateLevels(List(validLevel)) should be ('right)
  }
}