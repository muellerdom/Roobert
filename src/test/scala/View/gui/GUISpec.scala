package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.mockito.MockitoSugar.mock
import scalafx.application.Platform
import scalafx.scene.Scene

class GUISpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "A GUI" should "initialize the stage correctly" in {
    val controller = mock[Controller]
    val gui = new GUI(controller)

    Platform.runLater {
      gui.start()

      gui.stage should not be null
      gui.stage.title.value should be("Hilf Roobert")
      gui.stage.scene.value shouldBe a[Scene]
    }
  }

  it should "switch to LevelView" in {
    val controller = mock[Controller]
    val gui = new GUI(controller)
    val levelView = mock[LevelView]

    Platform.runLater {
      gui.switchToLevelView()

      gui.stage.scene.value.root.value shouldBe a[LevelView]
    }
  }

  it should "switch to GameView" in {
    val controller = mock[Controller]
    val gui = new GUI(controller)
    val gameView = mock[GameView]

    Platform.runLater {
      gui.switchToGameView()

      gui.stage.scene.value.root.value shouldBe a[GameView]
    }
  }
}