package Model.PlayerComponent.PlayerBaseImpl

import Model.SpielfeldComponent.KomponentenInterface
import org.scalamock.clazz.MockImpl.mock
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InventorySpec extends AnyFlatSpec with Matchers with MockitoSugar {

  "Inventory" should "add items correctly" in {
    val inventory = new Inventory()
    val item = mock[KomponentenInterface]
    inventory.addItem(item)
    inventory.containsItem(item) shouldBe true
  }

  it should "return the correct size" in {
    val inventory = new Inventory()
    val item1 = mock[KomponentenInterface]
    val item2 = mock[KomponentenInterface]
    inventory.addItem(item1)
    inventory.addItem(item2)
    inventory.size shouldBe 2
  }

  it should "return the correct items" in {
    val inventory = new Inventory()
    val item1 = mock[KomponentenInterface]
    val item2 = mock[KomponentenInterface]
    inventory.addItem(item1)
    inventory.addItem(item2)
    inventory.getItems should contain allOf (item1, item2)
  }
}