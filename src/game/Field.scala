package game

import scala.math._

class Field {
  val thinRhomb = new Polygon(1, List(36, 144, 36))
  val thickRhomb = new Polygon(1, List(72, 108, 72))
  var tiles = List(thinRhomb)
  var currentTile = thinRhomb

  var currentTileSide = 0

  var outlineLines = List[Line]()
  var tilesLines = List[Line]()
  var outlineLineIndex = 0

  var lastTime: Long = 0
  var accumulatedDelta: Long = 0
  var intersetcsCache = false

  def newGame = {
    val r1 = thickRhomb
    val r2 = thickRhomb.align(r1.render()(1), 0)
    val r3 = thickRhomb.align(r2.render()(3), 0)
    val r4 = thickRhomb.align(r3.render()(3), 0)
    tiles = List(r1,r2,r3, r4)
    currentTile = thickRhomb
    currentTileSide = 1
    outlineLines = List[Line]()
    tilesLines = List[Line]()
    outlineLineIndex = 7
    updateLines
    onLeft
  }

  newGame

  private def updateLines = {
    // collect all lines from all tiles
    val lines = tiles.flatMap(tile => tile.render)

    // partition into overlapping and non-overlapping
    val partition = lines.groupBy(line => lines.count(line2 => line2.isCoincident(line)) > 1)

    // update lines
    if (partition.contains(true)) {
      tilesLines = partition(true).distinct
    }
    if (partition.contains(false)) {
      outlineLines = partition(false).distinct
    }

    // sort outline lines: start of a line should be the same as end of another line, if possible
    // there are gaps if there are holes in the outline polygon
    var sorted = List[Line]()
    while (sorted.length < outlineLines.length) {
      // add initial line, if empty
      if (sorted.isEmpty)
        sorted = List(outlineLines.head)

      // search next line
      val test = sorted.head
      def searchNextLine: Line = {
        // first try to find a connected line
        outlineLines.foreach(line => {
          if (!sorted.contains(line)) {
            if (test.p2.isCoincident(line.p1))
              return line
          }
        })

        // not found: return any line which is not already added
        outlineLines.foreach(line => {
          if (!sorted.contains(line)) {
            return line
          }
        })

        // should not reach this point
        Console.println("line sorting error")
        outlineLines.head
      }
      sorted ::= searchNextLine
    }
    outlineLines = sorted.reverse
  }

  private def addCurrentTile = {
    tiles ::= currentTile
    updateLines
  }

  def intersects = tiles.exists(tile => currentTile.intersects(tile))

  def addTile(newTile: Polygon): Unit = {
    // new tile only allowed, if not intersecting
    if (intersects)
      return

    // add last current tile
    addCurrentTile

    // get center of last tile
    val lastTileCenter = currentTile.center

    // calculate center for outline lines
    var outlineCenter = Vector2d(0, 0)
    outlineLines.foreach(line => { outlineCenter += line.center })
    if (!outlineLines.isEmpty) {
      outlineCenter = (1 / outlineLines.length) * outlineCenter
    }

    // try to place new tile, so that the distance sum to center of outline and center of previous tile is minimal
    // and that it doesn't intersect
    var minDistance = Double.MaxValue
    var i = 0
    outlineLineIndex = 0
    currentTile = newTile
    outlineLines.foreach(line => {
      currentTile = currentTile.align(line, currentTileSide)
      val currentTileCenter = currentTile.center
      val distance = (currentTileCenter - outlineCenter).abs + (currentTileCenter - lastTileCenter).abs
      if (distance < minDistance && !intersects) {
        minDistance = distance
        outlineLineIndex = i
      }
      i += 1
    })

    // update alignment
    updateCurrentTile

    // update lines
    updateLines
  }

  def addThinRhomb = {
    addTile(thinRhomb)
  }

  def addThickRhomb = {
    addTile(thickRhomb)
  }

  def getLines: List[Line] = {
    // timer
    val t = System.currentTimeMillis()
    var delta = t - lastTime
    if (delta > 100) {
      delta = 100
    }
    if (delta < 0) {
      delta = 0
    }
    lastTime = t
    accumulatedDelta += delta
    if (accumulatedDelta > 1000) accumulatedDelta -= 1000

    var list = List[Line]()

    // draw blinking next tile
    val blinkColor = abs(accumulatedDelta.toDouble - 500) / 500
    val lines = currentTile.render(blinkColor)
    list ++= lines
    if (intersetcsCache) {
      list ::= Line(lines(0).p1, lines(2).p1, blinkColor)
      list ::= Line(lines(1).p1, lines(3).p1, blinkColor)
    }

    // draw tiles
    list ++= tilesLines.map(line => Line(line.p1, line.p2, 0.4))

    // draw outline
    list ++= outlineLines.map(line => Line(line.p1, line.p2, 1.0))

    list
  }

  def updateCurrentTile = {
    currentTile = currentTile.align(outlineLines(outlineLineIndex), currentTileSide)
    intersetcsCache = intersects
  }

  def onUp = {
    currentTileSide += 1
    if (currentTileSide >= currentTile.points.length)
      currentTileSide = 0
    updateCurrentTile
  }

  def onDown = {
    currentTileSide -= 1
    if (currentTileSide < 0)
      currentTileSide = currentTile.points.length - 1
    updateCurrentTile
  }

  def onLeft = {
    outlineLineIndex += 1
    if (outlineLineIndex >= outlineLines.length)
      outlineLineIndex = 0
    updateCurrentTile
  }

  def onRight = {
    outlineLineIndex -= 1
    if (outlineLineIndex < 0)
      outlineLineIndex = outlineLines.length - 1
    updateCurrentTile
  }

  def onButton1 = {
    addThinRhomb
  }

  def onButton2 = {
    addThickRhomb
  }

  def onButton3 = {

  }

  def onButton4 = {

  }
}
