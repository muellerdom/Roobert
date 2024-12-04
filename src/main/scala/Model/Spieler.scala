package Model

import Util.Observable

// Singleton-Object für den Spieler
object Spieler extends Observable {

  // Enum für Richtungen
  sealed trait Direction

  case object Oben extends Direction

  case object Rechts extends Direction

  case object Unten extends Direction

  case object Links extends Direction

  private var initialized = false // Kontrollflag für Initialisierung
  var position: Option[Coordinate] = None // Startposition des Spielers
  var direction: Direction = Oben // Start-Richtung
  var eingesammelteJerms: Set[Coordinate] = Set()


  private val maxX: Int = levelManager.getCurrentLevel.get.width
  private val maxY: Int = levelManager.getCurrentLevel.get.height

  // Initialisierungsmethode
  def initialize(startX: Int, startY: Int): Unit = {

    position = Some(Coordinate(startX, startY))
    initialized = true
    println(s"Spieler initialisiert bei ($startX, $startY)")
  }

  // Methode, um die aktuelle Position sicher zu erhalten
  def getPosition: Coordinate = {
    position.getOrElse(throw new IllegalStateException("Spieler ist nicht initialisiert!"))
  }

  // Bewegung basierend auf Aktionen
  def move(action: String): Unit = {
    if (!initialized) throw new IllegalStateException("Spieler ist nicht initialisiert!")
    action match {
      case "forward" => moveForward()
      case "right" => turnRight()
      case "left" => turnLeft()
      case _ => println("Unbekannte Aktion!")
    }
  }

  // Nach rechts drehen
  private def turnRight(): Unit = {
    direction = direction match {
      case Oben => Rechts
      case Rechts => Unten
      case Unten => Links
      case Links => Oben
    }
    notifyObservers()
  }

  // Nach links drehen
  private def turnLeft(): Unit = {
    direction = direction match {
      case Oben => Links
      case Links => Unten
      case Unten => Rechts
      case Rechts => Oben
    }
    notifyObservers()
  }

  // Vorwärts bewegen
  private def moveForward(): Unit = {
    val currentPosition = getPosition
    val newPos = direction match {
      case Oben => Coordinate(currentPosition.x, currentPosition.y + 1)
      case Rechts => Coordinate(currentPosition.x + 1, currentPosition.y)
      case Unten => Coordinate(currentPosition.x, currentPosition.y - 1)
      case Links => Coordinate(currentPosition.x - 1, currentPosition.y)
    }

    if (isValidMove(newPos)) {
      position = Some(newPos)
      println(s"Spieler bewegt zu $newPos in Richtung $direction")
      einsammeln(newPos)
      notifyObservers()
    } else {
      println("Ungültige Bewegung blockiert!")
    }
  }

  // Überprüfen, ob Bewegung gültig ist
  private def isValidMove(pos: Coordinate): Boolean = {
    val withinBounds = pos.x >= 0 && pos.x < maxX && pos.y >= 0 && pos.y < maxY
    val isObstacle = levelManager.getCurrentLevel.get.objects.obstacles.exists(_.coordinates == pos)
    withinBounds && !isObstacle
  }

  // Jerm einsammeln
  def einsammeln(pos: Coordinate): Unit = {
    val level = levelManager.getCurrentLevel.get
    level.objects.jerm.find(_.coordinates == pos) match {
      case Some(jerm) =>
        eingesammelteJerms += pos
        //---- Jedesmal wenn sich der SPieler über das Feld wo ein Jerm sein soll bewegt, sammelt er ihn ein
        //--> infinite jerm glitch
        //Dies ist zu korrigieren
        //level.objects.jerm = level.objects.jerm.filterNot(_.coordinates == pos) // Entferne eingesammelten Jerm
        println(s"Jerm an Position $pos eingesammelt.")
        notifyObservers()
      case None =>
        println("Kein Jerm an dieser Position.")
    }
  }

  override def toString: String = s"Spieler(Position: ${getPosition}, Richtung: $direction)"
}