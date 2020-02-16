import Project._
import java.math._

object Main {

  def main(args : Array[String]): Unit = {

    var t179 = new Table(Array("Name", "Abbreviation", "Seats"));
    t179.addEntry(("Social Democrats for a United Country" :: "SDUC" :: 56 :: Nil).toArray);
    t179.addEntry(("National Conservatives" :: "NC" :: 49 :: Nil).toArray);
    t179.addEntry(("Union of Liberals and Conservatives" :: "ULC" :: 37 :: Nil).toArray);
    t179.addEntry(("Green Democrats" :: "GD" :: 24 :: Nil).toArray);
    t179.addEntry(("The People's Socialist Alliance" :: "PSA" :: 13 :: Nil).toArray);

    var t109 = new Table(Array("Name", "Abbreviation", "Seats"));
    t109.addEntry(("New Republican Party" :: "R" :: 63 :: Nil).toArray);
    t109.addEntry(("Democratic Party" :: "D" :: 46 :: Nil).toArray);

    var h179 = new HorseShoe(179);
    h179.partiesFromTable(t179, 2);
    h179.build()

    h179.setPartyColour("Social Democrats for a United Country", "red");
    h179.setPartyColour("National Conservatives", "lightblue");
    h179.setPartyColour("Union of Liberals and Conservatives", "yellow");
    h179.setPartyColour("Green Democrats", "green");
    h179.setPartyColour("The People's Socialist Alliance", "darkred");

    h179.colour();

    var h109 = new HorseShoe(109);
    h109.partiesFromTable(t109, 2);
    h109.build()

    h109.setPartyColour("New Republican Party", "red");
    h109.setPartyColour("Democratic Party", "blue");

    h109.colour()

    var t751 = new Table(Array("Name", "Abbreviation", "Seats"));
    t751.addEntry(("Greens" :: "G" :: 23 :: Nil).toArray);
    t751.addEntry(("Liberal Reform Party" :: "LRP" :: 59 :: Nil).toArray);
    t751.addEntry(("The Christian Conservative Party" :: "CC" :: 117 :: Nil).toArray);
    t751.addEntry(("People's Union of Communists and Socialist Party" :: "PUCSP" :: 9 :: Nil).toArray);
    t751.addEntry(("Union of Socialists and Social Democrats" :: "USSD" :: 98 :: Nil).toArray);
    t751.addEntry(("National Conservative and Protectionist Party" :: "NCPP" :: 103 :: Nil).toArray);
    t751.addEntry(("Democratic Union of Conservatives and Liberals" :: "DUCL" :: 17 :: Nil).toArray);
    t751.addEntry(("Alliance for Freedom and Liberty" :: "AFL" :: 31 :: Nil).toArray);
    t751.addEntry(("Moderate Democrats and Centrists" :: "MDC" :: 104 :: Nil).toArray);
    t751.addEntry(("Liberal and Social Union of People's" :: "LSUP" :: 34 :: Nil).toArray);
    t751.addEntry(("Unitary Left" :: "UL" :: 75 :: Nil).toArray);
    t751.addEntry(("New Right's Final Alliance" :: "NRFA" :: 81 :: Nil).toArray);

    var h751 = new HorseShoe(751);
    h751.partiesFromTable(t751, 2);
    h751.build()

    h751.setPartyColour("Greens", "green");
    h751.setPartyColour("Liberal Reform Party", "blue");
    h751.setPartyColour("The Christian Conservative Party", "lightblue");
    h751.setPartyColour("People's Union of Communists and Socialist Party", "darkred");
    h751.setPartyColour("Union of Socialists and Social Democrats", "red");
    h751.setPartyColour("National Conservative and Protectionist Party", "black");
    h751.setPartyColour("Democratic Union of Conservatives and Liberals", "magenta");
    h751.setPartyColour("Alliance for Freedom and Liberty", "darkgreen");
    h751.setPartyColour("Moderate Democrats and Centrists", "yellow");
    h751.setPartyColour("Liberal and Social Union of People's", "orange");
    h751.setPartyColour("Unitary Left", "pink");
    h751.setPartyColour("New Right's Final Alliance", "grey");

    h751.colour();

    new HTMLFile(t179.getHtml() + h179.getHtml()).save("test_179.html")
    new HTMLFile(t109.getHtml() + h109.getHtml()).save("test_109.html")
    new HTMLFile(t751.getHtml() + h751.getHtml()).save("test_751.html")

  }

}
