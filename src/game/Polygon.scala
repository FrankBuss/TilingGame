package game

class Polygon() {
  // create polygon by length and angles
  def init(sideLength: Double, angles: List[Double]) = {
    var direction = Vector2d(sideLength, 0)
    var start = Vector2d(0, 0)
    points = start :: points
    angles.foreach(angle => {
      start += direction
      points = start :: points
      direction = direction.rotate(angle)
    })
  }

  def this(sideLength: Double, angles: List[Double]) = {
    this()
    init(sideLength, angles)
  }

  // create polygon by points
  def this(points: List[Vector2d]) = {
    this()
    this.points = points
  }

  private var position = Vector2d(0, 0)
  private var rotation: Double = 0
  var points = List[Vector2d]()

  private def deepCopy() = {
    val p = new Polygon(points)
    p.position = position
    p.rotation = rotation
    p
  }

  def move(v: Vector2d) = {
    val moved = deepCopy()
    moved.position += v
    moved
  }

  def rotate(angle: Double) = {
    val rotated = deepCopy()
    rotated.rotation += angle
    rotated
  }

  def renderPoints(): List[Vector2d] = points.map(p => p.rotate(rotation) + position)

  def render(): List[Line] = render(1)

  def render(intensity: Double) = {
    val renderedPoints = renderPoints()
    if (renderedPoints.isEmpty) {
      List[Line]()
    } else {
      for {
        linePair <- (renderedPoints.last :: renderedPoints).zip(renderedPoints)
        line = Line(linePair._1, linePair._2, intensity)
      } yield line
    }
  }

  def align(otherLine: Line, thisSide: Int) = {
    // first step: adjust rotation
    val thisLines = render()
    val thisLine = thisLines(thisSide)
    val thisAngle = (thisLine.p2 - thisLine.p1).calculateRotation()
    val otherAngle = (otherLine.p1 - otherLine.p2).calculateRotation()
    val rotated = rotate(thisAngle - otherAngle)

    // second step: adjust position
    val thisLines2 = rotated.render()
    val thisLine2 = thisLines2(thisSide)
    rotated.move(otherLine.p2 - thisLine2.p1)
  }

  // using the Separating Axis Theorem
  def intersects(other: Polygon): Boolean = {
    val lines1 = render()
    val points1 = lines1.map(line => line.p1)
    val lines2 = other.render()
    val points2 = lines2.map(line => line.p1)

    // calculate min/max of the projection of all points to vector p
    def project(p: Vector2d, points: List[Vector2d]) = {
      var min = Double.MaxValue
      var max = Double.MinValue
      points.foreach(p2 => {
        val dot = p.dot(p2)
        if (dot < min)
          min = dot
        if (dot > max)
          max = dot
      })
      (min, max)
    }

    // check intersection for all axis of lines1
    def intersectsImpl(lines1: List[Line], points1: List[Vector2d], lines2: List[Line], points2: List[Vector2d]): Boolean = {
      lines1.foreach(line1 => {
        // create perpendicular vector
        val d = line1.p2 - line1.p1
        val p = Vector2d(-d.y, d.x)

        // project all points on it
        val (min1, max1) = project(p, points1)
        val (min2, max2) = project(p, points2)

        // test for intersection (allow some overlapping because of floating point imprecision) 
        val overlapping = (max1 - min1) / 10000
        if (max1 - overlapping < min2 || max2 - overlapping < min1)
          return false
      })
      true
    }

    // polygon intersection, if not axis of lines1 and not axis of lines2 exists
    intersectsImpl(lines1, points1, lines2, points2) && intersectsImpl(lines2, points2, lines1, points1)
  }

  def center = {
    var center = Vector2d(0, 0)
    points.foreach(point => { center += point })
    if (!points.isEmpty) {
      center = (1 / points.length) * center
    }
    center
  }

  override def toString: String = {
    var result = ""
    result += "position: " + position + ", rotation: " + rotation + ", points: " + points.mkString(", ")
    result
  }
}
