package game

import scala.math._

// simple vector class
case class Vector2d(x: Double, y: Double) {
  def +(other: Vector2d) = Vector2d(this.x + other.x, this.y + other.y)

  def -(other: Vector2d) = Vector2d(this.x - other.x, this.y - other.y)

  def *(scalar: Double) = Vector2d(this.x * scalar, this.y * scalar)

  def rotate(angle: Double) = {
    val rad = toRadians(angle)
    val sa = sin(rad)
    val ca = cos(rad)
    Vector2d(x * ca - y * sa, y * ca + x * sa)
  }
  def calculateRotation() = {
    toDegrees(atan2(x, y))
  }

  def distance(other: Vector2d) = {
    val dx = x - other.x
    val dy = y - other.y
    sqrt(dx * dx + dy * dy)
  }

  def isCoincident(other: Vector2d): Boolean = {
    distance(other) < 0.0001
  }

  def abs = sqrt(x * x + y * y)

  def dot(v: Vector2d): Double = v.x * x + v.y * y

  override def equals(other: Any) = other match {
    case (v: Vector2d) => isCoincident(v)
    case _ => false
  }

  // TODO: better hashcode, but needs to be the same for coincident vectors
  override def hashCode(): Int = 0
}

// implicit conversion of a double with a wrapper class to allow scalar*vector
class DoubleWrapper(d: Double) {
  def *(v: Vector2d) = v * d
}
object Vector2d {
  implicit final def wrapDouble(d: Double) = new DoubleWrapper(d)
}
