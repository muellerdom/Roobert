import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SpielerTest extends AnyFlatSpec with Matchers {

  "Ein Spieler" should "sich nach oben bewegen, wenn moveForward aufgerufen wird und die Richtung oben ist" in {
    val spieler = new Spieler(0, 1)
    spieler.move("moveForward()")
    spieler.posY should be(0)
    spieler.posX should be(0)
  }

  it should "sich nach rechts bewegen, wenn moveForward aufgerufen wird und die Richtung rechts ist" in {
    val spieler = new Spieler(0, 0)
    spieler.direction = spieler.Rechts
    spieler.move("moveForward()")
    spieler.posX should be(1)
    spieler.posY should be(0)
  }

  it should "sich nach unten bewegen, wenn moveForward aufgerufen wird und die Richtung unten ist" in {
    val spieler = new Spieler(0, 0)
    spieler.direction = spieler.Unten
    spieler.move("moveForward()")
    spieler.posY should be(1)
    spieler.posX should be(0)
  }

  it should "sich nach links bewegen, wenn moveForward aufgerufen wird und die Richtung links ist" in {
    val spieler = new Spieler(1, 0)
    spieler.direction = spieler.Links
    spieler.move("moveForward()")
    spieler.posX should be(0)
    spieler.posY should be(0)
  }

  it should "die Richtung auf rechts ändern, wenn turnRight aufgerufen wird und die Richtung oben ist" in {
    val spieler = new Spieler(0, 0)
    spieler.move("turnRight()")
    spieler.direction should be(spieler.Rechts)
  }

  it should "die Richtung auf links ändern, wenn turnLeft aufgerufen wird und die Richtung oben ist" in {
    val spieler = new Spieler(0, 0)
    spieler.move("turnLeft()")
    spieler.direction should be(spieler.Links)
  }

  it should "die Position nicht verändern, wenn eine unbekannte Bewegung aufgerufen wird" in {
    val spieler = new Spieler(0, 0)
    spieler.move("invalidMove")
    spieler.posX should be(0)
    spieler.posY should be(0)
    spieler.direction should be(spieler.Oben)
  }

  "The turnRight method" should "rotate the player clockwise" in {
    val spieler = new Spieler(0, 0)
    spieler.turnRight()
    spieler.direction should be(spieler.Rechts)
    spieler.turnRight()
    spieler.direction should be(spieler.Unten)
    spieler.turnRight()
    spieler.direction should be(spieler.Links)
    spieler.turnRight()
    spieler.direction should be(spieler.Oben)
  }

  "The turnLeft method" should "rotate the player counter-clockwise" in {
    val spieler = new Spieler(0, 0)
    spieler.turnLeft()
    spieler.direction should be(spieler.Links)
    spieler.turnLeft()
    spieler.direction should be(spieler.Unten)
    spieler.turnLeft()
    spieler.direction should be(spieler.Rechts)
    spieler.turnLeft()
    spieler.direction should be(spieler.Oben)
  }

  "The toString method" should "display the player position correctly" in {
    val spieler = new Spieler(3, 5)
    spieler.toString should be("Spieler(3, 5)")
  }
}
