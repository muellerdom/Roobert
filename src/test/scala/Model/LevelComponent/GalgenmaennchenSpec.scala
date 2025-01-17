package Model.LevelComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GalgenmaennchenSpec extends AnyFlatSpec with Matchers {

  "Galgenmaennchen" should "correctly guess a letter" in {
    val game = new Galgenmaennchen("testword")
    game.pruefeBuchstabeOeffentlich('t') shouldBe true
    game.displayGuessedWord shouldBe "t _ _ t _ _ _ _"
  }

  it should "incorrectly guess a letter" in {
    val game = new Galgenmaennchen("testword")
    game.pruefeBuchstabeOeffentlich('x') shouldBe false
    game.guessesLeft shouldBe 5
  }

  it should "win the game" in {
    val game = new Galgenmaennchen("test")
    "test".foreach(game.pruefeBuchstabeOeffentlich)
    game.isGameWon shouldBe true
  }

  it should "lose the game" in {
    val game = new Galgenmaennchen("test", 1)
    game.pruefeBuchstabeOeffentlich('x')
    game.isGameLost shouldBe true
  }
}