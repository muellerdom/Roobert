package Controller

import javax.swing._
import java.awt._
import java.io.File
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import scala.util.{Failure, Success, Try}
import View.GUILevel1

class GalgenmaennchenLevel1 {

  GalgenmaennchenLevel1.mainPanel.setLayout(GalgenmaennchenLevel1.cardLayout)

  // Konstruktor: Lädt die Konfiguration aus der JSON-Datei
  loadConfiguration("Config.JSON")

  /**
   * Gibt das Hauptpanel (`mainPanel`) zurück.
   */
  def getMainPanel: JPanel = GalgenmaennchenLevel1.mainPanel

  /**
   * Lädt die Konfigurationsdaten für das Spiel aus einer JSON-Datei.
   * Falls das aktuelle Level "Level1" ist, wird dieses geladen.
   */
  private def loadConfiguration(configFilePath: String): Unit = {
    val mapper = new ObjectMapper()
    val configFile = new File(configFilePath)

    if (!configFile.exists()) {
      println(s"Config-Datei nicht gefunden: ${configFile.getAbsolutePath}")
      return
    }

    Try(mapper.readTree(configFile)) match {
      case Success(config) =>
        val currentLevel = config.get("currentLevel").asText()

        if (currentLevel == "Level1") {
          val level1Config = config.get("levels").get("Level1")
          val secretWord = level1Config.get("secretWord").asText()
          val maxGuesses = level1Config.get("maxGuesses").asInt()

          // Das Panel für Level 1 wird erstellt und dem `mainPanel` hinzugefügt
          val level1Panel = new GUILevel1(secretWord, maxGuesses)
          GalgenmaennchenLevel1.mainPanel.add(level1Panel.getPanel, "Level1")
          println("Level1-Panel hinzugefügt")
        }

      case Failure(e) =>
        e.printStackTrace()
    }
  }
}

object GalgenmaennchenLevel1 {
  val mainPanel: JPanel = new JPanel()
  val cardLayout: CardLayout = new CardLayout()

  /**
   * Wechselt das aktuelle Panel im `CardLayout` zu Level 1 und aktualisiert das Fenster.
   */
  def startLevel1(frame: JFrame): Unit = {
    println("Wechsel zu Level 1")
    cardLayout.show(mainPanel, "Level1")
    frame.revalidate()
    frame.repaint()
  }
}
