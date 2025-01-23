package UTIL

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Util.Observer

class ObserverSpec extends AnyFlatSpec with Matchers {

  "An Observer" should "be notified" in {
    val observer = new TestObserver
    observer.update()
    observer.notified should be(true)
  }

  class TestObserver extends Observer {
    var notified = false

    override def update(): Unit = {
      notified = true
    }
  }
}