package game

import java.awt.Graphics
import java.awt.Color
import java.awt.event._
import javax.swing._
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.BasicStroke

class FieldComponent(field: Field) extends JComponent with MouseListener with MouseMotionListener {
  var scale = 1.0
  var ofs = Vector2d(0, 0)

  def mouseClicked(arg0: MouseEvent) = {}

  def mousePressed(arg0: MouseEvent) = {}

  def mouseReleased(arg0: MouseEvent) = {}

  def mouseEntered(arg0: MouseEvent) = {}

  def mouseExited(arg0: MouseEvent) = {}

  def mouseDragged(arg0: MouseEvent) = {}

  def mouseMoved(arg0: MouseEvent) = {}

  override def paintComponent(g: Graphics) = {
    var g2 = g.asInstanceOf[Graphics2D]
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
      RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setStroke(new BasicStroke(2))

    // fill background
    val w: Double = getWidth()
    val h: Double = getHeight()
    g2.setColor(Color.BLACK)
    g2.fillRect(0, 0, w.toInt, h.toInt)

    // draw lines
    val lines: List[Line] = field.getLines
    if (!lines.isEmpty) {
      // get boundary, with some margin
      val points = lines.flatMap(l => List(l.p1, l.p2))
      val xs = points.map(p => p.x)
      val ys = points.map(p => p.y)
      val topLeft = Vector2d(xs.min, ys.min)
      val bottomRight = Vector2d(xs.max, ys.max)

      // calculate proportional scale factor
      val dx = bottomRight.x - topLeft.x
      val dy = bottomRight.y - topLeft.y
      val linesAspect = dx / dy
      val viewAspect = w / h
      val scaleTarget: Double = if (linesAspect > viewAspect) {
        w / (dx * 1.1)
      } else {
        h / (dy * 1.1)
      }
      scale += (scaleTarget - scale) / 20

      // calculate translation
      val targetOfs = Vector2d((w - dx * scale) / 2, (h - dy * scale) / 2) -
        scale * topLeft
      ofs += 0.05 * (targetOfs - ofs)

      // draw lines
      implicit def toInt(d: Double) = d.toInt
      lines.foreach { line =>
        val p1 = scale * line.p1 + ofs
        val p2 = scale * line.p2 + ofs
        var component = line.intensity * 255.0
        if (component > 255) component = 255
        if (component < 0) component = 0
        val c = component.toInt
        g2.setColor(new Color(c, c, c))
        g2.drawLine(p1.x, p1.y, p2.x, p2.y)

        // draw small arrow for each line
        if (false) {
          val direction = p2 - p1
          val startPoint = p2 - 0.1 * direction
          val stroke = startPoint - p2
          val leftStroke = stroke.rotate(10) + p2
          val rightStroke = stroke.rotate(-10) + p2
          g2.drawLine(p2.x, p2.y, leftStroke.x, leftStroke.y)
          g2.drawLine(p2.x, p2.y, rightStroke.x, rightStroke.y)
        }
      }
    }
  }

}