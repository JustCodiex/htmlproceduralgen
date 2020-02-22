package HtmlCompiler

sealed abstract class TextAlignment
case object LeftTextAlignment extends TextAlignment
case object RightTextAlignment extends TextAlignment
case object CentreTextAlignment extends TextAlignment

sealed abstract  class Border()
case class SolidBorder(colour: String, size: Double, centreText: TextAlignment = CentreTextAlignment) extends Border
case object NilBorder extends Border

class Table(columns: Array[String]) {

  var entries: List[Array[Any]] = Nil

  private val last: Array[Any] = new Array[Any](columns.length)
  private var lastInclude = false

  private var width: Option[Int] = None
  private var centre: Option[Boolean] = None

  private var tableBorder: Border = NilBorder
  private val columnBorder: Array[Border] = Array.fill(columns.length)(NilBorder)
  private var rowBorder: Array[Border] = Array.fill(entries.length)(NilBorder)

  def size(): Int = { this.entries.length }

  def addEntry(values: Array[Any]): Unit={
    if (values.length == columns.length){
      entries = values :: entries
      rowBorder = Array.fill(entries.length)(NilBorder)
    } else {
      throw new IllegalArgumentException("Too few or too many arguments")
    }
  }

  def sort(index: Int): Unit = {
    entries = entries.sortBy(x => x(index).asInstanceOf[Int]).reverse
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

  def setColumnBorder(border: Border, index: Int): Unit = { columnBorder(index) = border }
  def setColumnBorder(border: Border): Unit = { for (i <- columns.indices) columnBorder(i) = border }

  def setRowBorder(border: Border, index: Int): Unit = { rowBorder(index) = border }
  def setRowBorder(border: Border): Unit = { for (i <- entries.indices) rowBorder(i) = border }

  def setCentre(centre: Boolean): Unit = { this.centre = Option(centre) }

  private def getTextAlignmentName(textAlignment: TextAlignment) = {
    textAlignment match {
      case LeftTextAlignment => "text-align: left;"
      case RightTextAlignment => "text-align: right;"
      case CentreTextAlignment => "text-align: center;"
    }
  }

  def getBorderAsHtmlString(border: Border): String = {
    border match {
      case NilBorder => ""
      case SolidBorder(colour, size, align) => "border:" + size + "px solid " + colour + "; border-collapse: collapse;" + getTextAlignmentName(align)
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
    var html = "<table" + getStyle(Option(tableBorder), width, centre) + ">\n"
    html += "<tr" + getStyle(Option(rowBorder(0))) + ">"
    for (c <- columns.indices) html += "<th" + getStyle(Option(columnBorder(c))) + ">" + columns(c) + "</th>\n"
    html += "</tr>"
    for (e <- entries.indices) {
      html += "<tr" + getStyle(Option(rowBorder(e))) + ">\n"
      for (o <- entries(e).indices) html += "\t<th" + getStyle(Option(columnBorder(o))) + ">" + entries(e)(o) + "</th>\n"
      html += "</tr>\n"
    }
    if (lastInclude) {
      html += "<tr" + getStyle(Option(rowBorder(0))) + ">"
      for (o <- last.indices) {
        if (last(o) != null) {
          html += "<th" + getStyle(Option(columnBorder(o))) + ">" + last(o) + "</th>\n"
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
