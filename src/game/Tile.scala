package game

class Tile extends Polygon {

}

object NullTile extends Tile {
  override def intersects(p: Polygon) = false
}

object ThinRhombTile extends Tile {
  init(1, List(36, 144, 36))
}

object ThickRhombTile extends Tile {
  init(1, List(72, 108, 72))
}
