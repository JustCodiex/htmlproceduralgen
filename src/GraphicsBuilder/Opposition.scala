package GraphicsBuilder

class Opposition(seats: Int, isVertical: Boolean) extends ParliamentaryComposition(seats) {

  private val SeatsMajority = seats / 2 + 1

  def build(): Unit = {
    this.build(SeatsMajority) // default
  }

  def build(majority: Int): Unit = {

    val centre = w / 2.0
    val centreHeight = h / 2.0
    val seat_sz = seat_svg_size * 2 + seat_svg_spacing * 2

    var dirMod = -1.0
    val dst_to_mid = 60.0

    def getPos(r: Double, c: Double): (Double, Double) = {
      var x = centre + (((r * seat_sz ) + dst_to_mid) * dirMod)
      var y = dst_to_mid + c * seat_sz
      if (!isVertical){
        val t = x
        x = y + (centre / 2)
        y = (t - centre) + centreHeight
      }
      (x,y)
    }

    val oppositions = Array(majority, seats - majority)
    var sIndex = 0

    for (i <- oppositions.indices) {

      val columns = (oppositions(i) / 11)
      val rows = 11
      val rectSeats = rows * columns

      for (c <- 0 until columns) {
        for (r <- 0 until rows) {

          val xy = getPos(r,c)

          composition(sIndex) = Seat(xy._1, xy._2, "white", r, false)
          sIndex += 1

        }

      }

      for (last <- 0 until oppositions(i) - rectSeats){

        val xy = getPos(last,columns)

        composition(sIndex) = Seat(xy._1, xy._2, "white", rows + 1, false)

        sIndex += 1

      }

      dirMod = -dirMod

    }

  }

  def colour(): Unit = {

    var pIndex = 0
    val pSeats = parties.map(x => x.seats).toArray

    for (i <- 0 until seats){

      composition(i).colour = parties(pIndex).colour
      composition(i).seated = true

      pSeats(pIndex) -= 1

      if (pSeats(pIndex) < 0) {
        pIndex += 1
      }

    }

  }

}
