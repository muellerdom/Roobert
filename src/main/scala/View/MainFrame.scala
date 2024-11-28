package View

import javax.swing._
import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import Controller.GalgenmaennchenLevel1
import Util.Observer

class MainFrame extends Observer {

  private val frame = new JFrame("Galgenmännchen - Hauptmenü")
  private val sideMenu = new JPanel()
  private val mainPanel = new JPanel(new BorderLayout())
  private val level1Button = new JButton("Level 1")
  private val level2Button = new JButton("Level 2") // Hier könnte man für weitere Level Buttons hinzufügen
  private val gameController = new GalgenmaennchenLevel1()

  init()

  // Diese Methode wird aufgerufen, wenn der Controller oder das Modell eine Änderung vornimmt
  override def update(): Unit = {
    // Hier aktualisieren wir die GUI-Komponenten, wenn der Controller eine Änderung vornimmt
    println("Das Spiel hat sich geändert. Das Hauptfenster wird aktualisiert.")

    // Beispiel für das Aktivieren/Deaktivieren von Buttons basierend auf Spielstatus
    if (gameController.isLevelCompleted) {
      level1Button.setEnabled(true)
      level2Button.setEnabled(true)
      println("Level abgeschlossen! Weiter zu Level 2.")
    } else {
      level1Button.setEnabled(false)
      println("Level 1 läuft noch.")
    }

    // Eventuelle Updates für andere GUI-Elemente
    // Wenn das Modell eine neue Nachricht schickt, könnte hier auch ein Label oder Textfeld aktualisiert werden.
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
    mainPanel.add(gameController.getMainPanel, BorderLayout.CENTER)

    val toggleButton = new JButton("Auf zum nächsten Level!")
    toggleButton.addActionListener(_ => {
      sideMenu.setVisible(!sideMenu.isVisible)
      frame.revalidate()
      frame.repaint()
    })
    mainPanel.add(toggleButton, BorderLayout.SOUTH)

    level1Button.addActionListener(_ => {
      GalgenmaennchenLevel1.startLevel1(frame)
      // Nach Level 1 könnte der Button für Level 2 aktiviert werden
      level2Button.setEnabled(true)
    })

    // Initiale Deaktivierung von Level 2 Button bis Level 1 abgeschlossen ist
    level2Button.setEnabled(false)

    frame.getContentPane.add(sideMenu, BorderLayout.WEST)
    frame.getContentPane.add(mainPanel, BorderLayout.CENTER)
    frame.setVisible(true)
  }
}

object MainFrame {
  def main(args: Array[String]): Unit = {
    new MainFrame()
  }
}
