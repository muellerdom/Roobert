package View

import javax.swing._
import java.awt._
import Controller.GameController
import Util.Observer

// MainFrame als Singleton und Observer
abstract class MainFrameTemplate private() extends Observer {

  protected val frame = new JFrame("Galgenmännchen - Hauptmenü")
  protected val sideMenu = new JPanel()
  protected val mainPanel = new JPanel(new BorderLayout())
  protected val level1Button = new JButton("Level 1")
  protected val level2Button = new JButton("Level 2")
  protected val gameController = GameControllerSingleton.getInstance() // Singleton Instanz des Controllers

  protected var currentPanel: JPanel = _

  // Template-Methode: Definiert den Ablauf
  final def initializeFrame(): Unit = {
    setupFrame()          // Allgemeine Frame-Einstellungen
    setupSideMenu()       // Konfiguration des Seitenmenüs
    setupMainPanel()      // Hauptinhalt konfigurieren
    setupListeners()      // Event-Listener hinzufügen
    finalizeFrame()       // Frame sichtbar machen
  }

  // Abstrakte Methoden für die spezifischen Details
  protected def setupMainPanel(): Unit
  protected def setupListeners(): Unit

  // Default-Implementierungen für Frame- und SideMenu-Setup
  protected def setupFrame(): Unit = {
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setSize(600, 400)
    frame.getContentPane.setBackground(new Color(0x6CB2F3))
  }

  protected def setupSideMenu(): Unit = {
    sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS))
    sideMenu.setBackground(new Color(108, 178, 243))
    sideMenu.setPreferredSize(new Dimension((frame.getWidth * 0.2).toInt, frame.getHeight))

    sideMenu.add(level1Button)
    sideMenu.add(level2Button)
    frame.getContentPane.add(sideMenu, BorderLayout.WEST)
  }

  protected def finalizeFrame(): Unit = {
    frame.setVisible(true)
  }

  // Observer-Methode
  override def update(): Unit = {
    println("Das Spiel hat sich geändert. Das Hauptfenster wird aktualisiert.")
    if (GameController.isLevelCompleted) {
      level1Button.setEnabled(true)
      level2Button.setEnabled(true)
      println("Level abgeschlossen! Weiter zu Level 2.")
    } else {
      level1Button.setEnabled(false)
      println("Level 1 läuft noch.")
    }
  }

  // Methode zum Aktualisieren des MainPanels
  protected def updateMainPanel(): Unit = {
    mainPanel.removeAll()
    mainPanel.add(currentPanel, BorderLayout.CENTER)
    frame.revalidate()
    frame.repaint()
  }
}

// Konkrete Implementierung des MainFrames für Level 1
class MainFrameLevel1 extends MainFrameTemplate {

  // Initiales Setup des MainPanels
  override protected def setupMainPanel(): Unit = {
    currentPanel = createLevel1Panel()
    updateMainPanel()
  }

  // Listener-Setup für Level 1
  override protected def setupListeners(): Unit = {
    level1Button.addActionListener(_ => {
      // Startet Level 1 und zeigt es im MainFrame an
      GameController.completeLevel()  // Level 1 abgeschlossen
      level2Button.setEnabled(true)
    })

    level2Button.addActionListener(_ => {
      changeToLevel2() // Wechsel zu Level 2
    })

    // Initiale Deaktivierung von Level 2 Button bis Level 1 abgeschlossen ist
    level2Button.setEnabled(false)
  }

  // Methode zum Erstellen des Panels für Level 1
  private def createLevel1Panel(): JPanel = {
    val level1Panel = new JPanel(new BorderLayout())
    level1Panel.setBackground(new Color(0xFFFFFF))
    level1Panel.add(new JLabel("Level 1 wird gespielt..."), BorderLayout.CENTER)
    level1Panel
  }

  // Methode zum Wechseln zu Level 2
  private def changeToLevel2(): Unit = {
    currentPanel = createLevel2Panel()
    updateMainPanel()
  }

  // Methode zum Erstellen des Panels für Level 2
  private def createLevel2Panel(): JPanel = {
    val level2Panel = new JPanel(new BorderLayout())
    level2Panel.setBackground(new Color(0xFFFFFF))
    level2Panel.add(new JLabel("Level 2 wird gespielt..."), BorderLayout.CENTER)
    level2Panel
  }
}

// Singleton-Objekt für die GUI-Instanz
object MainFrameSingleton {

  private var instance: Option[MainFrameTemplate] = None

  // Thread-sichere Methode, um die passende GUI zu setzen
  def getInstance: MainFrameTemplate = synchronized {
    if (instance.isEmpty) {
      val newFrame = new MainFrameLevel1()
      newFrame.initializeFrame() // Template-Methode wird aufgerufen
      instance = Some(newFrame)
    }
    instance.get
  }
}

object GameControllerSingleton {

  def getInstance(): GameController = ??? // Implementiere den GameController
}
