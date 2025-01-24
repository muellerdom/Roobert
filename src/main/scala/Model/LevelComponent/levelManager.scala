package Model.LevelComponent

import Model.FileIOComponent.FileIoJsonImpl.{FileIO, LevelConfig}
import Model.GridComponent.Coordinate
import Util.Observable


object levelManager extends Observable with LevelManagerTrait {

  // intern für den Schutz der Levels zuständig
  private val levelLoader = new FileIO("src/main/resources/levels.json")
  private val levels: List[LevelConfig] = levelLoader.getLevels.map(_.levels).getOrElse(List())
  private var currentLevel: Option[LevelConfig] = None

  // Nur für den Controller direkt nutzbar; nicht öffentlich machen
  def loadLevel(levelId: Int): Either[String, LevelConfig] = {
    levels.find(_.id == levelId) match {
      case Some(level) =>
        currentLevel = Some(level)
        //notifyObservers()
        Right(level)
      case None => Left(s"Level mit ID '$levelId' nicht gefunden.")
    }
  }

  def getAvailableLevels: List[Int] = levels.map(_.id)

  def getCurrentLevel: Option[LevelConfig] = currentLevel

}
