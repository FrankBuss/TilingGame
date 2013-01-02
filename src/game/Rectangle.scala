package game

import scala.math._

class Rectangle(p1: Vector2d, p2: Vector2d) {
  var left = min(p1.x, p2.x)
  var top = min(p1.y, p2.y)
  var bottom = max(p1.y, p2.y)
  var right = max(p1.x, p2.x)

  def intersects(r2: Rectangle) =
    !(r2.left > right ||
      r2.right < left ||
      r2.top > bottom ||
      r2.bottom < top)

  def render() = {
    List(new Line(Vector2d(left, top), Vector2d(right, top)),
      new Line(Vector2d(right, top), Vector2d(right, bottom)),
      new Line(Vector2d(right, bottom), Vector2d(left, bottom)),
      new Line(Vector2d(left, bottom), Vector2d(left, top)))
  }

}
