package Project;

class HorseShoe(seats: Int) {

  private sealed case class Seat(x: Double, y: Double, var colour: String, r: Int);

  private sealed case class LegParty(name: String, var colour: String, seats: Int);

  val rows: Int = seats / 10;
  val w = 1700;
  val h = 640;
  val seat_svg_size = 8;
  val seat_svg_spacing = 2;
  val seats_front_row: Int = seats / rows;

  private var parties: List[LegParty] = Nil;

  private var seatIndices: List[Array[Int]] = Nil;

  private var shoe: Array[Seat] = new Array[Seat](seats);

  def partiesFromTable(source: Table, seatIndex: Int): Unit = {
    for (e <- source.entries){
      parties = LegParty(e(0).toString(), "white", e(seatIndex).toString.toInt) :: parties;
    }
  }

  def emptyRow(sz: Int, d: Int): Array[Int] = {
    var k = new Array[Int](sz);
    for (i <- 0 to sz-1) k(i) = d;
    k;
  }

  def build(): Unit= {

    var current_row = seats_front_row;
    var total = 0;
    var dst_to_centre = ((seats_front_row * (seat_svg_size * 2)) / seat_svg_spacing);

    var sIndex = 0;
    var rIndex = 0;

    while (total < seats) {

      val angle_decrement = 180.0 / current_row;
      var angle = 180.0 - (angle_decrement / 2.0);

      var arr = emptyRow(current_row, -1);

      for (i <- 1 to current_row) {

        if (total + i <= seats) {

          val x = (w / 2.0) + (math.cos(math.toRadians(angle)) * dst_to_centre);
          val y = h - (math.sin(math.toRadians(angle)) * dst_to_centre);

          shoe(sIndex) = Seat(x, y, "white", rIndex);
          arr(i-1) = sIndex;

          sIndex += 1;

          angle -= angle_decrement;

        }
      }

      seatIndices = arr :: seatIndices;

      dst_to_centre += (seat_svg_size + seat_svg_spacing) * 2;
      total += current_row;
      current_row += 3;
      rIndex += 1;

    }

    seatIndices = seatIndices.reverse;

  }

  def setPartyColour(name: String, colour: String): Unit = {
    for (p <- parties){
      if (p.name == name){
        p.colour = colour;
        return;
      }
    }
  }

  def colour(): Unit = {

    var k = 0;
    var l = emptyRow(seatIndices.length, 0);

    for (p <- parties) {

      var fill = p.seats;
      //var f = if (k==0) 1 else k * 3;

      while (fill > 0) {
        if (fill > 0) {

          val a = seatIndices(k);
          val i = l(k);

          if (i < a.length) {

            val s = a(i);

            if (s != -1) {

              shoe(s).colour = p.colour;

              fill -= 1;

            }

          }

          l(k) += 1;
          k += 1;

          if (k >= seatIndices.length){
            k = 0;
          }

        }

      }

    }

  }

  def getHtml(): String = {

    var result = "<svg style=\"width: " + w + "px; height: " + h + "px;\">\n"

    for (i <- 0 to shoe.length - 1) {

      val s = shoe(i)
      result += "<circle cx= \"" + s.x + "\" cy= \"" + s.y + "\" r= \"8\" stroke= \"black\" stroke-width= \"2\" fill= \"" + s.colour + "\"/>\n";

    }

    result += "</svg>"
    result;

  }

}
