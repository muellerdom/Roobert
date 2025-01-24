package Model.SpielfeldComponent

case class Coordinate(x: Int, y: Int) {

  // Prüfen, ob zwei Koordinaten gleich sind
  def isEqualTo(other: Coordinate): Boolean =
    this.x == other.x && this.y == other.y

  // Abstand zwischen zwei Koordinaten berechnen (optional)
  def distanceTo(other: Coordinate): Double =
    Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2))

  // Addition zweier Koordinaten
  def +(other: Coordinate): Coordinate =
    Coordinate(x + other.x, y + other.y)

  // Subtraktion zweier Koordinaten
  def -(other: Coordinate): Coordinate =
    Coordinate(x - other.x, y - other.y)

  // Überprüfung, ob die Koordinate in einer Liste enthalten ist
  def isIn(list: List[Coordinate]): Boolean =
    list.contains(this)
}

