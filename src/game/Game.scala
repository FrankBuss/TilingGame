package game

import scala.math._

class Game {
  var field = new Field
  var currentTile: Polygon = NullTile
  var currentTileSide = 0
  var outlineLineIndex = 0
  var blinkingTimer = new CyclicTimer(1000)

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
    if (outlineLineIndex >= field.outlineLines.length)
      outlineLineIndex = 0
    updateCurrentTile
  }

  def onRight = {
    outlineLineIndex -= 1
    if (outlineLineIndex < 0)
      outlineLineIndex = field.outlineLines.length - 1
    updateCurrentTile
  }

  def updateCurrentTile = {
    currentTile = currentTile.align(field.outlineLines(outlineLineIndex), currentTileSide)
  }

  def intersects = field.tiles.exists(tile => currentTile.intersects(tile))

  def addTile(tile: Tile) = {
    if (!intersects) {
      field.addTile(currentTile)
      currentTile = tile
      updateCurrentTile
    }
  }

  def onButton1 = {
    addTile(ThinRhombTile)
  }

  def onButton2 = {
    addTile(ThickRhombTile)
  }

  def onButton3 = {

  }

  def onButton4 = {

  }

  def newGame = {
    val r1 = ThickRhombTile
    val r2 = ThickRhombTile.align(r1.render()(1), 0)
    val r3 = ThickRhombTile.align(r2.render()(3), 0)
    val r4 = ThickRhombTile.align(r3.render()(3), 0)
    field.tiles = List(r1, r2, r3, r4)
    field.updateLines
    currentTileSide = 1
    outlineLineIndex = 7
    currentTile = ThinRhombTile
    updateCurrentTile
  }

  def getLines: List[Line] = {
    var list = field.getLines

    blinkingTimer.update
    val blinkColor = abs(blinkingTimer.time.toDouble - 500) / 500
    val lines = currentTile.render(blinkColor)
    if (intersects) {
      if (lines.length > 2) {
        list ::= Line(lines(0).p1, lines(2).p1, blinkColor)
        list ::= Line(lines(1).p1, lines(3).p1, blinkColor)
      }
    }
    list ++= lines

    list
  }
}
