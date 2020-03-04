package GraphicsBuilder

import HtmlCompiler._

class Bar(seats: Int) extends ParliamentaryComposition(seats) {

  private case class BarSegment(colour: String, x: Double, w: Double, seats: Int, share: Float){
    var animations: List[SVGAnimation] = List[SVGAnimation]()
  }

  h = 100
  w = 600

  private var segments: Array[BarSegment] = _

  private var showPercentages = false
  private var showSeats = false
  private var showMajority = false

  def setShowPercentages(show: Boolean): Unit = { showPercentages = show }
  def setShowSeats(show: Boolean): Unit = { showSeats = show }
  def setShowMajority(show: Boolean): Unit = { showMajority = show }
  def setWidth(w: Double): Unit = { this.w = w }

  override def build(): Unit = {

    segments = new Array[BarSegment](parties.length)
    var currentX = 1.0

    for (i <- parties.indices) {

      val party = parties(i)
      val width = (w-2) * (party.seats / this.seats.asInstanceOf[Double])
      val share = party.seats / this.seats.asInstanceOf[Float]

      segments(i) = BarSegment(party.colour, currentX, width, party.seats, share)
      currentX += width

    }

  }

  // Will implicitly call build for you!!
  override def colour(): Unit = {
    this.build()
  }

  override def addOpacityAnimation(duration: Double, maxDelay: Double): Unit = { addOpacityAnimation(duration) }

  def addOpacityAnimation(duration: Double): Unit = {
    var lDelay = 0.0
    for (segment <- segments) {
      val dur = (segment.w / w) * duration
      val fadeAnim = SVGFadeInAnimation(0, segment.w, dur, lDelay)
      segment.animations = fadeAnim :: segment.animations
      lDelay += dur
    }
  }

  private def getHtmlRectAnim(animation: SVGAnimation): String = {
    animation match {
      case SVGFadeInAnimation(from, to, duration, delay) =>
        "\t\t\t<animate attributeName=\"width\" from=\"" + from  + "\" to=\"" + to + "\" dur=\"" + duration + "s\" begin=\"" + delay + "\" fill=\"freeze\"/>\n"
    }
  }

  private def getHtmlRect(x: Double, y: Double, width: Double, fill: String, animations: List[SVGAnimation] = List(), stroke: Boolean = false): String = {
    val w = if (animations.isEmpty) width else 0
    var r = "\t<rect x=\"" + x + "\" y=\"" + y + "\" width=\"" + w + "\" height=\"40\" fill=\"" + fill + "\""
    if (stroke){
      r += "stroke=\"black\""
    }
    if (animations.nonEmpty){
      r += ">\n"
      for (anim <- animations){
        r += getHtmlRectAnim(anim)
      }
      r += "\t\t</rect>\n"
    } else{
      r += "/>\n"
    }
    r
  }

  private def roundAt(p: Int, n: Float): Double = {
    var s = math.pow(10, p)
    s = math.round( n * s) / s
    s
  }

  private def getHtmlText(content: String): String = {
    "<text x=\"50%\" y=\"65%\" alignment-baseline=\"middle\" text-anchor=\"middle\">" + content + "</text>"
  }

  private def getHtmlSegment(barSegment: BarSegment): String = {
    if (showSeats || showPercentages){
      val content = if (!showSeats && showPercentages) roundAt(2, barSegment.share * 100.0f).toString + "%" else barSegment.seats.toString
      if (content.length * 12 < barSegment.w )
        "\t<g transform=\"translate(" + barSegment.x + ",30)\">\n\t" + getHtmlRect(0, 0, barSegment.w, barSegment.colour, barSegment.animations) +
          "\t\t<svg width=\"" + barSegment.w + "\" height=\"40\">\n\t\t\t" + getHtmlText(content) +
          "\n\t\t</svg>\n\t</g>\n"
      else
        getHtmlRect(barSegment.x, 30,  barSegment.w, barSegment.colour, barSegment.animations)
    } else {
      getHtmlRect(barSegment.x, 30,  barSegment.w, barSegment.colour, barSegment.animations)
    }
  }

  override def getHtml: String = {

    if (segments == null)
      throw new Exception("Bar segments not calculated - forgot to build before retrieving HTML?")

    println(s"Generating HTML for parliamentary composition with $seats seats")

    var result = "<svg width=\"" + w + "\" height=\"" + h + "\"" + getStyle + ">\n"
    result += getHtmlRect(0, 30, w, "white", stroke = true)

    for (segment <- segments) {
      result += getHtmlSegment(segment)
    }

    if (showMajority) {
      result += "<line x1=\"50%\" y1=\"27\" x2=\"50%\" y2=\"80\" style=\"stroke:black;stroke-width:3;\"/>\n" +
      "<text x=\"50%\" y=\"95\" alignment-baseline=\"middle\" text-anchor=\"middle\">" + ((this.seats / 2) + 1) + "</text>"
    }

    result += "</svg>\n"
    result

  }

}
