import Util.Observer
import apple.laf.JRSUIConstants.Orientation

import scala.swing._
import scala.swing.event._
import java.awt.{Color, Dimension}

// Abstrakte Klasse für das Stage-Pattern
trait GameStage {
  def enter(): Unit
  def exit(): Unit
}
/*
// Haupt-Frame als Singleton und Observer
abstract class MainFrameTemplate extends Observer {

  protected val frame = new MainFrame {
    title = "Galgenmännchen - Hauptmenü"
    preferredSize = new Dimension(600, 400)
  }
  protected val sideMenu = new BoxPanel(Orientation.Vertical) {
    background = new Color(108, 178, 243)
  }
  protected val level1Button = new Button("Level 1")
  protected val level2Button = new Button("Level 2")

  protected var currentPanel: Panel = new FlowPanel() // Standard Panel

  // Aktuelle Stage des Spiels
  protected var currentStage: GameStage = _

  // Template-Methode: Definiert den Ablauf
  final def initializeFrame(): Unit = {
    setupFrame()          // Allgemeine Frame-Einstellungen
    setupSideMenu()       // Konfiguration des Seitenmenüs
    setupListeners()      // Event-Listener hinzufügen
    setupMainPanel()      // Hauptinhalt konfigurieren
    finalizeFrame()       // Frame sichtbar machen
  }

  // Abstrakte Methoden für die spezifischen Details
  protected def setupListeners(): Unit
  protected def setupMainPanel(): Unit

  // Default-Implementierungen für Frame- und SideMenu-Setup
  protected def setupFrame(): Unit = {
    frame.peer.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
    frame.background = new Color(0x6CB2F3)
  }

  protected def setupSideMenu(): Unit = {
    sideMenu.contents += level1Button
    sideMenu.contents += level2Button
    frame.contents = new BorderPanel {
      layout(sideMenu) = BorderPanel.Position.West
      layout(currentPanel) = BorderPanel.Position.Center
    }
  }

  protected def finalizeFrame(): Unit = {
    frame.visible = true
  }

  // Methoden zur Verwaltung der aktuellen Stage
  protected def setCurrentStage(stage: GameStage): Unit = {
    if (currentStage != null) currentStage.exit() // Vorherige Stage verlassen
    currentStage = stage
    currentStage.enter() // Neue Stage betreten
  }
}

// Konkrete Implementierungen der Stages
class MenuStage(mainFrame: MainFrameTemplate) extends GameStage {

  override def enter(): Unit = {
    mainFrame.currentPanel = new BoxPanel(Orientation.Vertical) {
      contents += new Label("Willkommen zum Galgenmännchen-Spiel!")
      contents += mainFrame.level1Button
      contents += mainFrame.level2Button
    }
    mainFrame.frame.contents = new BorderPanel {
      layout(mainFrame.sideMenu) = BorderPanel.Position.West
      layout(mainFrame.currentPanel) = BorderPanel.Position.Center
    }
  }

  override def exit(): Unit = {
    // Hier könnte die Logik zum Verlassen des Menüs kommen (z.B. alle Buttons deaktivieren)
  }
}

class PlayLevel1Stage(mainFrame: MainFrameTemplate) extends GameStage {

  override def enter(): Unit = {
    mainFrame.currentPanel = new BoxPanel(Orientation.Vertical) {
      contents += new Label("Level 1 wird gespielt...")
      val backButton = new Button("Zurück zum Menü")
      contents += backButton

      listenTo(backButton)
      reactions += {
        case ButtonClicked(`backButton`) =>
          mainFrame.setCurrentStage(new MenuStage(mainFrame)) // Zurück zum Menü
      }
    }
    mainFrame.frame.contents = new BorderPanel {
      layout(mainFrame.sideMenu) = BorderPanel.Position.West
      layout(mainFrame.currentPanel) = BorderPanel.Position.Center
    }
  }

  override def exit(): Unit = {
    // Optional: Level 1 beenden oder vorherige Logik ausführen
  }
}

class PlayLevel2Stage(mainFrame: MainFrameTemplate) extends GameStage {

  override def enter(): Unit = {
    mainFrame.currentPanel = new BoxPanel(Orientation.Vertical) {
      contents += new Label("Level 2 wird gespielt...")
      val backButton = new Button("Zurück zum Menü")
      contents += backButton

      listenTo(backButton)
      reactions += {
        case ButtonClicked(`backButton`) =>
          mainFrame.setCurrentStage(new MenuStage(mainFrame)) // Zurück zum Menü
      }
    }
    mainFrame.frame.contents = new BorderPanel {
      layout(mainFrame.sideMenu) = BorderPanel.Position.West
      layout(mainFrame.currentPanel) = BorderPanel.Position.Center
    }
  }

  override def exit(): Unit = {
    // Optional: Level 2 beenden oder vorherige Logik ausführen
  }
}

// Singleton für das MainFrame
object MainFrameSingleton {

  private var instance: Option[MainFrameTemplate] = None

  def getInstance: MainFrameTemplate = synchronized {
    if (instance.isEmpty) {
      val newFrame = new MainFrameTemplate {
        override protected def setupListeners(): Unit = {
          level1Button.reactions += {
            case ButtonClicked(_) =>
              setCurrentStage(new PlayLevel1Stage(this)) // Wechselt zu Level 1
          }
          level2Button.reactions += {
            case ButtonClicked(_) =>
              setCurrentStage(new PlayLevel2Stage(this)) // Wechselt zu Level 2
          }
        }

        override protected def setupMainPanel(): Unit = {
          // Start mit dem Menü
          setCurrentStage(new MenuStage(this))
        }
      }
      newFrame.initializeFrame() // Template-Methode aufrufen
      instance = Some(newFrame)
    }
    instance.get
  }
}

// Initialisierung des Spiels
object GameApp extends App {
  MainFrameSingleton.getInstance // Initialisiere das Hauptfenster
}
*/