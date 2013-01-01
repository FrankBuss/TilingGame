package game

import scala.math._

class Field {
  var tiles = List[Polygon]()

  var outlineLines = List[Line]()
  var tilesLines = List[Line]()

  def updateLines = {
    // collect all lines from all tiles
    val lines = tiles.flatMap(tile => tile.render)

    // partition into coincident (inner lines) and non-coincident (outline)
    val partition = lines.groupBy(line => lines.count(line2 => line2.isCoincident(line)) > 1)

    // update lines, without duplicate lines
    if (partition.contains(true)) {
      tilesLines = partition(true).distinct
    }
    if (partition.contains(false)) {
      outlineLines = partition(false).distinct
    }

    // sort outline lines: the start of a line should be the same as end of another line, if possible
    // because for the movement of the new tile at the outline
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

        // not found: return any line which is not already added (for gaps)
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

  def addTile(newTile: Polygon): Unit = {
    tiles ::= newTile
    updateLines
  }

  def getLines: List[Line] = {
    var list = List[Line]()

    // draw tiles
    list ++= tilesLines.map(line => Line(line.p1, line.p2, 0.4))

    // draw outline
    list ++= outlineLines.map(line => Line(line.p1, line.p2, 1.0))

    list
  }
}
