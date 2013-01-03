package game

class CyclicTimer(cycleTimeMilliseconds: Int) {
  var lastTime: Long = 0
  var time: Long = 0

  def update = {
    val t = System.currentTimeMillis()
    var delta = t - lastTime
    if (delta > 100) {
      delta = 100
    }
    if (delta < 0) {
      delta = 0
    }
    lastTime = t
    time += delta
    if (time > cycleTimeMilliseconds) time -= cycleTimeMilliseconds
  }

  def reset = lastTime = System.currentTimeMillis()
}
