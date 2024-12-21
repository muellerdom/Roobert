package Model.SpielfeldComponent.SpielfeldMockImpl

import Model.LevelComponent.levelManager.getCurrentLevel
import Model.SpielerComponent.PlayerBaseImpl.Spieler
import Model.SpielfeldComponent.{Coordinate, KomponentenInterface, SpielfeldInterface}
import Util.Observable

class MockSpielfeld extends Observable with SpielfeldInterface {

  private val level = getCurrentLevel.get

  var components: List[KomponentenInterface] = List.empty

  private var spielerPosition: Option[Coordinate] = None

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
    val grid = Array.fill(getCurrentLevel.get.width, getCurrentLevel.get.height)(' ')
    components.foreach(c => grid(c.getPosition.x)(c.getPosition.y) = c.Symbol)

    Spieler.position.foreach(pos => grid(pos.x)(pos.y) = Spieler.Symbol)
    grid.clone()
  }

  override def getSpielerPosition: Option[Coordinate] = spielerPosition

  override def setSpielerPosition(pos: Coordinate): Unit = {
    spielerPosition = Some(pos)
    notifyObservers()
  }
}