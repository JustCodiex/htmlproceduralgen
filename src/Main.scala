import Project._
import java.math._

object Main {

  def main(args : Array[String]): Unit = {

    val t179 = new Table(Array("Name", "Abbreviation", "Seats"))
    t179.addEntry(("Social Democrats for a United Country" :: "SDUC" :: 56 :: Nil).toArray)
    t179.addEntry(("National Conservatives" :: "NC" :: 49 :: Nil).toArray)
    t179.addEntry(("Union of Liberals and Conservatives" :: "ULC" :: 37 :: Nil).toArray)
    t179.addEntry(("Green Democrats" :: "GD" :: 24 :: Nil).toArray)
    t179.addEntry(("The People's Socialist Alliance" :: "PSA" :: 13 :: Nil).toArray)
    t179.sum(2)
    t179.setTableBorder(SolidBorder("black", 1))
    t179.setColumnBorder(SolidBorder("black", 1))
    t179.setWidth(520)
    t179.setCentre(true)

    val t109 = new Table(Array("Name", "Abbreviation", "Seats"))
    t109.addEntry(("New Republican Party" :: "R" :: 63 :: Nil).toArray)
    t109.addEntry(("Democratic Party" :: "D" :: 46 :: Nil).toArray)
    t109.sum(2)
    t109.setTableBorder(SolidBorder("black", 1))
    t109.setColumnBorder(SolidBorder("black", 1))
    t109.setWidth(520)
    t109.setCentre(true)

    val h109 = new SemiCircle(109)
    h109.partiesFromTable(t109, 2)
    h109.build()

    h109.setPartyColour("New Republican Party", "red")
    h109.setPartyColour("Democratic Party", "blue")

    h109.colour()

    val h179 = new SemiCircle(179)
    h179.partiesFromTable(t179, 2)
    h179.build()

    h179.setPartyColour("Social Democrats for a United Country", "red")
    h179.setPartyColour("National Conservatives", "lightblue")
    h179.setPartyColour("Union of Liberals and Conservatives", "yellow")
    h179.setPartyColour("Green Democrats", "green")
    h179.setPartyColour("The People's Socialist Alliance", "darkred")

    h179.colour()

    val t751 = new Table(Array("Name", "Abbreviation", "Seats"))
    t751.addEntry(("Greens" :: "G" :: 104 :: Nil).toArray)
    t751.addEntry(("The Christian Conservative Party" :: "CC" :: 117 :: Nil).toArray)
    t751.addEntry(("Union of Socialists and Social Democrats" :: "USSD" :: 98 :: Nil).toArray)
    t751.addEntry(("National Conservative and Protectionist Party" :: "NCPP" :: 120 :: Nil).toArray)
    t751.addEntry(("Alliance for Freedom and Liberty" :: "AFL" :: 90 :: Nil).toArray)
    t751.addEntry(("Moderate Democrats and Centrists" :: "MDC" :: 138 :: Nil).toArray)
    t751.addEntry(("Unitary Left" :: "UL" :: 84 :: Nil).toArray)
    t751.sort(2)
    t751.sum(2)
    t751.setTableBorder(SolidBorder("black", 1))
    t751.setColumnBorder(SolidBorder("black", 1))
    t751.setWidth(520)
    t751.setCentre(true)

    val h751 = new SemiCircle(751)
    h751.partiesFromTable(t751, 2)
    h751.build()

    h751.setPartyColour("Greens", "green");
    h751.setPartyColour("The Christian Conservative Party", "black")
    h751.setPartyColour("Union of Socialists and Social Democrats", "red")
    h751.setPartyColour("National Conservative and Protectionist Party", "blue")
    h751.setPartyColour("Alliance for Freedom and Liberty", "orange")
    h751.setPartyColour("Moderate Democrats and Centrists", "yellow")
    h751.setPartyColour("Unitary Left", "darkred")

    h751.colour()
    h751.addOpacityAnimation(0.1, 1)

    new HTMLFile(t179.getHtml + h179.getHtml()).save("test_179.html")
    new HTMLFile(t109.getHtml + h109.getHtml()).save("test_109.html")
    new HTMLFile(t751.getHtml + h751.getHtml()).save("test_751.html")

    val t650 = new Table(Array("Name", "Abbreviation", "Seats"))
    t650.addEntry(("Union of the Social Democratic and Labour Party" :: "USDLP" :: 317 :: Nil).toArray)
    t650.addEntry(("The Conservative Party" :: "Tory" :: 291 :: Nil).toArray)
    t650.addEntry(("The Alliance of Democrats and Liberals" :: "ADL" :: 19 :: Nil).toArray)
    t650.addEntry(("The People's Independent Party" :: "PIP" :: 23 :: Nil).toArray)
    t650.sort(2)
    t650.sum(2)
    t650.setTableBorder(SolidBorder("black", 1))
    t650.setColumnBorder(SolidBorder("black", 1))
    t650.setWidth(520)
    t650.setCentre(true)

    val h650 = new SemiCircle(650)
    h650.partiesFromTable(t650, 2)
    h650.build()

    h650.setPartyColour("Union of the Social Democratic and Labour Party", "red")
    h650.setPartyColour("The Conservative Party", "blue")
    h650.setPartyColour("The Alliance of Democrats and Liberals", "orange")
    h650.setPartyColour("The People's Independent Party", "magenta")
    h650.setPartyOrder(Array("Union of the Social Democratic and Labour Party", "The People's Independent Party", "The Conservative Party", "The Alliance of Democrats and Liberals"))

    h650.colour()

    new HTMLFile(t650.getHtml + h650.getHtml()).save("test_650.html")

    val o650 = new Opposition(650, true)
    o650.partiesFromTable(t650, 2)
    o650.build()

    o650.setPartyColour("Union of the Social Democratic and Labour Party", "red")
    o650.setPartyColour("The Conservative Party", "blue")
    o650.setPartyColour("The Alliance of Democrats and Liberals", "orange")
    o650.setPartyColour("The People's Independent Party", "magenta")
    o650.setPartyOrder(Array("Union of the Social Democratic and Labour Party", "The People's Independent Party", "The Conservative Party", "The Alliance of Democrats and Liberals"))

    o650.colour()

    new HTMLFile(t650.getHtml + o650.getHtml()).save("test_o_650.html")

  }

}
