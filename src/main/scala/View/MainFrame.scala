package View

import javax.swing._
import java.awt._
import Controller.GalgenmaennchenLevel1
import Util.Observer

// Abstrakte Factory zur Erstellung der Haupt-GUI für Level
trait GUIFactory {
  def createMainPanel(frame: JFrame): JPanel
}

// Konkrete Factory für Level 1
class Level1GUIFactory extends GUIFactory {
  override def createMainPanel(frame: JFrame): JPanel = {
    val gameController = new GalgenmaennchenLevel1()
    val mainPanel = new JPanel(new BorderLayout())
    mainPanel.setBackground(new Color(0xFFFFFF))
    mainPanel.add(gameController.getMainPanel, BorderLayout.CENTER)
    mainPanel
  }
}

// Konkrete Factory für Level 2 (Wenn gewünscht, für Level 2 spezifische GUI hinzufügen)
class Level2GUIFactory extends GUIFactory {
  override def createMainPanel(frame: JFrame): JPanel = {
    // Dies ist nur ein Beispiel, wie du Level 2 weiter implementieren könntest.
    val mainPanel = new JPanel(new BorderLayout())
    mainPanel.setBackground(new Color(0xFFFFFF))
    // Füge hier das Level 2 spezifische Panel hinzu.
    mainPanel
  }
}

class MainFrame extends Observer {

  private val frame = new JFrame("Galgenmännchen - Hauptmenü")
  private val sideMenu = new JPanel()
  private val mainPanel = new JPanel(new BorderLayout())
  private val level1Button = new JButton("Level 1")
  private val level2Button = new JButton("Level 2")
  private val gameController = new GalgenmaennchenLevel1()

  private var currentFactory: GUIFactory = new Level1GUIFactory()

  init()

  // Diese Methode wird aufgerufen, wenn der Controller oder das Modell eine Änderung vornimmt
  override def update(): Unit = {
    println("Das Spiel hat sich geändert. Das Hauptfenster wird aktualisiert.")
    if (gameController.isLevelCompleted) {
      level1Button.setEnabled(true)
      level2Button.setEnabled(true)
      println("Level abgeschlossen! Weiter zu Level 2.")
    } else {
      level1Button.setEnabled(false)
      println("Level 1 läuft noch.")
    }
  }

  private def init(): Unit = {
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.setSize(600, 400)
    frame.getContentPane.setBackground(new Color(0x6CB2F3))

    sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS))
    sideMenu.setBackground(new Color(108, 178, 243))
    sideMenu.setPreferredSize(new Dimension((frame.getWidth * 0.2).toInt, frame.getHeight))

    sideMenu.add(level1Button)
    sideMenu.add(level2Button)

    mainPanel.setBackground(new Color(0xFFFFFF))
    updateMainPanel()

    val toggleButton = new JButton("Auf zum nächsten Level!")
    toggleButton.addActionListener(_ => {
      sideMenu.setVisible(!sideMenu.isVisible)
      frame.revalidate()
      frame.repaint()
    })
    mainPanel.add(toggleButton, BorderLayout.SOUTH)

    level1Button.addActionListener(_ => {
      GalgenmaennchenLevel1.startLevel1(frame)
      level2Button.setEnabled(true)
    })

    // Initiale Deaktivierung von Level 2 Button bis Level 1 abgeschlossen ist
    level2Button.setEnabled(false)

    frame.getContentPane.add(sideMenu, BorderLayout.WEST)
    frame.getContentPane.add(mainPanel, BorderLayout.CENTER)
    frame.setVisible(true)
  }

  // Methode zum Aktualisieren des MainPanels mit der richtigen Factory
  private def updateMainPanel(): Unit = {
    val newMainPanel = currentFactory.createMainPanel(frame)
    mainPanel.removeAll()
    mainPanel.add(newMainPanel, BorderLayout.CENTER)
    frame.revalidate()
    frame.repaint()
  }

  // Eventuell möchtest du später mit einem Level-Wechsel auch die Factory wechseln
  private def changeToLevel2(): Unit = {
    currentFactory = new Level2GUIFactory()
    updateMainPanel()
  }
}

object MainFrame {
  def main(args: Array[String]): Unit = {
    new MainFrame()
  }
}
