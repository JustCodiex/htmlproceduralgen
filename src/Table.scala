package Project;

class Table(columns: Array[String]) {

  var entries: List[Array[Any]] = Nil;

  def size(): Int = { this.entries.length }

  def addEntry(values: Array[Any]): Unit={
    if (values.length == columns.length){
      entries = values :: entries;
    } else{
      throw new IllegalArgumentException("Too few or too many arguments");
    }
  }

  def getHtml(): String = {
    var html = "<table>\n";
    html += "<tr>";
    for (c <- columns) html += "<th>" + c + "</th>\n"
    html += "</tr>";
    for (e <- entries.reverse) {
      html += "<tr>";
      for (o <- e) html += "<th>" + o + "</th>\n"
      html += "</tr>";
    }
    html += "</table>"
    html;
  }

}
