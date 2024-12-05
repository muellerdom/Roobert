import Model.Bruecke.notifyObservers
import Util.Observer

// Abstrakte Klasse für den Ablauf des Zustandswechsels
abstract class StateChanger(controller: Controller) {
  // Die Template-Methode, die den Ablauf definiert
  def changeState(input: String): Unit = {
    preChangeState(input) // Vor der Zustandsänderung
    controller.changeState(input) // Zustandsänderung im Controller
    postChangeState() // Nach der Zustandsänderung
  }

  // Diese Methoden können in den abgeleiteten Klassen überschrieben werden
  def preChangeState(input: String): Unit = {
    // Allgemeine Vorbereitungen (kann in der Unterklasse überschrieben werden)
    println(s"Vor der Zustandsänderung: $input")
  }

  def postChangeState(): Unit = {
    // Allgemeine Nachbereitungen (kann in der Unterklasse überschrieben werden)
    println("Nach der Zustandsänderung.")
  }
}

// Die konkrete TUI-Klasse, die den Ablauf anpasst
class TUI(controller: Controller) extends StateChanger(controller) with Observer {
  controller.addObserver(this) // TUI als Beobachter hinzufügen

  def start(): Unit = {
    println("Textbasierte Benutzeroberfläche gestartet!")
  }

  def processInputLine(input: String): Unit = {
    changeState(input) // Nutze die Template-Methode zum Zustandswechsel
    println(s"Neuer Zustand: ${controller.getState}")
  }

  // update-Methode für Observer-Pattern
  override def update(): Unit = {
    println(s"TUI Benachrichtigung: Der Zustand im Controller hat sich geändert!")
  }

  // Überschreibe die Vor- und Nachbereitung, falls nötig
  override def preChangeState(input: String): Unit = {
    println(s"TUI Bereite die Eingabe vor: $input")
  }

  override def postChangeState(): Unit = {
    println("TUI Nach der Zustandsänderung.")
  }
}

// Singleton Pattern für Controller bleibt unverändert
class Controller private() extends Observable {
  private var someState: String = "Initial State"

  def changeState(newState: String): Unit = {
    someState = newState
    notifyObservers() // Benachrichtige die Beobachter
  }

  def getState: String = someState
}

object Controller {
  private val instance: Controller = new Controller()

  def getInstance: Controller = instance
}

// Main-Objekt bleibt unverändert
object Main {
  val controller = Controller.getInstance  // Holen Sie sich die Singleton-Instanz des Controllers
  val tui = new TUI(controller)

  // Simuliere die Benachrichtigung zu Beginn (optional)
  controller.notifyObservers()

  def main(args: Array[String]): Unit = {
    tui.start()  // Starte das TUI

    if (args.nonEmpty) {
      tui.processInputLine(args(0))  // Falls Eingabewerte übergeben werden, verarbeite sie
    } else {
      var input: String = ""
      do {
        input = scala.io.StdIn.readLine()
        tui.processInputLine(input)  // Verarbeite die Eingabe
      } while (input != "q")  // Bei "q" beenden
    }
  }
}
