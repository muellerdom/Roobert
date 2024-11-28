package Controller

import Util.{Observable, Observer}
import javax.swing._
import java.awt._
import java.io.File
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import scala.util.{Failure, Success, Try}
import View.GUILevel1

class GalgenmaennchenLevel1 private() extends Observable {

  // Setzt Layout für das Hauptpanel
  GalgenmaennchenLevel1.mainPanel.setLayout(GalgenmaennchenLevel1.cardLayout)

  // Konstruktor: Lädt die Konfiguration aus der JSON-Datei
  loadConfiguration("Config.JSON")

  def getMainPanel: JPanel = GalgenmaennchenLevel1.mainPanel

  // Methode, um Observer hinzuzufügen
  override def addObserver(observer: Observer): Unit = {
    super.addObserver(observer) // Observer über Observable hinzufügen
  }

  // Methode, um alle Observer zu benachrichtigen
  private def notifyObservers(): Unit = {
    super.notifyObservers() // Notify Observer im Observable
  }

  // Lädt die Konfiguration und setzt das Level
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

        // Überprüfe, ob Level1 geladen werden muss
        if (currentLevel == "Level1") {
          val level1Config = config.get("levels").get("Level1")
          val secretWord = level1Config.get("secretWord").asText()
          val maxGuesses = level1Config.get("maxGuesses").asInt()

          // Level1-Panel hinzufügen
          val level1Panel = new GUILevel1(secretWord, maxGuesses)
          GalgenmaennchenLevel1.mainPanel.add(level1Panel.getPanel, "Level1")

          // Benachrichtige alle Observer (z. B. View)
          notifyObservers()
          println("Level1-Panel hinzugefügt")
        }

      case Failure(e) =>
        e.printStackTrace()  // Fehlerbehandlung bei JSON-Leseproblemen
    }
  }
}

object GalgenmaennchenLevel1 {
  // Das Hauptpanel und Layout für die verschiedenen Levels
  val mainPanel: JPanel = new JPanel()
  val cardLayout: CardLayout = new CardLayout()

  // Singleton-Instanz
  private var instance: Option[GalgenmaennchenLevel1] = None

  // Die Methode, um die Instanz zu holen (wird nur eine Instanz zugelassen)
  def getInstance: GalgenmaennchenLevel1 = {
    if (instance.isEmpty) {
      instance = Some(new GalgenmaennchenLevel1())
    }
    instance.get
  }

  // Wechsel zu Level1 im Frame
  def startLevel1(frame: JFrame): Unit = {
    println("Wechsel zu Level 1")
    cardLayout.show(mainPanel, "Level1")
    frame.revalidate()
    frame.repaint()
  }
}
