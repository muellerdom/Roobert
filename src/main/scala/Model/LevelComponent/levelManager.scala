package Model.LevelComponent

import Util.Observable

/**
 * zum Managen der Level u.a. auch das aktuelle Level
 * somit wird das auch vom Controller entkoppelt
 */

object levelManager extends Observable with LevelManagerTrait {

  // intern für den Schutz der Levels zuständig
  private val levelLoader = loadLeveFromJSON
  private val levels: List[LevelConfig] = levelLoader.getLevels.map(_.levels).getOrElse(List())
  private var currentLevel: Option[LevelConfig] = None

  // Nur für den Controller direkt nutzbar; nicht öffentlich machen
  def ladeLevel(levelName: String): Either[String, LevelConfig] = {
    levels.find(_.level == levelName) match {
      case Some(level) =>
        currentLevel = Some(level)
        //notifyObservers()
        Right(level)
      case None => Left(s"Level '$levelName' nicht gefunden.")
    }
  }

  def getAvailableLevels: List[String] = levels.map(_.level)

  def getCurrentLevel: Option[LevelConfig] = currentLevel

  override def loadLevel(levelName: String): Either[String, LevelConfig] = {
    ladeLevel(levelName)
  }}