package GraphicsBuilder

import HtmlCompiler._

sealed case class LegislativeParty(name: String, var colour: String, seats: Int)

sealed class PartyList {

  private var parties: List[LegislativeParty] = Nil

  def partiesFromTable(source: Table, seatIndex: Int): PartyList = {
    for (e <- source.entries){
      parties = LegislativeParty(e(0).toString, "white", e(seatIndex).toString.toInt) :: parties
    }
    parties = parties.sortBy(x => x.seats).reverse
    this
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

    var partyOrder = List[LegislativeParty]()

    for (p <- order) {
      partyOrder = parties.find(x => x.name == p).orNull :: partyOrder
    }

    parties = partyOrder.distinct.filter(x => x != null).reverse

  }

  def apply(index: Int): LegislativeParty = { this.parties(index) }

  def length: Int = { this.parties.length }

  def indices: Range = { this.parties.indices }

  def toSeats: Array[Int] = { this.parties.map(x => x.seats).toArray }

  def filter(function: Function[LegislativeParty, Boolean]): Unit = { this.parties.filter(function) }

  def update(source: Table, seatIndex: Int): PartyList = {
    val ls = new PartyList().partiesFromTable(source, seatIndex)
    for (p1 <- parties.indices) {
      for (p2 <- ls.parties.indices){
        if (this.parties(p1).name == ls.parties(p2).name){
          ls.parties(p2).colour = this.parties(p1).colour
        }
      }
    }
    ls
  }

}
