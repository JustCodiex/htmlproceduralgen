package Project

sealed abstract  class Border()
case class SolidBorder(colour: String, size: Double, centreText: Option[Boolean] = None) extends Border
case object NilBorder extends  Border

class Table(columns: Array[String]) {

  var entries: List[Array[Any]] = Nil

  private val last: Array[Any] = new Array[Any](columns.length)
  private var lastInclude = false

  private var width: Option[Int] = None
  private var centre: Option[Boolean] = None

  private var tableBorder: Border = NilBorder
  private var columnBorder: Border = NilBorder
  private var rowBorder: Array[Border] = Array.fill(entries.length)(NilBorder)

  def size(): Int = { this.entries.length }

  def addEntry(values: Array[Any]): Unit={
    if (values.length == columns.length){
      entries = values :: entries
      rowBorder = Array.fill(entries.length)(NilBorder)
    } else{
      throw new IllegalArgumentException("Too few or too many arguments")
    }
  }

  def sort(index: Int): Unit = {
    entries = entries.sortBy(x => x(index).asInstanceOf[Int])
  }

  def sum(index: Int): Unit = {
    var total = 0
    for (e <- entries)
      total += e(index).asInstanceOf[Int]
    last(index) = total
    lastInclude = true
  }

  def setWidth(w: Int): Unit = {
    width = Option(w)
  }

  def setTableBorder(border: Border): Unit = { tableBorder = border }
  def setColumnBorder(border: Border): Unit = { columnBorder = border }
  def setRowBorder(border: Border, index: Int): Unit = { rowBorder(index) = border }
  def setRowBorder(border: Border): Unit = { for (i <- columns.indices) rowBorder(i) = border }
  def setCentre(centre: Boolean): Unit = { this.centre = Option(centre) }

  def getBorderAsHtmlString(border: Border): String = {
    border match {
      case NilBorder => ""
      case SolidBorder(colour, size, b) => b match {
        case Some(b) =>
          if (b) "border:" + size + "px solid " + colour + "; border-collapse: collapse; text-align: left;"
          else "border:" + size + "px solid " + colour + "; border-collapse: collapse;"
        case None => "border:" + size + "px solid " + colour + "; border-collapse: collapse;"
      }
    }
  }

  def getWidth(w: Option[Int]): String = {
    w match {
      case Some(i) => "width: " + i + "px;"
      case None => ""
    }
  }

  def getCentreHTML(c: Option[Boolean]): String = {
    c match {
      case Some(b) => if (b) "margin: 0px auto;" else ""
      case None => ""
    }
  }

  def getStyle(border: Option[Border], width: Option[Int] = None, putCentre: Option[Boolean] = None): String = {
    " style=\"" + getBorderAsHtmlString(border.getOrElse(NilBorder)) + getWidth(width) + getCentreHTML(putCentre) + "\""
  }

  def getHtml: String = {
    entries = entries.reverse
    var html = "<table" + getStyle(Option(tableBorder), width, centre) + ">\n"
    html += "<tr" + getStyle(Option(rowBorder(0))) + ">"
    for (c <- columns) html += "<th" + getStyle(Option(columnBorder)) + ">" + c + "</th>\n"
    html += "</tr>"
    for (e <- entries.indices) {
      html += "<tr" + getStyle(Option(rowBorder(e))) + ">\n"
      for (o <- entries(e).indices) html += "\t<th" + getStyle(Option(columnBorder)) + ">" + entries(e)(o) + "</th>\n"
      html += "</tr>\n"
    }
    if (lastInclude) {
      html += "<tr" + getStyle(Option(rowBorder(0))) + ">"
      for (o <- last) {
        if (o != null) {
          html += "<th" + getStyle(Option(columnBorder)) + ">" + o + "</th>\n"
        } else{
          html += "<th></th>"
        }
      }
      html += "</tr>\n"
    }
    html += "</table>\n"
    html
  }

}
