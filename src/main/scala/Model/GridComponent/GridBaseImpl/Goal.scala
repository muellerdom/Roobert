package Model.GridComponent.GridBaseImpl

import Model.PlayerComponent.PlayerBaseImpl.Player
import Model.GridComponent.{Coordinate, GridSegmentsInterface}


case class Goal(position: Coordinate) extends GridSegmentsInterface {
  override def getPosition: Coordinate = position

  override def Symbol: Char = 'G' // Symbol für das Ziel

  override def isBlocking: Boolean = false

  //Das scheint mir unwichtig
  override def interactWithPlayer(): Unit = {
    //    if (Player.getPosition == position) {
    //      println("Herzlichen Glückwunsch! Du hast das Ziel erreicht!")
    //    }
  }

  override def segmentType: String = "goal"

  override def subType: Option[String] = None
}

