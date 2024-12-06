import Util.{Observable, Observer}
import javax.swing._
import java.awt._
import scala.util.parsing.json.JSON
import scala.io.Source
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

  // Enumeration der verschiedenen Spielphasen
  sealed trait GameStage
  case object InitializationStage extends GameStage
  case object UserInputStage extends GameStage
  case object ProcessMoveStage extends GameStage
  case object GameEndStage extends GameStage

  // Aktuelle Spielphase
  private var currentStage: GameStage = InitializationStage

  // Template-Methode: definiert den allgemeinen Ablauf zum Laden des Levels
  private def loadConfiguration(configFilePath: String): Unit = {
    val config = readConfigFile(configFilePath)

    if (config.nonEmpty) {
      val currentLevel = getCurrentLevel(config)

      // Überprüfe, ob das aktuelle Level das passende ist
      if (currentLevel == "Level1") {
        val levelConfig = getLevelConfig(config, "Level1")
        setupLevelPanel(levelConfig)
        notifyObservers()
        currentStage = UserInputStage  // Setzt die Phase auf "Benutzereingabe"
      }
    }
  }

  // Schritt 1: Lese die Konfigurationsdatei
  protected def readConfigFile(configFilePath: String): Map[String, Any] = {
    val file = new File(configFilePath)
    if (!file.exists()) {
      println(s"Config-Datei nicht gefunden: ${file.getAbsolutePath}")
      return Map.empty
    }

    Try {
      val source = Source.fromFile(file)
      val jsonString = try source.mkString finally source.close()
      JSON.parseFull(jsonString) match {
        case Some(data: Map[String, Any]) => data
        case _ =>
          println("Ungültiges JSON-Format.")
          Map.empty
      }
    } match {
      case Success(config) => config
      case Failure(e) =>
        e.printStackTrace()
        Map.empty
    }
  }

  // Schritt 2: Bestimme das aktuelle Level
  protected def getCurrentLevel(config: Map[String, Any]): String = {
    config.get("currentLevel").map(_.toString).getOrElse("")
  }

  // Schritt 3: Lade die Levelkonfiguration für ein bestimmtes Level
  protected def getLevelConfig(config: Map[String, Any], level: String): Map[String, Any] = {
    config.get("levels") match {
      case Some(levels: Map[String, Any]) =>
        levels.get(level) match {
          case Some(levelConfig: Map[String, Any]) => levelConfig
          case _ => Map.empty
        }
      case _ => Map.empty
    }
  }

  // Schritt 4: Erstelle das Panel und füge es hinzu (wird in Unterklassen spezifiziert)
  protected def setupLevelPanel(levelConfig: Map[String, Any]): Unit

  // Spielsteuerung je nach Phase
  def runGameCycle(): Unit = {
    currentStage match {
      case InitializationStage => initializeGame()
      case UserInputStage => promptForMove()
      case ProcessMoveStage => processMove(getUserInput())
      case GameEndStage => endGame()
    }
  }

  // Startet das Spiel
  protected def initializeGame(): Unit = {
    // Initialisiere Level, lade Konfiguration etc.
    println("Spiel wird initialisiert...")
    currentStage = UserInputStage  // Setzt die Phase auf "Benutzereingabe"
  }

  // Benutzereingabe anfordern
  protected def promptForMove(): Unit = {
    println("Bitte gib einen Buchstaben zum Raten ein...")
    currentStage = ProcessMoveStage  // Setzt die Phase auf "Bewege verarbeiten"
  }

  // Verarbeitet den Benutzerzug
  protected def processMove(input: String): Unit = {
    println(s"Verarbeite Eingabe: $input")
    // Verarbeite das Ratewort etc.
    currentStage = if (isLevelCompleted) GameEndStage else UserInputStage
  }

  // Überprüft, ob das Level abgeschlossen ist
  protected def isLevelCompleted: Boolean = {
    // Überprüfe, ob der Benutzer das richtige Wort erraten hat oder die maximale Anzahl an Versuchen erreicht ist
    false
  }

  // Beendet das Spiel
  protected def endGame(): Unit = {
    println("Das Spiel ist zu Ende!")
  }
}

/**
 * GalgenmaennchenLevel1 spezifische Implementierung
 */
class GalgenmaennchenLevel1(configFilePath: String) extends GalgenmaennchenLevel(configFilePath) {

  // Schritt 4: Setup für Level 1, wie es im spezifischen Fall benötigt wird
  override protected def setupLevelPanel(levelConfig: Map[String, Any]): Unit = {
    val secretWord = levelConfig.getOrElse("secretWord", "unknown").toString
    val maxGuesses = levelConfig.getOrElse("maxGuesses", 0).toString.toInt

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
