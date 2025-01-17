package Model.LevelComponent

trait LevelManagerTrait {
  def loadLevel(levelName: String): Either[String, LevelConfig]
  def getAvailableLevels: List[String]
  def getCurrentLevel: Option[LevelConfig]
}