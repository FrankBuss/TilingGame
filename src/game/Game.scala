package game

import scala.math._
import scala.util._

class Game {
  var field = new Field
  var currentTile: Polygon = NullTile
  var currentTileSide = 0
  var outlineLineIndex = 0
  var blinkingTimer = new CyclicTimer(1000)
  var score: Int = 0
  var delay: Int = 5
  var circle = new Polygon(1, List.fill(36)(10)).move(Vector2d(0, -4))
  var circleSize: Int = 5
  var gameOver = false
  var tileTimeStart: Long = 0

  def updateCircle = {
    circle = new Polygon(circleSize.toDouble / 5, List.fill(36)(10))
    circle.render
    val boundingBox = circle.boundingBox
    circle = circle.move(Vector2d(0, (boundingBox.top - boundingBox.bottom) / 2 + 2))
  }
  updateCircle

  def wrap(value: Int, max: Int) = {
    if (value < 0) {
      max - 1
    } else if (value >= max) {
      0
    } else {
      value
    }
  }

  def onUpDown(ofs: Int, loop: Boolean): Unit = {
    if (gameOver) return
    currentTileSide = wrap(currentTileSide + ofs, currentTile.points.length)
    updateCurrentTile
    if (loop) {
      if (intersects) onUpDown(ofs, true)
    }
  }

  def onUp = onUpDown(-1, true)

  def onDown = onUpDown(1, true)

  def onLeftRight(ofs: Int): Unit = {
    if (gameOver) return
    val lastCurrentTile = currentTile
    def onLeftRightImpl(ofs: Int): Unit = {
      outlineLineIndex = wrap(outlineLineIndex + ofs, field.outlineLines.length)
      updateCurrentTile
      if (intersects) {
        def upDownTest(): Unit = {
          for (i <- 0 until currentTile.points.length) {
            onUpDown(1, false)
            if (!intersects)
              return
          }
        }
        upDownTest
      }
      if (intersects || lastCurrentTile.isCoincident(currentTile))
        onLeftRightImpl(ofs)
    }
    onLeftRightImpl(ofs)
  }

  def onLeft = onLeftRight(1)

  def onRight = onLeftRight(-1)

  def updateCurrentTile = {
    currentTile = currentTile.align(field.outlineLines(outlineLineIndex), currentTileSide)
  }

  def intersects = field.tiles.exists(tile => currentTile.intersects(tile))

  def addRandomNewTile = {
    if (currentTile.intersects(circle)) {
      if (Random.nextInt(2) == 1) {
        currentTile = ThinRhombTile
      } else {
        currentTile = ThickRhombTile
      }
      outlineLineIndex = 0
      updateCurrentTile
      onLeft
      blinkingTimer.reset
    } else {
      gameOver = true
      AudioOutput.gameOver
    }
    tileTimeStart = System.currentTimeMillis()
  }

  def onButton1 = {
    if (!intersects && !gameOver) {
      field.addTile(currentTile)
      addRandomNewTile
      score += 1
      AudioOutput.clack
    }
  }

  def onButton2 = {

  }

  def onButton3 = {

  }

  def onButton4 = {

  }

  def changeCircleSize(ofs: Int) = {
    circleSize += ofs;
    if (circleSize > 5) circleSize = 5
    if (circleSize < 1) circleSize = 1
    updateCircle
  }

  def changeDelay(ofs: Int) = {
    delay += ofs
    if (delay > 5) delay = 5
    if (delay < 1) delay = 1
  }

  def newGame = {
    gameOver = false
    field.tiles = List(ThickRhombTile.move(Vector2d(0, 1)))
    field.updateLines
    currentTileSide = 0
    outlineLineIndex = 0
    score = 1
    currentTile = ThickRhombTile
    addRandomNewTile
  }

  def getLines: List[Line] = {
    val time = System.currentTimeMillis()
    if (!gameOver && time - tileTimeStart > delay * 1000) {
      onButton1
    }

    var list = field.getLines(gameOver)

    blinkingTimer.update
    val blinkColor = abs(blinkingTimer.time.toDouble - 500) / 500
    val lines = currentTile.render(blinkColor)
    if (intersects) {
      if (lines.length > 2) {
        list ::= Line(lines(0).p1, lines(2).p1, blinkColor)
        list ::= Line(lines(1).p1, lines(3).p1, blinkColor)
      }
    }
    if (gameOver) {
      list ++= HersheyFont.drawString("Game", -4, 1, 0.1)
      list ++= HersheyFont.drawString("Over", -3, 4, 0.1)
    } else {
      list ++= lines
    }

    list ++= HersheyFont.drawString("Score: " + score, -5, 8.5, 0.04)
    list ++= HersheyFont.drawString("Delay: " + delay, -5, 10, 0.04)
    list ++= HersheyFont.drawString("Circle: " + circleSize, 1, 10, 0.04)

    list ++= circle.render

    val rect = new Rectangle(Vector2d(-6, -5), Vector2d(7, 11))
    list ++= rect.render

    list
  }
}
