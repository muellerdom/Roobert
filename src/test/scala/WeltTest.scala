
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

import java.io.ByteArrayOutputStream


class WeltTest extends AnyWordSpec {

  "grid" should {
    "create a Welt" in {
      val welt = new Welt(5, 5)
      welt.width should be(5)
      welt.height should be(5)

    }
  }

  "welt.setHindernes" should {

    val welt = new Welt(5, 5)

    "set an Obstacle" in {
      welt.setHindernis(2, 2)
      welt.grid(2)(2) should be('#')
    }

    "not set a diamant on same pos. as obstacle" in {
      val exception = intercept[IllegalArgumentException] {
        welt.setDiamant(2, 2)
      }

      exception.getMessage should be("Fehler: Das Element an Position (2, 2) ist 'D'.")

      welt.grid(2)(2) should be('#')
    }
  }

  "welt.setDiamant" should {
    val welt = new Welt(5, 5)
    "set a Diamant" in {
      welt.setDiamant(2, 2)
      welt.grid(2)(2) should be('D')
    }

    "not set an obstacle on same pos. as diamant" in {
      val exception = intercept[IllegalArgumentException] {
        welt.setHindernis(2, 2)
      }

      exception.getMessage should be("Fehler: Das Element an Position (2, 2) ist 'D'.")

      welt.grid(2)(2) should be('D')
    }
  }
}




