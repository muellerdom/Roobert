import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import Controller.Component.{ControllerInterface, ControllerBaseImpl}
import Model.LevelComponent.{LevelManagerTrait, levelManager}

class GameModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[LevelManagerTrait].toInstance(levelManager)
    bind[ControllerInterface].toInstance(new ControllerBaseImpl.Controller(levelManager))
  }
}