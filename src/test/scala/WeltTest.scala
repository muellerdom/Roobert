import main.scala.{Spieler, Welt}
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.scalatest._
import java.io.ByteArrayOutputStream


class WeltTest {


  @Test
  def testSetHindernis():Unit =
  {
    var welt = new Welt(5, 5)
    welt.setHindernis(2, 2)


    assertEquals('#', welt.grid(2) (2)) //isch des korrekt
  }
  @Test
  def testSetDiamant(): Unit = {
    var welt = new Welt(5, 5)
    welt.setDiamant(1,1)
    assertEquals('D', welt.grid(1) (1)) //isch des korrekt
  }
  @Test
  def testPrintField(): Unit = {
    var welt = new Welt(5, 5)
    var spieler = new Spieler(2,2)

    welt.setHindernis(1, 1)
    welt.setDiamant(4,4)

    assertEquals('D', welt.grid(1) (1)) //isch des korrekt

    //mer machen n Puffer hi.
    var outputStream = new ByteArrayOutputStream() // des ischt der Zwischenspeicher für den output
    //dat speichert alli geschriebene Daten n de arbeitsspeicher in Form von nem ByteArray
    Console.withOut(outputStream){
      welt.printField(spieler)
    }
    assertEquals('W', outputStream.toString())
  }








}




