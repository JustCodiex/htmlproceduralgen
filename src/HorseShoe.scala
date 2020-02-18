package Project;

class HorseShoe(seats: Int) {

  private sealed case class Seat(x: Double, y: Double, var colour: String, r: Int, var seated: Boolean);

  private sealed case class LegParty(name: String, var colour: String, seats: Int);

  val rows: Int = seats / 10;
  val w = 1700;
  val h = 640;
  val seat_svg_size = 8;
  val seat_svg_spacing = 2;
  val seats_front_row: Int = seats / rows;

  private var parties: List[LegParty] = Nil;

  private var seatIndices: List[Array[Int]] = List();

  private var shoe: Array[Seat] = new Array[Seat](seats);

  def partiesFromTable(source: Table, seatIndex: Int): Unit = {
    for (e <- source.entries){
      parties = LegParty(e(0).toString(), "white", e(seatIndex).toString.toInt) :: parties;
    }
    parties.sortBy(x => x.seats);
  }

  def emptyRow(sz: Int, d: Int): Array[Int] = {
    var k = new Array[Int](sz);
    for (i <- 0 to sz-1) k(i) = d;
    k;
  }

  def calculaterows(): List[Int] = {

    var front_row: Int = (seats * 0.12).asInstanceOf[Int];

    if (front_row > 70)
      front_row = 45;

    var seats_in_row = List[Int](front_row);
    var remaining_seats = seats - front_row;
    var decrement_by = front_row + 3;

    while (remaining_seats > 0) {

      if ((remaining_seats - (decrement_by )) > 0) {
        seats_in_row = decrement_by :: seats_in_row;
        remaining_seats -= decrement_by;
        decrement_by += 3;
      } else{
        var rIndex = 0;
        val a: Array[Int] = seats_in_row.toArray;
        while (remaining_seats > 0){
          a(rIndex) += 1;
          remaining_seats -= 1;
          rIndex += 1;
          if (rIndex >= seats_in_row.length){
            rIndex = 0;
          }
        }
        seats_in_row = a.toList;
      }

    }


    seats_in_row = seats_in_row.sortBy(x => x)
    return seats_in_row;

  }

  def build(): Unit= {

    val seats_per_row = calculaterows();
    var sIndex = 0;
    var rIndex = 0;
    var dst_to_centre = seats_per_row(0) * ((seat_svg_size * 2.0) / seat_svg_spacing);

    for (current_row <- seats_per_row) {

      val angle_decrement = 180.0 / current_row;
      var angle = 180.0 - (angle_decrement / 2.0);

      val arr = emptyRow(current_row, -1);

      for (i <- 0 until current_row) {

        val x = (w / 2.0) + (math.cos(math.toRadians(angle)) * dst_to_centre);
        val y = h - (math.sin(math.toRadians(angle)) * dst_to_centre);

        shoe(sIndex) = Seat(x, y, "white", rIndex, false);
        arr(i) = sIndex;

        sIndex += 1;

        angle -= angle_decrement;

      }

      dst_to_centre += (seat_svg_size * 2) * 1.25

      rIndex += 1;
      seatIndices = arr :: seatIndices;


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

    var pIndex = 0;
    var rIndex = seatIndices.length - 1;

    var rMinIndex = 0;

    val cIndex = emptyRow(seatIndices.length, 0);
    val pSeats = parties.map(x => x.seats).toArray;

    for (_ <-0 until seats) {

      val c = cIndex(rIndex);
      shoe(seatIndices(rIndex)(c)).colour = parties(pIndex).colour;

      pSeats(pIndex) -= 1;
      if (pSeats(pIndex) <= 0){
        pIndex += 1
      }

      cIndex(rIndex) += 1;
      if (cIndex(rIndex) > seatIndices(rIndex).length-1){
        rMinIndex += 1;
      }

      rIndex -= 1;

      if (rIndex < rMinIndex) {
        rIndex = seatIndices.length - 1;
      }

    }
  }

  def colour2(): Unit = {

    var pIndexMin = 0;
    var pIndexMax = parties.length - 1;
    var rIndex = seatIndices.length - 1;

    var rMinIndex = 0;

    val cIndex = emptyRow(seatIndices.length, 0);
    val pSeats = parties.map(x => x.seats).toArray;

    var invert = false;

    for (_ <-0 until seats) {

      val c = if (invert) seatIndices(rIndex).length - 1 - cIndex(rIndex) else cIndex(rIndex)

      val pIndex = if (invert) pIndexMax else pIndexMin
      println(pIndex + ":" + invert);
      val colour = parties(pIndex).colour;

      val sIndex = seatIndices(rIndex)(c);

      if (!shoe(sIndex).seated) {

        shoe(sIndex).colour = colour;
        shoe(sIndex).seated = true;

        pSeats(pIndex) -= 1;
        if (pSeats(pIndex) <= 0) {
          if (invert && pIndexMax - 1 >= pIndexMin) {
            pIndexMax -= 1
          } else {
            pIndexMin += 1
          }
        }

      }

      val cut = seatIndices(rIndex).length / 2

      if (invert) {

        if (cIndex(rIndex) + 1 == cut) {
          rMinIndex += 1;
        } else {
          cIndex(rIndex) += 1;
        }

        rIndex -= 1;

        if (rIndex < rMinIndex) {
          rIndex = seatIndices.length - 1;
        }

      } else{

        if (cIndex(rIndex) + 1 == cut) {

          if (seatIndices(rIndex).length % 2 != 0){
            val s = seatIndices(rIndex)(c+1);
            shoe(s).colour = colour;
            shoe(s).seated = true;
            pSeats(pIndexMin) -= 1;
            if (pSeats(pIndexMin) - 1 <= 0) {
              pIndexMin += 1;
            }
          }

        }

      }

      invert = !invert

    }

    println("Done");

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
