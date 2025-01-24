import Controller.Component.{ControllerInterface, ControllerBaseImpl}
import Model.LevelComponent.{LevelManagerTrait, levelManager}
import com.google.inject.{AbstractModule, Guice, Injector}
import net.codingwell.scalaguice.ScalaModule
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GameModuleSpec extends AnyFlatSpec with Matchers {

  "GameModule" should "bind LevelManagerTrait and ControllerInterface" in {
    val injector: Injector = Guice.createInjector(new GameModule)
    val levelManagerInstance = injector.getInstance(classOf[LevelManagerTrait])
    val controllerInstance = injector.getInstance(classOf[ControllerInterface])

    levelManagerInstance shouldBe levelManager
    controllerInstance shouldBe a[ControllerBaseImpl.Controller]
  }
}