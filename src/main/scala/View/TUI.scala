// TUI.scala
package View

import Controller.Component.ControllerBaseImpl.Controller
import Model.GridComponent.Coordinate
import Util.Observer
import com.google.inject.Inject

class TUI @Inject() (controller: Controller) extends Observer {

  controller.addObserver(this) // Register TUI as an observer

  def start(): Unit = {
    val availableLevels = controller.getAvailableLevels

    println("Help Robert!")
    if (availableLevels.nonEmpty) {
      println("Available levels:")
      availableLevels.foreach(level => println(s"- $level"))

      println("Please enter a level to start:")
      waitForLevelInput()
    } else {
      println("No available levels found. Please check the level file.")
    }
  }

  private def waitForLevelInput(): Unit = {
    val scanner = new java.util.Scanner(System.in)

    var levelName = ""
    do {
      println("Please choose a level or 'q' to quit:")
      levelName = scanner.nextLine().trim
      processInputLine(levelName)
    } while (levelName != "q")
  }

  def processInputLine(input: String): Unit = {
    if (input == "q") {
      println("Exiting the application...")
    } else {
      controller.startLevel(input.toInt) match {
        case Right(foundLevel) =>
          println(s"Starting level ${foundLevel.id}: ${foundLevel.instructions}")
          controller.notifyObservers()
          waitForPlayerActions()
        case Left(errorMessage) =>
          println(errorMessage)
      }
    }
  }


  private def displayGrid(): Unit = {
    controller.getLevelConfig match {
      case Some(level) =>
        println("Game board:")
        println("+" + ("---+" * level.logic.gridSize.get.width))
        for (y <- level.logic.gridSize.get.height - 1 to 0 by -1) {
          for (x <- 0 until level.logic.gridSize.get.width) {
            val symbol = controller.getGrid.gridSegments.findByPosition(Coordinate(x, y)).get.Symbol
            print(s"| $symbol ")
          }
          println("|")
          println("+" + ("---+" * level.logic.gridSize.get.width))
        }
      case None =>
        println("No current level loaded.")
    }
  }

  def waitForPlayerActions(): Unit = {
    val scanner = new java.util.Scanner(System.in)
    var action = " "
    val codeBlock = new StringBuilder

    println("Enter a command (q to quit, z to undo, y to redo, compile to execute commands):")
    println("Available commands: moveUp(), moveDown(), moveLeft(), moveRight()")


    do {
      action = scanner.nextLine().trim

      action match {
        case "q" =>
          println("Game ended.")

        case "z" =>
          controller.undo()

        case "y" =>
          controller.redo()

        case "compile" =>

          val code = codeBlock.toString()
          controller.interpret(code) // Grid wird durch update() nachgeführt
          codeBlock.clear()

          controller.notifyObservers() // Notify observers after processing input


        case _ =>
          codeBlock.append(action).append("\n")

          //controller.notifyObserversGUIOnly() // Synchronisiere NUR die GUI

          //println(s"Unknown command: $action")
      }

    } while (action.toLowerCase != "q")

    println("Game ended.")
  }

  override def update(): Unit = {
    if (controller.getLevelConfig.isDefined) { // Aktualisiere nur, wenn ein Level geladen ist
      //controller.notifyObservers()
      println("Update called from TUI")
      displayGrid()
    }
  }
}