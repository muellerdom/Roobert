package Controller.Component.ControllerBaseImpl


import Controller.Component.ControllerInterface
import Model.FileIOComponent.FileIoJsonImpl.{FileIO, LevelConfig}
import Model.GridComponent.GridBaseImpl._
import Model.GridComponent.{Coordinate, GridInterface}
import Model.LevelComponent.LevelManagerTrait
import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.REPLComponent.REPLBaseImpl.REPL
import Util.{Observable, UndoManager}
import com.google.inject.Inject
import scalafx.application.Platform

class Controller @Inject()(levelManager: LevelManagerTrait) extends ControllerInterface with Observable {

  private val undoManager = new UndoManager

  private var grid: Grid = Grid(GridSegments(List())) // Initialisiert mit leerem Grid


  def startLevel(levelId: Int): Either[String, LevelConfig] = {
    levelManager.loadLevel(levelId) match {
      case Right(level) =>
        val entities = level.logic.entities
        val gridSegments: GridSegments = GridSegments(
          (for {
            x <- 0 until level.logic.gridSize.get.width
            y <- 0 until level.logic.gridSize.get.height
          } yield {
            entities.find(_.position == Coordinate(x, y)) match {
              case Some(e) if e.`type` == "player" => Player(e.position)
              case Some(e) if e.`type` == "goal" => Goal(e.position)
              case Some(e) if e.`type` == "obstacle" => new Obstacle(e.position, e.subtype.toString)
              case Some(e) if e.`type` == "jerm" => new Jerm(e.position)
              case _ => emptyField(Coordinate(x, y))
            }
          }).toList
        )

        grid = Grid(gridSegments)

        new FileIO("src/main/resources/gameState.json").save(grid)

        REPL.replBind(this)
       // notifyObservers()
        Right(level)

      case Left(error) =>
        Left(error)
    }
  }

  def getAvailableLevels: List[String] = levelManager.getAvailableLevels.map(_.toString)


  def setCommand(action: String): Unit = {
    val command = new SetCommand(action, this)
    undoManager.doStep(command)
    //notifyObservers()
  }

  def undo(): Unit = {
    undoManager.undoStep
    notifyObservers()
  }

  def redo(): Unit = {
    undoManager.redoStep
    notifyObservers()
  }

  // Prüfen, ob die neue Position gültig ist
//  private def isValidMove(position: Coordinate): Boolean = {
//    grid.gridSegments.findByPosition(position) match {
//      case Some(segment) => !segment.isBlocking
//      case None => false // Position außerhalb des Grids
//    }
//  }

  def interpret(code: String): Unit = {
    REPL.Interpret(code)
    //setCommand("forward")
  }


  def isLevelComplete: Boolean = {
    //    levelManager.getCurrentLevel.exists { level =>
    //      val goal = level.logic.entities.find(_.type == "goal").map(_.position)
    //      val collectedAllJerms = Player.collectedEntityCount == level.logic.entities.count(_.type == "jerm")
    //      collectedAllJerms && goal.exists(Player.hasReachedGoal)
    //    }
    true
  }

  def getGrid: GridInterface = grid // Zugriff auf den aktuellen Grid-Zustand

  def updateGrid(updatedGrid: Grid): Unit = {
    grid = updatedGrid // Grid mit neuer Instanz ersetzen
    //notifyObservers() // Änderungen melden
  }

  def getLevelConfig: Option[LevelConfig] = levelManager.getCurrentLevel
}