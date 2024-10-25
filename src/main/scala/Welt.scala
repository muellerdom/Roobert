/*
Verwaltet alle Level des Spiels.
levels ist eine Liste aller Level.
currentLevel zeigt an, in welchem Level sich der Spieler gerade befindet.
 */

class Welt(var width: Int, var height: Int) {
  // Initialisiere das Spielfeld mit leeren Feldern



    var grid: Array[Array[Char]] = Array.ofDim(width, height)
    
    
  
    // Setzt ein Hindernis auf die angegebene Position
    def setHindernis(x: Int, y: Int): Unit = {
      grid(y)(x) = '#'
    }

    // Setzt den Diamanten auf die angegebene Position
    def setDiamant(x: Int, y: Int): Unit = {
      grid(y)(x) = 'D'
    }

    // Gibt das Spielfeld aus, inklusive der Position des Spielers
    def printField(spieler: Spieler): Unit = {
      for (y <- 0 until height) {
        for (x <- 0 until width) {
          if (spieler.posX == x && spieler.posY == y) {
            print('S')
          } else {
            print(grid(y)(x))
          }
        }
        println()
      }
    }
  }





object playground {


  def builtPlayground(): Unit = {

    printf("  _ " * 4 + "\n")


    for (counter <- 0 until 2) {
      printf("| _ " * 4)
      printf("|  " + "\n")

    }


  } //boah junge


}