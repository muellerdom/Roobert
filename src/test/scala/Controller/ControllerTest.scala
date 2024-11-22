package Controller

import Controller.Controller
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ControllerTest extends AnyFlatSpec with Matchers {

  "A Controller" should "load levels correctly" in {
    val controller = new Controller()

    // Angenommen, dass wir ein Level 'test-level' in der Controller-Klasse definieren oder es irgendwo mocken müssen
    val result = controller.startLevel("test-level")

    // Füge eine println-Anweisung hinzu, um den Fehler zu debuggen
    println(s"Result of startLevel: $result")

    result should be ('right) // Erwartet ein erfolgreiches Laden des Levels (Right)
  }

  it should "validate levels correctly" in {
    val controller = new Controller()

    // Definiere ein fehlerhaftes und ein gültiges Level
    val faultyLevel = LevelConfig("faulty", "", "", 3, 3, Coordinate(0, 0), Goal(4, 4), Objects(List(), List()))
    val validLevel = LevelConfig("valid", "", "", 3, 3, Coordinate(0, 0), Goal(2, 2), Objects(List(), List()))

    // Teste die Validierung der Levels
    val faultyValidation = controller.validateLevels(List(faultyLevel))
    val validValidation = controller.validateLevels(List(validLevel))

    // Füge println-Anweisungen hinzu, um den Status der Validierungen zu überprüfen
    println(s"Faulty level validation result: $faultyValidation")
    println(s"Valid level validation result: $validValidation")

    // Überprüfe die erwarteten Ergebnisse
    faultyValidation should be ('left) // Erwartet einen Fehler bei der Validierung des fehlerhaften Levels (Left)
    validValidation should be ('right)  // Erwartet eine erfolgreiche Validierung des gültigen Levels (Right)
  }
}
