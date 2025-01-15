package Model.SpielfeldComponent.SpielfeldBaseImpl

import Model.LevelComponent.levelManager.getCurrentLevel
import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.SpielfeldComponent.{Coordinate, KomponentenInterface, SpielfeldInterface}
import Util.Observable

object Spielfeld extends Observable with SpielfeldInterface {

  private val level = getCurrentLevel.get

  var components: List[KomponentenInterface] = List(
    new Ziel(Coordinate(level.goal.x, level.goal.y)),
  ) ++ level.objects.obstacles.map(o => new Hindernis(o.coordinates)) ++
    level.objects.jerm.map(jerm => new Jerm(jerm.coordinates))
  // Liste aller Spielfeldkomponenten

  private var spielerPosition: Option[Coordinate] = None


  override def entfernen(x: Int, y: Int): Unit = {
    components = components.filterNot(_.getPosition == Coordinate(x, y))
    if (spielerPosition.contains(Coordinate(x, y))) spielerPosition = None
    notifyObservers()
  }

//  def getCoords(symbold: Char): Coordinate = {
//    components.filter(_.Symbol == symbold).map(_.getPosition).head
//  }

  override def getAnPos(x: Int, y: Int): Char = {
    if (spielerPosition.contains(Coordinate(x, y))) 'R'
    else components.find(_.getPosition == Coordinate(x, y)).map(_.Symbol).getOrElse(' ')
  }

  override def getSpielfeld: Array[Array[Char]] = {
    val grid = Array.fill(getCurrentLevel.get.width, getCurrentLevel.get.height)(' ')
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
