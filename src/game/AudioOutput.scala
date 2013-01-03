package game

import javax.sound.sampled._
import scala.math._
import scala.util._

object AudioOutput {
  val sampleRate = 22050

  // create 16 bit signed stereo output byte array from mono samples input, normalized to max amplitude
  val monoSamples = scala.collection.mutable.ArrayBuffer.empty[Double]
  def startSamples = monoSamples.clear
  def sampleOut(sample: Double) = monoSamples += sample
  def createStereoSamples = {
    var minSample = Double.MaxValue
    var maxSample = Double.MinValue
    monoSamples.foreach(sample => {
      if (minSample > sample) minSample = sample
      if (maxSample < sample) maxSample = sample
    })
    val amplitude = 32000.0 / max(abs(maxSample), abs(minSample)).toFloat
    val samples = Array.fill[Byte](monoSamples.length * 4)(0)
    for (i <- 0 until monoSamples.length) {
      val sample = monoSamples(i)
      val leftSample = (sample * amplitude).toInt
      val rightSample = leftSample
      val adr = i * 4
      samples(adr + 0) = leftSample.toByte
      samples(adr + 1) = (leftSample >>> 8).toByte
      samples(adr + 2) = (rightSample).toByte
      samples(adr + 3) = (rightSample >>> 8).toByte
    }
    samples
  }

  // create a clack sound
  val clackLength = 0.05
  val clackSampleCount = (clackLength * sampleRate).toInt
  startSamples
  var filtered: Double = 0
  for (i <- 0 until clackSampleCount) {
    val random: Double = Random.nextDouble
    filtered = (random - filtered) * 0.2
    sampleOut(filtered * (1.0 / clackSampleCount) * i)
  }
  val clackSamples = createStereoSamples

  // create a "game over" sound
  val noteNames = List("c", "c#", "d", "d#", "e", "f", "f#",
    "g", "g#", "a", "a#", "b")
  val noteFrequencies = Array.fill[Double](12)(0)
  val factor: Double = pow(2.0, 1.0 / 12.0);
  val base: Double = 120
  for (i <- 0 until 12) {
    noteFrequencies(i) = pow(factor, i) * base
  }
  startSamples
  val song = "b g f# d c#"
  song.split(" ").foreach(note => {
    val freq = noteFrequencies(noteNames.indexOf(note))
    val attack = 50
    val release = 1000
    val length = 2000
    for (i <- 0 to length) {
      val time = i.toDouble / sampleRate
      var sample = signum(sin(freq * 2.0 * Pi * time))
      if (i < attack) sample = sample / attack.toDouble * i
      if (i > length - release) sample = sample / release.toDouble * (length - i)
      sampleOut(sample)
    }
  })
  val gameOverSamples = createStereoSamples

  // play the clack sound
  def clack = play(clackSamples)

  // play the game over sound
  def gameOver = play(gameOverSamples)

  private def play(samples: Array[Byte]) = {
    new Thread() {
      override def run() = {
        val audioformat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, sampleRate, 16, 2, 4, sampleRate, false)
        val datalineinfo = new DataLine.Info(classOf[SourceDataLine], audioformat)
        if (!AudioSystem.isLineSupported(datalineinfo)) {
          Console.println("Line matching " + datalineinfo + " is not supported.");
        } else {
          val sourcedataline = AudioSystem.getLine(datalineinfo).asInstanceOf[SourceDataLine];
          sourcedataline.open(audioformat);
          sourcedataline.start();
          val amplitude: Float = 32000
          sourcedataline.write(samples, 0, samples.length)
          sourcedataline.drain()
          sourcedataline.stop()
          sourcedataline.close()
        }
      }
    }.start()
  }

}
