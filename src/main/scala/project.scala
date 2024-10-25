import scala.io.StdIn.readLine

//import play.api.libs.json._

def main(args: Array[String]): Unit = { // hier werden argumente mitgegeben

  println("Rooobert, Zeit zu coooden!")
  println(IDEA.instruction())
  //println(IDEA.inputLine())


} // enums für die auswahkl optionen

//klasse wenn es sich im Laufe des Spiels regelmäßig verändert




private object Movement {

  // durchlaufen der MAtrix, User EIngaben direkt übertragen, dss die Figur an diesen Ort übertragen wird

  //daten werden ind er Figur gesepeichtert und final dann auf die Matrix angepasst
  def movehelp(): Unit = {

    val matrix = playground.coordinates


  }


  //Methode zur Bewegung der Figur auf dem Spielfeld
  def moveRight(): Unit = {
    println("Right")
  }

  //Methode zur Bewegung der Figur auf dem Spielfeld
  def moveLeft(): Unit = {
    println("Left ... Left")
  }

  //Methode zur Bewegung der Figur auf dem Spielfeld
  def moveForward(): Unit = {
    println("straight")
  }

  //Methode zur Drehung der Figur auf dem Spielfeld
  def turn(): Unit = {}
}



//Figur-implementierung
object figure {
  val seize = 1.0 // LückenfüllerGröße

  //var coordinates : Array[Array[Int]] = Array.ofDim(0,0)
  var xcoordinate = 0 //xkoordinate des momentanen Aufenthalts der Figur
  var ycoordinate = 0 //ykoordinate des momentanen Aufenthalts der Figur


  printf("x")
}

//Diamanten-implementierung
object Jerm {}


object playground {

  //use this to refer to coordinates on field
  val coordinates: Array[Array[Int]] = Array.ofDim[Int](3, 3)



  def builtPlayground(): Unit = {

    printf("  _ " * 4 + "\n")

    for (counter <- 0 until 2) {
      printf("| _ " * 4)
      printf("|  " + "\n")

    }
  }

 

}



object IDEA {

  def movement(input: List[String]): Unit = { // können es rekursiv machen
    input.foreach {
      case "moveForward()" => Movement.moveForward()
      case "moveRight()" => Movement.moveRight()
      case "moveLeft()" => Movement.moveLeft()
      case _ => printf("Hilf Robert")
      // Anweisungen, wenn keiner der oben genannten Fälle zutrifft
    }
  }

  //w



  def inputLine(): Unit = {

    var input = readLine()
    var inputList: List[String] = List()

    while (input != "start") {
      input = readLine()
      inputList = inputList :+ input
    }

    IDEA.movement(inputList)


    // ein Wert, den du vergleichen möchtest


  }



  def instruction(): Unit = {
    println("Coding - Anleitung")

    println("Robert will den Jerm. Hilf ihm dabei, indem du die Methoden " +
      "moveForward(), moveRight(), moveLeft() und collectJerm() kombinierst.\n" +
      "Wähle das Level mit '1' oder '2' und drücke ENTER")



    inputLine()


    //      printf("  _ " * 10 + "\n")
    //
    //
    //      for (counter <- 0 until 5) {
    //        println("|  " + "   " * 12 + "  |")
    //      }
    //      printf("  _ " * 10 + "\n")
  }
}

object levels {

  def readJSONFile(): Unit = {
    val input_file = "levels.json"
    val json_content = scala.io.Source.fromFile(input_file).mkString
    // val json_data = JSON.parseFull(json_content)
  }


}