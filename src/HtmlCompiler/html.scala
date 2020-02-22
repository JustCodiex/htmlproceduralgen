package HtmlCompiler

import java.io._

class HTMLFile(content: String) {

  def save(outpath: String): Unit = {

    val writer = new PrintWriter(new File(outpath ))

    WriteLn(writer, "<html>");
    WriteLn(writer, content);
    WriteLn(writer, "</html>");

    writer.close()

  }

  private def WriteLn(stream: PrintWriter, content : String)={
    stream.write(content + "\n");
  }

}
