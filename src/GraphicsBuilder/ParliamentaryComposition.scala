package GraphicsBuilder

import HtmlCompiler._

sealed case class Seat(x: Double, y: Double, var colour: String, r: Int, var seated: Boolean){
  var animations: List[SVGAnimation] = List[SVGAnimation]()
}

abstract class ParliamentaryComposition(seats: Int) {

  protected var w = 1700.0
  protected var h = 740.0

  def getWidth: Double = w
  def getHeight: Double = h

  val seat_svg_size = 8
  val seat_svg_spacing = 2

  private var centreSVG: Boolean = false

  protected var parties: PartyList = new PartyList()

  protected val composition: Array[Seat] = new Array[Seat](seats)

  def setParties(partyList: PartyList): Unit = { parties = partyList }

  def build(): Unit

  def colour(): Unit

  def addOpacityAnimation(duration: Double, maxDelay: Double): Unit = {
    for (seat <- composition){
      val delay = (180.0 - math.atan2(h - seat.y, seat.x - (w/2)).toDegrees) / 180.0 * maxDelay
      seat.animations = SVGFadeInAnimation(0, 1, duration, delay) :: seat.animations
    }
  }

  def setCentre(centre: Boolean): Unit = { centreSVG = centre }

  protected def getStyle: String = {
    var result = " style=\""
    if (centreSVG) result += "margin-left:auto; margin-right:auto; display:block;"
    result += "\""
    result
  }

  def getHtml: String = {

    println(s"Generating HTML for parliamentary composition with $seats seats")

    var result = "<svg width=\"" + w + "\" height=\"" + h + "\"" + getStyle + ">\n"

    for (i <- composition.indices) {

      val s = composition(i)

      var circle = "<circle cx=\"" + s.x + "\" cy=\"" + s.y + "\" r=\"" + seat_svg_size + "\" stroke=\"black\" stroke-width=\"2\" fill=\"" + s.colour + "\""
      var animations = ""

      for (anim <- s.animations){
        anim match {
          case SVGFadeInAnimation(f,e,d,w) =>
            circle += " opacity=\"" + 0 + "\""
            animations += "<animate attributeType=\"CSS\" attributeName=\"opacity\" from=\"" + f + "\" to=\"" + e + "\" dur=\"" + d + "s\" begin=\"" + w + "s\" fill=\"freeze\"/>"
        }
      }

      circle += ">\n\t" + animations + "\n</circle>\n"

      result += circle

    }

    result += "<text x=\"50%\" y=\"" + (h - 12.0) + "\" font-size=\"22\" font-family=\"Verdana\" alignment-baseline=\"middle\" text-anchor=\"middle\">" + seats + "</text>"

    result += "</svg>\n"
    result

  }

}
