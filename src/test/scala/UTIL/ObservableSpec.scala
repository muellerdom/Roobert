package UTIL

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import Util.{Observable, Observer}

class ObservableSpec extends AnyFlatSpec with Matchers {

  "An Observable" should "add and notify observers" in {
    val observable = new TestObservable
    val observer = new TestObserver

    observable.addObserver(observer)
    observable.notifyObservers()

    observer.notified should be(true)
  }

  it should "remove observers" in {
    val observable = new TestObservable
    val observer = new TestObserver

    observable.addObserver(observer)
    observable.remove(observer)
    observable.notifyObservers()

    observer.notified should be(false)
  }

  class TestObservable extends Observable

  class TestObserver extends Observer {
    var notified = false

    override def update(): Unit = {
      notified = true
    }
  }
}