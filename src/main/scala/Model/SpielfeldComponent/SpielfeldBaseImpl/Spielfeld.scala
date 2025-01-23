package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.LevelComponent.levelManager.getCurrentLevel
import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.SpielfeldComponent.{Coordinate, KomponentenInterface, SpielfeldInterface}
import Util.Observable
import scala.util.Random

object Spielfeld extends Observable with SpielfeldInterface {

  private val level = getCurrentLevel.get
  private val random = new Random()

  // Initialize components only once at the beginning
  var components: List[KomponentenInterface] = initializeComponents()

  private var spielerPosition: Option[Coordinate] = Some(level.start) // Set initial player position

  private def initializeComponents(): List[KomponentenInterface] = {
    val goal = new Ziel(Coordinate(level.goal.x, level.goal.y))
    val obstacles = level.objects.obstacles.map(_ => new Hindernis(randomCoordinate()))
    val jerms = level.objects.jerm.map(_ => new Jerm(randomCoordinate()))
    goal :: obstacles ::: jerms
  }

  private def randomCoordinate(): Coordinate = {
    Coordinate(random.nextInt(level.width), random.nextInt(level.height))
  }

  def resetComponents(): Unit = {
    components = initializeComponents()
    spielerPosition = Some(level.start)
    notifyObservers()
  }

  override def entfernen(x: Int, y: Int): Unit = {
    components = components.filterNot(_.getPosition == Coordinate(x, y))
    if (spielerPosition.contains(Coordinate(x, y))) spielerPosition = None
    notifyObservers()
  }

  override def getAnPos(x: Int, y: Int): Char = {
    if (spielerPosition.contains(Coordinate(x, y))) 'R'
    else components.find(_.getPosition == Coordinate(x, y)).map(_.Symbol).getOrElse(' ')
  }

  override def getSpielfeld: Array[Array[Char]] = {
    val grid = Array.fill(level.width, level.height)(' ')
    components.foreach(c => grid(c.getPosition.x)(c.getPosition.y) = c.Symbol)

    Player.position.foreach(pos => grid(pos.x)(pos.y) = Player.Symbol)
    grid.clone()
  }

  override def getSpielerPosition: Option[Coordinate] = spielerPosition

  override def setSpielerPosition(pos: Coordinate): Unit = {
    spielerPosition = Some(pos)
    notifyObservers()
  }
}