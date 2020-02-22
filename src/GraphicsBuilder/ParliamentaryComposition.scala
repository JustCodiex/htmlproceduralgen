package GraphicsBuilder

import HtmlCompiler.{SVGAnimation, SVGFadeInAnimation, Table}

sealed case class Seat(x: Double, y: Double, var colour: String, r: Int, var seated: Boolean){
  var animations: List[SVGAnimation] = List[SVGAnimation]()
}

sealed case class LegParty(name: String, var colour: String, seats: Int)

abstract class ParliamentaryComposition(seats: Int) {

  protected val w = 1700
  protected val h = 740

  val seat_svg_size = 8
  val seat_svg_spacing = 2

  private var centreSVG: Boolean = false

  protected var parties: List[LegParty] = Nil

  protected val composition: Array[Seat] = new Array[Seat](seats)

  def partiesFromTable(source: Table, seatIndex: Int): Unit = {
    for (e <- source.entries){
      parties = LegParty(e(0).toString, "white", e(seatIndex).toString.toInt) :: parties
    }
    parties = parties.filter(x => x.seats > 0).sortBy(x => x.seats).reverse
  }

  def setPartyColour(name: String, colour: String): Unit = {
    for (p <- parties){
      if (p.name == name){
        p.colour = colour
        return
      }
    }
  }

  def setPartyOrder(order: Array[String]): Unit = {

    var partyOrder = List[LegParty]()

    for (p <- order) {
      partyOrder = parties.find(x => x.name == p).orNull :: partyOrder
    }

    parties = partyOrder.distinct.filter(x => x != null).reverse

  }

  def build(): Unit

  def colour(): Unit

  def addOpacityAnimation(duration: Double, maxDelay: Double): Unit = {
    val minx: Double = composition.minBy(x => x.x).x - 2
    val maxx: Double = composition.maxBy(x => x.x).x

    val diff = maxx - minx
    for (seat <- composition){
      val delay = (180.0 - math.atan2((h - seat.y), (seat.x - (w/2))).toDegrees) / 180.0 * maxDelay
      if (seat.r == 0) {
        println(math.atan2((h - seat.y), (seat.x - (w/2))).toDegrees + "°")
      }
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
