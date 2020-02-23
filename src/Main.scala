import GraphicsBuilder._
import HtmlCompiler._

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
    t179.sort(2)

    val p179 = new PartyList().partiesFromTable(t179, 2)


    val t109 = new Table(Array("Name", "Abbreviation", "Seats"))
    t109.addEntry(("New Republican Party" :: "R" :: 63 :: Nil).toArray)
    t109.addEntry(("Democratic Party" :: "D" :: 46 :: Nil).toArray)
    t109.sum(2)
    t109.setTableBorder(SolidBorder("black", 1))
    t109.setColumnBorder(SolidBorder("black", 1))
    t109.setWidth(520)
    t109.setCentre(true)
    t109.sort(2)

    val p109 = new PartyList().partiesFromTable(t109, 2)

    val h109 = new SemiCircle(109)
    h109.build()
    h109.addOpacityAnimation(0.1, 1)

    p109.setPartyColour("New Republican Party", "red")
    p109.setPartyColour("Democratic Party", "blue")
    h109.setParties(p109)

    h109.colour()
    h109.setCentre(true)

    val h179 = new SemiCircle(179)
    h179.build()

    p179.setPartyColour("Social Democrats for a United Country", "red")
    p179.setPartyColour("National Conservatives", "lightblue")
    p179.setPartyColour("Union of Liberals and Conservatives", "yellow")
    p179.setPartyColour("Green Democrats", "green")
    p179.setPartyColour("The People's Socialist Alliance", "darkred")
    h179.setParties(p179)

    h179.colour()
    h179.addOpacityAnimation(0.1, 1.5)
    h179.setCentre(true)

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
    t751.setColumnBorder(SolidBorder("black", 1, LeftTextAlignment), 0)
    t751.setWidth(520)
    t751.setCentre(true)

    val h751 = new SemiCircle(751)
    h751.build()

    val p751 = new PartyList().partiesFromTable(t751, 2)
    p751.setPartyColour("Greens", "green")
    p751.setPartyColour("The Christian Conservative Party", "black")
    p751.setPartyColour("Union of Socialists and Social Democrats", "red")
    p751.setPartyColour("National Conservative and Protectionist Party", "blue")
    p751.setPartyColour("Alliance for Freedom and Liberty", "orange")
    p751.setPartyColour("Moderate Democrats and Centrists", "yellow")
    p751.setPartyColour("Unitary Left", "darkred")
    p751.setPartyOrder(
      Array(
        "Unitary Left", "Union of Socialists and Social Democrats", "Greens",
        "Moderate Democrats and Centrists", "Alliance for Freedom and Liberty",
        "The Christian Conservative Party", "National Conservative and Protectionist Party"
      )
    )

    h751.setParties(p751)
    h751.colour()
    h751.addOpacityAnimation(0.1, 1)
    h751.setCentre(true)

    new HTMLFile(t179.getHtml + h179.getHtml).save("test_179.html")
    new HTMLFile(t109.getHtml + h109.getHtml).save("test_109.html")
    new HTMLFile(t751.getHtml + h751.getHtml).save("test_751.html")

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

    val p650 = new PartyList().partiesFromTable(t650, 2)
    p650.setPartyColour("Union of the Social Democratic and Labour Party", "red")
    p650.setPartyColour("The Conservative Party", "blue")
    p650.setPartyColour("The Alliance of Democrats and Liberals", "orange")
    p650.setPartyColour("The People's Independent Party", "magenta")
    p650.setPartyOrder(Array("Union of the Social Democratic and Labour Party", "The People's Independent Party", "The Conservative Party", "The Alliance of Democrats and Liberals"))


    val h650 = new SemiCircle(650)
    h650.build()

    h650.setParties(p650)
    h650.colour()
    h650.addOpacityAnimation(0.1, 1.5)
    h650.setCentre(true)

    new HTMLFile(t650.getHtml + h650.getHtml).save("test_650.html")

    val o650 = new Opposition(650, true)
    o650.build()
    o650.setParties(p650)
    o650.colour()
    o650.addOpacityAnimation(0.1, 1.5)
    o650.setCentre(true)

    new HTMLFile(t650.getHtml + o650.getHtml).save("test_o_650.html")

    val t435 = new Table(Array("Name", "Abbreviation", "Seats"))
    t435.addEntry(("Democratic Socialists" :: "S" :: 16 :: Nil).toArray)
    t435.addEntry(("Democrat" :: "D" :: 73 :: Nil).toArray)
    t435.addEntry(("Moderate" :: "M" :: 198 :: Nil).toArray)
    t435.addEntry(("Republican" :: "R" :: 101 :: Nil).toArray)
    t435.addEntry(("National Republicans" :: "N" :: 47 :: Nil).toArray)
    t435.sort(2)
    t435.sum(2)
    t435.setTableBorder(SolidBorder("black", 1))
    t435.setColumnBorder(SolidBorder("black", 1))
    t435.setWidth(520)
    t435.setCentre(true)

    val p435 = new PartyList().partiesFromTable(t435, 2)
    p435.setPartyColour("Democratic Socialists", "darkred")
    p435.setPartyColour("Democrat", "blue")
    p435.setPartyColour("Moderate", "yellow")
    p435.setPartyColour("Republican", "red")
    p435.setPartyColour("National Republicans", "magenta")
    p435.setPartyOrder(Array("Moderate", "Democrat", "Republican", "National Republicans", "Democratic Socialists"))

    val b435 = new Bar(435)
    b435.setParties(p435)
    b435.colour()

    b435.setShowSeats(true)
    b435.setCentre(true)
    b435.setShowMajority(true)
    b435.addOpacityAnimation(1.5)

    val h435 = new SemiCircle(435)
    h435.setParties(p435)
    h435.build()

    h435.colour()
    h435.addOpacityAnimation(0.1, 1.5)
    h435.setCentre(true)

    new HTMLFile(t435.getHtml + b435.getHtml + h435.getHtml).save("test_expanded_us_435.html")

    // 870, 54
    val trs = new Table(Array("Name", "Abbreviation", "House of Representatives", "House of Senators"))
    trs.addEntry(("Liberal Democrats" :: "LibDem" :: 77 :: 5 :: Nil).toArray)
    trs.addEntry(("Conservative People's Party" :: "CPP" :: 206 :: 18 :: Nil).toArray)
    trs.addEntry(("National Liberation Front" :: "NLF" :: 161 :: 7 :: Nil).toArray)
    trs.addEntry(("Moderate Centrist Voting Group" :: "ModCen" :: 92 :: 6 :: Nil).toArray)
    trs.addEntry(("The People's Socialist Party" :: "PSP" :: 248 :: 15 :: Nil).toArray)
    trs.addEntry(("Party of Democrats and Socialists" :: "DemSoc" :: 50 :: 3 :: Nil).toArray)
    trs.addEntry(("Revolutionary Alliance of Communists" :: "RAC" :: 36 :: 0 :: Nil).toArray)
    trs.sort(2)
    trs.sum(2)
    trs.sum(3)
    trs.setTableBorder(SolidBorder("black", 1))
    trs.setColumnBorder(SolidBorder("black", 1))
    trs.setWidth(800)
    trs.setCentre(true)

    val prs_r = new PartyList().partiesFromTable(trs, 2)
    prs_r.setPartyColour("Liberal Democrats", "yellow")
    prs_r.setPartyColour("Conservative People's Party", "darkgreen")
    prs_r.setPartyColour("National Liberation Front", "blue")
    prs_r.setPartyColour("Moderate Centrist Voting Group", "orange")
    prs_r.setPartyColour("The People's Socialist Party", "red")
    prs_r.setPartyColour("Party of Democrats and Socialists", "magenta")
    prs_r.setPartyColour("Revolutionary Alliance of Communists", "darkred")
    prs_r.setPartyOrder(
      Array(
        "National Liberation Front", "Conservative People's Party", "Liberal Democrats",
        "Moderate Centrist Voting Group", "The People's Socialist Party", "Party of Democrats and Socialists",
        "Revolutionary Alliance of Communists"
      )
    )
    val prs_s = prs_r.update(trs, 3)
    prs_s.setPartyOrder(
      Array(
        "National Liberation Front", "Conservative People's Party", "Liberal Democrats",
        "Moderate Centrist Voting Group", "The People's Socialist Party", "Party of Democrats and Socialists",
        "Revolutionary Alliance of Communists"
      )
    )

    val brs_r = new Bar(870)
    brs_r.setParties(prs_r)
    brs_r.colour()
    brs_r.setShowSeats(true)
    brs_r.setCentre(true)
    brs_r.setShowMajority(true)
    brs_r.addOpacityAnimation(1.5)

    val brs_s = new Bar(54)
    brs_s.setParties(prs_s)
    brs_s.colour()
    brs_s.setShowSeats(true)
    brs_s.setCentre(true)
    brs_s.setShowMajority(true)
    brs_s.addOpacityAnimation(1.5)

    val hbr_r = new SemiCircle(870)
    hbr_r.setParties(prs_r)
    hbr_r.build()
    hbr_r.colour()
    hbr_r.addOpacityAnimation(0.1, 1.5)
    hbr_r.setCentre(true)

    val hbr_s = new SemiCircle(54)
    hbr_s.setParties(prs_s)
    hbr_s.build()
    hbr_s.colour()
    hbr_s.addOpacityAnimation(0.1, 1.5)
    hbr_s.setCentre(true)

    new HTMLFile(trs.getHtml + brs_r.getHtml + brs_s.getHtml + hbr_r.getHtml + hbr_s.getHtml).save("test_rep_sen_870_54.html")

  }

}
