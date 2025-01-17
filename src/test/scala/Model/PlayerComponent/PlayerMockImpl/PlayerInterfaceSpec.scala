package Model.PlayerComponent

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PlayerInterfaceSpec extends AnyFlatSpec with Matchers {

  "PlayerInterface" should "be implemented by Player" in {
    val player: PlayerInterface = Model.PlayerComponent.PlayerBaseImpl.Player
    player shouldBe a[PlayerInterface]
  }
}