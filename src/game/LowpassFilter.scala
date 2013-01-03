package game

class LowpassFilter(factor: Double) {
  var filtered: Double = 0

  def update(value: Double) = {
    filtered += (value - filtered) * factor
    filtered
  }
}
