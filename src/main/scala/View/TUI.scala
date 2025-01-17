package View

import Controller.Component.ControllerBaseImpl.Controller
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
      controller.startLevel(input) match {
        case Right(foundLevel) =>
          println(s"Starting level ${foundLevel.level}: ${foundLevel.description}")
          displayGrid()
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
        println("+" + ("---+" * level.width))
        for (y <- level.height - 1 to 0 by -1) {
          for (x <- 0 until level.width) {
            val symbol = controller.getSpielfeld.getSpielfeld(x)(y)
            print(s"| $symbol ")
          }
          println("|")
          println("+" + ("---+" * level.width))
        }
      case None =>
        println("No current level loaded.")
    }
  }

  def waitForPlayerActions(): Unit = {
    val scanner = new java.util.Scanner(System.in)
    var action = ""

    do {
      println("Enter a command (q to quit, z to undo, y to redo, compile to execute commands):")
      println("Available commands: moveUp(), moveDown(), moveLeft(), moveRight()")
      action = scanner.nextLine().trim

      action.toLowerCase match {
        case "q" =>
          println("Game ended.")

        case "z" =>
          controller.undo()

        case "y" =>
          controller.redo()

        case "compile" =>
          controller.setCommand(action)

          if (controller.isLevelComplete) {
            println("Congratulations! Robert has arrived!")
            start()
          }

        case "moveup()" =>
          controller.moveUp()

        case "movedown()" =>
          controller.moveDown()

        case "moveleft()" =>
          controller.moveLeft()

        case "moveright()" =>
          controller.moveRight()

        case _ =>
          println(s"Unknown command: $action")
      }

    } while (action.toLowerCase != "q")

    println("Game ended.")
  }

  override def update(): Unit = {
    displayGrid()
    if (controller.isInvalidMove) {
      println("Invalid move! You cannot move over an obstacle.")
    }
    if (controller.isJermCollected) {
      println("hurrah hurrah")
    }
  }
}