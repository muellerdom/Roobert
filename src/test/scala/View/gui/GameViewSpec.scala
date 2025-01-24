/*package View.gui

import Controller.Component.ControllerBaseImpl.Controller
import Model.LevelComponent.LevelConfig
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalafx.scene.control.Label

class GameViewSpec extends AnyFlatSpec with Matchers with MockFactory {

  "A GameView" should "initialize the grid correctly" in {
    val controller = mock[Controller]
    val gui = mock[GUI]
    val gameView = new GameView(controller, gui)

    // Mock the level configuration
    val levelConfig = mock[LevelConfig]
    (controller.getLevelConfig _).expects().returning(Some(levelConfig))
    (levelConfig.width _).expects().returning(5)
    (levelConfig.height _).expects().returning(5)
    (controller.getSpielfeld.getAnPos _).expects(*, *).returning(' ')

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
    (controller.getLevelConfig _).expects().returning(Some(levelConfig))
    (levelConfig.width _).expects().returning(5)
    (levelConfig.height _).expects().returning(5)
    (controller.getSpielfeld.getAnPos _).expects(0, 0).returning('R')
    (controller.getSpielfeld.getAnPos _).expects(1, 0).returning('G')
    (controller.getSpielfeld.getAnPos _).expects(2, 0).returning('J')
    (controller.getSpielfeld.getAnPos _).expects(3, 0).returning('X')
    (controller.getSpielfeld.getAnPos _).expects(4, 0).returning(' ')

    gameView.refreshGrid()

    // Verify the grid update
    val cells = gameView.gridPane.children.toArray
    cells(0).asInstanceOf[Label].text.value should be("R")
    cells(1).asInstanceOf[Label].text.value should be("G")
    cells(2).asInstanceOf[Label].text.value should be("J")
    cells(3).asInstanceOf[Label].text.value should be("X")
    cells(4).asInstanceOf[Label].text.value should be(" ")
  }
}*/