abstract class ViewStage {
  def execute(): Unit
}


// ViewStageManager zur Verwaltung der Phasen
class ViewStageManager(viewStages: List[ViewStage]) {
  private var stages: List[ViewStage] = viewStages

  def executeAll(): Unit = {
    stages.foreach(_.execute()) // Führt alle Phasen in der Reihenfolge aus
  }

  // Möglichkeit, eine neue Phase hinzuzufügen oder bestehende zu entfernen
  def addStage(stage: ViewStage): Unit = {
    stages = stages :+ stage
  }

  def removeStage(stage: ViewStage): Unit = {
    stages = stages.filterNot(_ == stage)
  }

  // Möglichkeit, die Reihenfolge der Phasen zu ändern
  def setStages(newStages: List[ViewStage]): Unit = {
    stages = newStages
  }

  abstract class ViewStage {
    def execute(): Unit
  }




}
