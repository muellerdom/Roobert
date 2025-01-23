import Controller.Component.ControllerBaseImpl.Controller
import View.TUI
import View.gui.{GUI, GameView}
import com.google.inject.{Guice, Injector}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.mockito.Mockito._
import org.scalatestplus.mockito.MockitoSugar
import javafx.application.Platform
import org.powermock.api.mockito.PowerMockito.whenNew

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class MainSpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "Main" should "initialize and notify observers" in {
    val injector = mock[Injector]
    val controller = mock[Controller]
    val gui = mock[GUI]
    val gameView = mock[GameView]
    val tui = mock[TUI]

    when(injector.getInstance(classOf[Controller])).thenReturn(controller)
    when(injector.getInstance(classOf[GUI])).thenReturn(gui)
    when(injector.getInstance(classOf[GameView])).thenReturn(gameView)
    whenNew(classOf[TUI]).withArguments(controller).thenReturn(tui)

    Platform.startup(() => {
      Main.main(Array.empty)

      verify(controller).addObserver(gameView)
      verify(controller).addObserver(tui)
      verify(controller).addObserver(gui)
      verify(controller).notifyObservers()
    })
  }
}