import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import scala.io.StdIn

object SimpleREPL {
  def main(args: Array[String]): Unit = {
    val settings = new Settings()
    settings.usejavacp.value = true  // Benutzt den Java-Classpath

    val interpreter = new IMain(settings)  // Initialisiert den REPL

    println("Scala REPL gestartet. Gib Scala-Code ein (oder 'exit' zum Beenden):")

    var continue = true
    while (continue) {
      print("scala> ")
      val code = StdIn.readLine()  // Benutzereingabe einlesen

      code match {
        case "exit" =>
          println("REPL beendet.")
          continue = false

        case nonEmptyCode if nonEmptyCode.trim.nonEmpty =>
          // Code ausführen
          val result = interpreter.interpret(nonEmptyCode)
          if (result == scala.tools.nsc.interpreter.IMain..Result.Success) {
            println("Code erfolgreich ausgeführt.")
          } else {
            println("Fehler beim Ausführen des Codes.")
          }

        case _ =>
          println("Bitte gib gültigen Scala-Code ein oder 'exit', um zu beenden.")
      }
    }
  }
}
