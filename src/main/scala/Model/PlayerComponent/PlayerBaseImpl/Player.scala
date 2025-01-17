package Model.PlayerComponent.PlayerBaseImpl

import Model.LevelComponent.levelManager
import Model.SpielfeldComponent.SpielfeldBaseImpl.{Jerm, Spielfeld}
import Model.SpielfeldComponent.{Coordinate, KomponentenInterface}
import Util.Observable

object Player extends Observable {

  sealed trait Direction
  case object Oben extends Direction
  case object Rechts extends Direction
  case object Unten extends Direction
  case object Links extends Direction

  private val startPosX = levelManager.getCurrentLevel.get.start.x
  private val startPosY = levelManager.getCurrentLevel.get.start.y

  val inventory = new Inventory()

  var position: Option[Coordinate] = None
  var direction: Direction = Oben

  private val maxX: Int = levelManager.getCurrentLevel.get.width
  private val maxY: Int = levelManager.getCurrentLevel.get.height

  def Symbol: Char = 'R'

  def initialize(): Unit = {
    direction = Oben
    position = Some(Coordinate(startPosX, startPosY))
  }

  def getPosition: Coordinate = {
    position.getOrElse(throw new IllegalStateException("Spieler ist nicht initialisiert!"))
  }

  def moveUp(): Unit = {
    move("up")
  }

  def moveDown(): Unit = {
    move("down")
  }

  def moveLeft(): Unit = {
    move("left")
  }

  def moveRight(): Unit = {
    move("right")
  }

  private def move(action: String): Unit = {
    action match {
      case "up" => moveForward()
      case "right" => turnRight()
      case "left" => turnLeft()
      case "down" => turnDown()
      case _ => println("Unbekannte Aktion!")
    }
  }

  private def turnRight(): Unit = {
    direction = direction match {
      case Oben => Rechts
      case Rechts => Unten
      case Unten => Links
      case Links => Oben
    }
  }

  private def turnLeft(): Unit = {
    direction = direction match {
      case Oben => Links
      case Links => Unten
      case Unten => Rechts
      case Rechts => Oben
    }
  }

  private def turnDown(): Unit = {
    direction = direction match {
      case Oben => Unten
      case Unten => Oben
      case Links => Rechts
      case Rechts => Links
    }
  }

  private def moveForward(): Unit = {
    println(s"Aktuelle Position: ${getPosition}, Richtung: $direction")

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
      notifyObservers() // Notify observers when an invalid move is attempted
    }
  }

  def isValidMove(pos: Coordinate): Boolean = {
    val withinBounds = pos.x >= 0 && pos.x < maxX && pos.y >= 0 && pos.y < maxY
    val isObstacle = levelManager.getCurrentLevel.get.objects.obstacles.exists(_.coordinates == pos)
    withinBounds && !isObstacle
  }

  def einsammeln(pos: Coordinate): Unit = {
    Spielfeld.components.find(_.getPosition == pos) match {
      case Some(jerm: Jerm) =>
        inventory.addItem(jerm)
        Spielfeld.entfernen(pos.x, pos.y)
        println(s"Jerm an Position $pos eingesammelt.")
        notifyObservers() // Notify observers when a Jerm is collected
      case _ =>
    }
  }
}

class Inventory {
  private var items: Set[KomponentenInterface] = Set()

  def addItem(item: KomponentenInterface): Unit = {
    items += item
  }

  def containsItem(name: KomponentenInterface): Boolean =
    items.contains(name)

  def getItems: Set[KomponentenInterface] = items

  def size: Int = items.size
}