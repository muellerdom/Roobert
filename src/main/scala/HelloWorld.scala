import scala.io.StdIn.readLine

object HelloWorld {
  def main(args: Array[String]): Unit = { // hier werden argumente mitgegeben

    println("Rooobert, Zeit zu coooden!")
    println(IDEA.instruction())
    //println(IDEA.inputLine())

    //THIS IS A COMMENT TO CAUSE A MERGE CONFLICT :))) <-- Well, this is the original comment

  }

  def checkInput(): Unit = {
    //    val input = scala.io.StdIn.readLine()
    //    val solution = true
    //    // Durchlaufe jedes Zeichen im Input-String
    //    for (zeichen <- input) {
    //      // Vergleiche jedes Zeichen mit der Lösung
    //      if (zeichen == solution) {
    //        println("true")
    //        return true // Gebe sofort true zurück, wenn die Lösung gefunden wurde
    //      } else {
    //        println("false")
    //      }

  }


  object Movement {

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

    printf("x")

  }

  //Diamanten-implementierung
  object Jerm {}


  object playground {


    def builtPlayground(): Unit = {

      printf("  _ " * 4 + "\n")


      for (counter <- 0 until 2) {
        printf("| _ " * 4)
        printf("|  " + "\n")

      }


    }


  }

  object IDEA {

    def movement (input: List[String]): Unit = { // können es rekursiv machen
      input.foreach {
        case "moveForward()" => Movement.moveForward()
        case "moveRight()" => Movement.moveRight()
        case "moveLeft()" => Movement.moveLeft()
        case _ => printf("Hilf Robert")
        // Anweisungen, wenn keiner der oben genannten Fälle zutrifft
      }
    }

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
}












