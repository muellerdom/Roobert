import Util.{Observable, Observer}
import javax.swing._
import java.awt._
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import scala.util.{Failure, Success, Try}
import java.io.File

// Abstrakte Klasse GalgenmaennchenLevel, die das Template Method Pattern umsetzt
abstract class GalgenmaennchenLevel(private val configFilePath: String) extends Observable {

  // Setzt Layout für das Hauptpanel
  GalgenmaennchenLevel.mainPanel.setLayout(GalgenmaennchenLevel.cardLayout)

  // Konstruktor: Lädt die Konfiguration aus der JSON-Datei
  loadConfiguration(configFilePath)

  def getMainPanel: JPanel = GalgenmaennchenLevel.mainPanel

  // Methode, um Observer hinzuzufügen
  override def addObserver(observer: Observer): Unit = {
    super.addObserver(observer) // Observer über Observable hinzufügen
  }

  // Methode, um alle Observer zu benachrichtigen
  private def notifyObservers(): Unit = {
    super.notifyObservers() // Notify Observer im Observable
  }

  // Template-Methode: definiert den allgemeinen Ablauf zum Laden des Levels
  private def loadConfiguration(configFilePath: String): Unit = {
    val config = readConfigFile(configFilePath)

    if (config != null) {
      val currentLevel = getCurrentLevel(config)

      // Überprüfe, ob das aktuelle Level das passende ist
      if (currentLevel == "Level1") {
        val levelConfig = getLevelConfig(config, "Level1")
        setupLevelPanel(levelConfig)
        notifyObservers()
      }
    }
  }

  // Schritt 1: Lese die Konfigurationsdatei (kann von Unterklassen spezifiziert werden)
  protected def readConfigFile(configFilePath: String): JsonNode = {
    val mapper = new ObjectMapper()
    val configFile = new File(configFilePath)
    if (!configFile.exists()) {
      println(s"Config-Datei nicht gefunden: ${configFile.getAbsolutePath}")
      return null
    }

    Try(mapper.readTree(configFile)) match {
      case Success(config) => config
      case Failure(e) =>
        e.printStackTrace()
        null
    }
  }

  // Schritt 2: Bestimme das aktuelle Level (kann von Unterklassen spezifiziert werden)
  protected def getCurrentLevel(config: JsonNode): String = {
    config.get("currentLevel").asText()
  }

  // Schritt 3: Lade die Levelkonfiguration für ein bestimmtes Level
  protected def getLevelConfig(config: JsonNode, level: String): JsonNode = {
    config.get("levels").get(level)
  }

  // Schritt 4: Erstelle das Panel und füge es hinzu (wird in Unterklassen spezifiziert)
  protected def setupLevelPanel(levelConfig: JsonNode): Unit
}

/**
 * GalgenmaennchenLevel1 spezifische Implementierung
 */
class GalgenmaennchenLevel1(configFilePath: String) extends GalgenmaennchenLevel(configFilePath) {

  // Schritt 4: Setup für Level 1, wie es im spezifischen Fall benötigt wird
  override protected def setupLevelPanel(levelConfig: JsonNode): Unit = {
    val secretWord = levelConfig.get("secretWord").asText()
    val maxGuesses = levelConfig.get("maxGuesses").asInt()

    // Level1-Panel erstellen und hinzufügen
    val level1Panel = new GUILevel1(secretWord, maxGuesses)
    GalgenmaennchenLevel.mainPanel.add(level1Panel.getPanel, "Level1")
    println("Level1-Panel hinzugefügt")
  }
}

// Das GUILevel1-Panel zur Darstellung der Level1-spezifischen Komponenten
class GUILevel1(secretWord: String, maxGuesses: Int) {
  // Erstelle das Panel für Level1
  private val panel: JPanel = new JPanel()
  private val label: JLabel = new JLabel(s"Secret word: $secretWord, Max guesses: $maxGuesses")

  panel.add(label)

  def getPanel: JPanel = panel
}

object GalgenmaennchenLevel {
  // Das Hauptpanel und Layout für die verschiedenen Levels
  val mainPanel: JPanel = new JPanel()
  val cardLayout: CardLayout = new CardLayout()

  // Singleton-Instanz
  private var instance: Option[GalgenmaennchenLevel] = None

  /**
   * Liefert die Singleton-Instanz von GalgenmaennchenLevel1.
   * Wenn sie noch nicht existiert, wird sie mit einer Konfigurationsdatei erzeugt.
   * @param configFilePath Der Pfad zur Konfigurationsdatei.
   * @return Die Singleton-Instanz.
   */
  def getInstance(configFilePath: String): GalgenmaennchenLevel = {
    if (instance.isEmpty) {
      instance = Some(new GalgenmaennchenLevel1(configFilePath))
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
