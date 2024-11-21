package View

import javax.swing._
import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import Controller.GalgenmaennchenLevel1

class MainFrame {

  // Attribute
  private val frame = new JFrame("Galgenmännchen - Hauptmenü")
  private val sideMenu = new JPanel()
  private val mainPanel = new JPanel(new BorderLayout())
  private val level1Button = new JButton("Level 1")
  private val gameController = new GalgenmaennchenLevel1() // GameController-Referenz

  /**
   * Konstruktor: Initialisiert das Hauptfenster und alle GUI-Komponenten.
   */
  init()

  private def init(): Unit = {
    // Hauptfenster konfigurieren
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE) // Verwende WindowConstants.EXIT_ON_CLOSE
    frame.setSize(600, 400)
    frame.getContentPane.setBackground(new Color(0x6CB2F3))

    // Sidemenü konfigurieren
    sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS))
    sideMenu.setBackground(new Color(108, 178, 243))
    sideMenu.setPreferredSize(new Dimension((frame.getWidth * 0.2).toInt, frame.getHeight))
    sideMenu.setLayout(new GridLayout(0, 1))
    sideMenu.setVisible(false)

    // Menüeinträge hinzufügen
    val level2Button = new JButton("Level 2") // Platzhalter für Level 2
    sideMenu.add(level1Button)
    sideMenu.add(level2Button)

    // Hauptpanel konfigurieren
    mainPanel.setBackground(new Color(0xFFFFFF))
    mainPanel.add(gameController.getMainPanel, BorderLayout.CENTER)

    // Button zum Ein-/Ausblenden des Sidemenüs
    val toggleButton = new JButton("Auf zum nächsten Level!")
    toggleButton.addActionListener(_ => {
      sideMenu.setVisible(!sideMenu.isVisible)
      frame.revalidate()
      frame.repaint()
    })
    mainPanel.add(toggleButton, BorderLayout.SOUTH)

    // ActionListener für den Level 1 Button
    level1Button.addActionListener(_ => {
      GalgenmaennchenLevel1.startLevel1(frame)
    })

    // Panels zum Hauptfenster hinzufügen
    frame.getContentPane.add(sideMenu, BorderLayout.WEST) // Sidemenü links
    frame.getContentPane.add(mainPanel, BorderLayout.CENTER) // Hauptpanel in der Mitte

    // Fenster sichtbar machen
    frame.setVisible(true)
  }
}

object MainFrame {
  /**
   * Main-Methode zum Starten der Anwendung.
   */
  def main(args: Array[String]): Unit = {
    new MainFrame() // Erstelle eine Instanz des Hauptfensters
  }
}
