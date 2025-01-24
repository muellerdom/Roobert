package Model.FileIOComponent

import Model.GridComponent.GridInterface

trait FileIOInterface {

  def load(): Unit

  def save(grid: GridInterface): Unit

}
