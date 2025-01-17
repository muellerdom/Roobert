package Model.REPLComponent

import Controller.Component.ControllerBaseImpl.Controller
import Util.Observable

trait REPLInterface extends Observable {
  def replBind(controller: Controller): Unit
  def Interpret(code: String): Unit
}
