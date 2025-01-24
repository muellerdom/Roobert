package Model.GridComponent.GridBaseImpl

import Model.GridComponent.{Coordinate, GridSegmentsInterface}

case class emptyField(position: Coordinate) extends GridSegmentsInterface {
  override def getPosition: Coordinate = position

  override def Symbol: Char = ' ' // Leerzeichen repräsentiert ein leeres Feld

  override def isBlocking: Boolean = false

  override def interactWithPlayer(): Unit = {

  }

  override def segmentType: String = "empty"

  override def subType: Option[String] = None
}