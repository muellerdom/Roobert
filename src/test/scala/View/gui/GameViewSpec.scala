package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Model.LevelComponent.LevelConfig
import org.scalamock.clazz.MockImpl.mock
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar
import scalafx.scene.control.Label

import scala.Option.when

class GameViewSpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "A GameView" should "initialize the grid correctly" in {
    val controller = mock[Controller]
    val gui = mock[GUI]
    val gameView = new GameView(controller, gui)

    // Mock the level configuration
    val levelConfig = mock[LevelConfig]
    when(controller.getLevelConfig).thenReturn(Some(levelConfig))
    when(levelConfig.width).thenReturn(5)
    when(levelConfig.height).thenReturn(5)
    when(controller.getSpielfeld.getAnPos(any[Int], any[Int])).thenReturn(' ')

    gameView.refreshGrid()

    // Verify the grid initialization
    gameView.gridPane.children.size should be(25)
  }

  it should "update the grid correctly" in {
    val controller = mock[Controller]
    val gui = mock[GUI]
    val gameView = new GameView(controller, gui)

    // Mock the level configuration
    val levelConfig = mock[LevelConfig]
    when(controller.getLevelConfig).thenReturn(Some(levelConfig))
    when(levelConfig.width).thenReturn(5)
    when(levelConfig.height).thenReturn(5)
    when(controller.getSpielfeld.getAnPos(0, 0)).thenReturn('R')
    when(controller.getSpielfeld.getAnPos(1, 0)).thenReturn('G')
    when(controller.getSpielfeld.getAnPos(2, 0)).thenReturn('J')
    when(controller.getSpielfeld.getAnPos(3, 0)).thenReturn('X')
    when(controller.getSpielfeld.getAnPos(4, 0)).thenReturn(' ')

    gameView.refreshGrid()

    // Verify the grid update
    val cells = gameView.gridPane.children.toArray
    cells(0).asInstanceOf[Label].text.value should be("R")
    cells(1).asInstanceOf[Label].text.value should be("G")
    cells(2).asInstanceOf[Label].text.value should be("J")
    cells(3).asInstanceOf[Label].text.value should be("X")
    cells(4).asInstanceOf[Label].text.value should be(" ")
  }
}