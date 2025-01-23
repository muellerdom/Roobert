/*package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import scalafx.scene.control.{Button, Label}

class LevelViewSpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "A LevelView" should "display available levels" in {
    val controller = mock[Controller]
    val gui = mock[GUI]
    val levelView = new LevelView(controller, gui)

    when(controller.getAvailableLevels).thenReturn(List("Level 1", "Level 2"))

    levelView.refreshLevels()

    levelView.levelButtonsContainer.children.size should be(2)
    levelView.levelButtonsContainer.children.head shouldBe a[Button]
    levelView.levelButtonsContainer.children.head.asInstanceOf[Button].text.value should be("Level 1")
  }

  it should "display a message when no levels are available" in {
    val controller = mock[Controller]
    val gui = mock[GUI]
    val levelView = new LevelView(controller, gui)

    when(controller.getAvailableLevels).thenReturn(List())

    levelView.refreshLevels()

    levelView.levelButtonsContainer.children.size should be(1)
    levelView.levelButtonsContainer.children.head shouldBe a[Label]
    levelView.levelButtonsContainer.children.head.asInstanceOf[Label].text.value should be("Keine Levels verfügbar!")
  }
}*/