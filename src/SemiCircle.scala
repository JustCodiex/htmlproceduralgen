package Project;

class SemiCircle(seats: Int) extends ParliamentaryComposition(seats) {

  val rows: Int = seats / 10
  val seats_front_row: Int = seats / rows

  private var seatIndices: List[Array[Int]] = List()

  def emptyRow(sz: Int, d: Int): Array[Int] = {
    val k = new Array[Int](sz)
    for (i <- 0 until sz) k(i) = d
    k;
  }

  def calculaterows(): List[Int] = {

    var front_row: Int = (seats * 0.125).asInstanceOf[Int]

    if (front_row > 32)
      front_row = 32

    var seats_in_row = List[Int](front_row)
    var remaining_seats = seats - front_row
    var decrement_by = front_row + 3

    while (remaining_seats > 0) {

      if ((remaining_seats - decrement_by ) > 0) {
        seats_in_row = decrement_by :: seats_in_row
        remaining_seats -= decrement_by
        decrement_by += 3
      } else{
        var rIndex = 0
        val a: Array[Int] = seats_in_row.toArray
        while (remaining_seats > 0){
          a(rIndex) += 1
          remaining_seats -= 1
          rIndex += 1
          if (rIndex >= seats_in_row.length){
            rIndex = 0
          }
        }
        seats_in_row = a.toList
      }

    }

    seats_in_row = seats_in_row.sortBy(x => x)
    seats_in_row

  }

  def build(): Unit= {

    val seats_per_row = calculaterows()
    var sIndex = 0
    var rIndex = 0
    var dst_to_centre = seats_per_row(0) * ((seat_svg_size * 2.0) / seat_svg_spacing)

    for (current_row <- seats_per_row) {

      val angle_decrement = 180.0 / current_row
      var angle = 180.0 - (angle_decrement / 2.0)

      val arr = emptyRow(current_row, -1)

      for (i <- 0 until current_row) {

        val x = (w / 2.0) + (math.cos(math.toRadians(angle)) * dst_to_centre)
        val y = h - (math.sin(math.toRadians(angle)) * dst_to_centre)

        composition(sIndex) = Seat(x, y, "white", rIndex, false)
        arr(i) = sIndex

        sIndex += 1

        angle -= angle_decrement

      }

      dst_to_centre += (seat_svg_size * 2) * 1.25

      rIndex += 1
      seatIndices = arr :: seatIndices


    }

    seatIndices = seatIndices.reverse

  }

  def colour(): Unit = {
    this.colour(true)
  }

  def colour(outwards: Boolean): Unit = {

    var pIndexMin = 0
    var pIndexMax = parties.length - 1
    var rIndex = if (outwards) seatIndices.length - 1 else 0

    var rMinIndex = 0

    val cIndex = emptyRow(seatIndices.length, 0)
    val pSeats = parties.map(x => x.seats).toArray // Also used to validate we've allocated correctly

    var invert = false

    for (_ <- 0 until seats) {

      val c = if (invert) seatIndices(rIndex).length - 1 - cIndex(rIndex) else cIndex(rIndex)

      val pIndex = if (invert) pIndexMax else pIndexMin
      val colour = parties(pIndex).colour

      val sIndex = seatIndices(rIndex)(c)

      if (!composition(sIndex).seated) {

        composition(sIndex).colour = colour
        composition(sIndex).seated = true

        pSeats(pIndex) -= 1
        if (pSeats(pIndex) <= 0) {
          if (invert && pIndexMax - 1 >= pIndexMin) {
            pIndexMax -= 1
          } else {
            if (pIndexMin + 1 <= pIndexMax) {
              pIndexMin += 1
            }
          }
        }

      }

      val cut = seatIndices(rIndex).length / 2

      if (invert) {

        if (cIndex(rIndex) + 1 == cut) {
          if (outwards || (!outwards && rMinIndex + 1 < seatIndices.length)) {
            rMinIndex += 1
          }
        } else {
          cIndex(rIndex) += 1
        }

        if (outwards) {
          rIndex -= 1
          if (rIndex < rMinIndex) {
            rIndex = seatIndices.length - 1
          }
        }
        else {
          rIndex += 1
          if (rIndex >= seatIndices.length) {
            rIndex = rMinIndex
          }
        }

      } else{

        if (cIndex(rIndex) + 1 == cut) {

          if (seatIndices(rIndex).length % 2 != 0){ // Only assign using left-hand party (since inverse = false)
            val s = seatIndices(rIndex)(c+1)
            composition(s).colour = colour
            composition(s).seated = true
            pSeats(pIndexMin) -= 1
            if (pSeats(pIndexMin) - 1 <= 0) {
              if (pIndexMin + 1 <= pIndexMax) {
                pIndexMin += 1
              }
            }
          }

        }

      }

      invert = !invert

    }

  }

}
