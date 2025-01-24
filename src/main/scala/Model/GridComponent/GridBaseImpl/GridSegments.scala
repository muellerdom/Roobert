package Model.GridComponent.GridBaseImpl

import Model.GridComponent.{Coordinate, GridSegmentsInterface}

case class GridSegments(segments: List[GridSegmentsInterface] = List()) {

  /**
   * Fügt ein neues Segment (oder den Spieler) zur Liste hinzu und gibt eine neue Instanz zurück.
   */
  def add(segment: GridSegmentsInterface): GridSegments = {
    copy(segments = segments :+ segment)
  }

  /**
   * Entfernt ein Segment aus der Liste.
   */
  def remove(segment: GridSegmentsInterface): GridSegments = {
    val emptySegment = emptyField(segment.getPosition)
    copy(segments = segments.map {
      case s if s == segment => emptySegment
      case s => s
    })
  }

  /**
   * Aktualisiert ein Segment innerhalb der Liste.
   */
  def update(oldSegment: GridSegmentsInterface, newSegment: GridSegmentsInterface): GridSegments = {
    val clearedGrid = clearSegment(oldSegment.getPosition)
    clearedGrid.copy(segments = clearedGrid.segments.map {
      case s if s.getPosition == newSegment.getPosition => newSegment
      case s => s
    })
  }

  /**
   * Entfernt ein Segment, indem es durch ein leeres Feld ausgetauscht wird.
   */
  def clearSegment(position: Coordinate): GridSegments = {
    copy(segments = segments.map {
      case s if s.getPosition == position => emptyField(position)
      case s => s
    })
  }

  /**
   * Sucht ein Segment an einer bestimmten Position.
   */
  def findByPosition(position: Coordinate): Option[GridSegmentsInterface] = {
    segments.find(_.getPosition == position)
  }
}