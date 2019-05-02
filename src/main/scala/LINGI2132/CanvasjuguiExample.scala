package LINGI2132

import org.scalajs.dom
import dom.{document, html}

object CanvasjuguiExample {
  def run(canvas: html.Canvas): Unit = {
    val canvasjugui = new Canvasjugui(canvas)

    val circles = Array.fill(4)(new JGCircle(0, 0, 50))

    canvasjugui += circles

  }



}
