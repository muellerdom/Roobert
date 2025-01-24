import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import Controller.Component.{ControllerBaseImpl, ControllerInterface}
import Model.LevelComponent.{LevelManagerTrait, levelManager}
import Model.REPLComponent.REPLBaseImpl.REPL
import Model.REPLComponent.REPLInterface
import Model.FileIOComponent.FileIOInterface
import Model.FileIOComponent.FileIoJsonImpl.FileIO // Importiere JSON-Implementation
import Model.FileIOComponent.FileIoXmlImpl.FileIO  // Importiere XML-Implementation

class GameModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {

    // LevelManager binden
    bind[LevelManagerTrait].toInstance(levelManager)

    // Controller binden
    bind[ControllerInterface].toInstance(new ControllerBaseImpl.Controller(levelManager))

    // FileIOInterface abhänging von einer Konfiguration
    // Wähle hier zwischen JSON und XML
    val useXml = false // Ändere dies, um XML oder JSON zu aktivieren

    if (useXml) {
      bind[FileIOInterface].toInstance(new Model.FileIOComponent.FileIoXmlImpl.FileIO("src/main/resources/levels.xml"))
    } else {
      bind[FileIOInterface].toInstance(new Model.FileIOComponent.FileIoJsonImpl.FileIO("src/main/resources/levels.json"))
    }
  }
}