package Model.PlayerComponent.PlayerBaseImpl

import Model.LevelComponent.levelManager
import Model.SpielfeldComponent.SpielfeldBaseImpl.{Jerm, Spielfeld}
import Model.SpielfeldComponent.{Coordinate, KomponentenInterface}
import Util.Observable

// Singleton-Object für den Spieler
//MACHT DAS SINN? -> sollte case class sein
//Interface für Spieler lol
//NAMEN NUR IN EINER SPRACHE (BESTENS AUF ENGLISH)
//Immutable machen (values anstatt vars)



object Player extends Observable {

  // Enum für Richtungen
  sealed trait Direction

  case object Oben extends Direction

  case object Rechts extends Direction

  case object Unten extends Direction

  case object Links extends Direction

  private val startPosX = levelManager.getCurrentLevel.get.start.x
  private val startPosY = levelManager.getCurrentLevel.get.start.y

  val inventory = new Inventory()


  //var wegkriegen
  var position: Option[Coordinate] = None
  var direction: Direction = Oben // Start-Richtung
  //var eingesammelteJerms: Set[Coordinate] = Set()

  private val maxX: Int = levelManager.getCurrentLevel.get.width
  private val maxY: Int = levelManager.getCurrentLevel.get.height

  def Symbol: Char = 'R' // Symbol für Spieler

  def initialize(): Unit = {
    direction = Oben // Start-Richtung
    position = Some(Coordinate(startPosX, startPosY)) // Startposition des Spielers
  }

  // Methode, um die aktuelle Position sicher zu erhalten
  def getPosition: Coordinate = {
    position.getOrElse(throw new IllegalStateException("Spieler ist nicht initialisiert!"))
  }

  // Bewegung basierend auf Aktionen
  def move(action: String): Unit = {

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
  }

  // Nach links drehen
  private def turnLeft(): Unit = {
    direction = direction match {
      case Oben => Links
      case Links => Unten
      case Unten => Rechts
      case Rechts => Oben
    }
  }

  // Vorwärts bewegen
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
      position = Some(newPos) //update position

      println(s"Spieler bewegt zu $newPos in Richtung $direction")
      einsammeln(newPos)
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


  // Jerm einsammeln -
  def einsammeln(pos: Coordinate): Unit = {

    //      println("Aktuelle Komponenten:")
    //      Spielfeld.components.foreach { component =>
    //        println(s"- Typ: ${component.getClass.getSimpleName}, Position: ${component.getPosition}")
    //      }
    //


    Spielfeld.components.find(_.getPosition == pos) match {
      case Some(jerm: Jerm) =>
        inventory.addItem(jerm) // Jerm zum Inventar hinzufügen
        Spielfeld.entfernen(pos.x, pos.y) // Jerm vom Spielfeld entfernen
        println(s"Jerm an Position $pos eingesammelt.")
      case _ =>
    }
  }
}

class Inventory {
  private var items: Set[KomponentenInterface] = Set()

  // Objekt hinzufügen
  def addItem(item: KomponentenInterface): Unit = {
    items += (item)
  }

  // Überprüfen, ob ein bestimmtes Item eingesammelt wurde
  def containsItem(name: KomponentenInterface): Boolean =
    items.contains(name)

  // Alle eingesammelten Items abrufen
  def getItems: Set[KomponentenInterface] = items

  // Anzahl der eingesammelten Objekte
  def size: Int = items.size
}