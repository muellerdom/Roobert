package Model.LevelComponent

import Model.FileIOComponent.FileIoJsonImpl.LevelConfig

trait LevelManagerTrait {
  def loadLevel(levelId: Int): Either[String, LevelConfig]
  def getAvailableLevels: List[Int]
  def getCurrentLevel: Option[LevelConfig]
}