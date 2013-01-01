package game

import scala.math._

case class Line(p1: Vector2d, p2: Vector2d, intensity: Double) {
  def this(p1: Vector2d, p2: Vector2d) = this(p1, p2, 1)

  def isCoincident(p: Vector2d): Boolean =
    p1.isCoincident(p) || p2.isCoincident(p)

  def isCoincident(otherLine: Line): Boolean = {
    p1.isCoincident(otherLine.p1) && p2.isCoincident(otherLine.p2) ||
      p1.isCoincident(otherLine.p2) && p2.isCoincident(otherLine.p1)
  }

  def center = 0.5 * (p1 + p2)

  override def equals(other: Any) = other match {
    case (line: Line) => isCoincident(line)
    case _ => false
  }

  // TODO: better hashcode, but needs to be the same for coincident lines
  override def hashCode(): Int = 0
}
