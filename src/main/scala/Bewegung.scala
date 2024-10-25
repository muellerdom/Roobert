import main.scala.HelloWorld.{IDEA, Movement}

import scala.io.StdIn.readLine

class Bewegung {

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
  }
}