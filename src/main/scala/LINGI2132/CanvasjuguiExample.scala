package LINGI2132

import org.scalajs.dom
import dom.{document, html}
import Extensions._

object CanvasjuguiExample {
  def run(canvas: html.Canvas): Unit = {
    val canvasjugui = new Canvasjugui(canvas)

    val circles = Array.fill(4)(JGCircle(50, 50, 50))
    val rectangles = Array.fill(2)(JGRectangle(20, 20, 20, 20))
    val bezierCurves = Array.fill(2)(JGBezierCurve(75, 37, 70, 25, 50, 25))
    val arcs = Array.fill(2)(JGArc(60, 60, 20, 0.0, Math.PI/2, true))
    val line = JGLine(0, 0, 35, 35)

    circles(0).translate(20, 20)
    rectangles(0).translateX(10)
    rectangles(1).translateY(5)

    circles.changeColor("#7CC8C5")

    circles(2).strokeWidth(6)

    circles(3).fill(true)
    circles(3).transparency(0.5)

    rectangles(1).clearArea(true)

    bezierCurves.fill(true)
    bezierCurves.clearArea(true)

    arcs(1).fill(true)

    canvasjugui += circles
    canvasjugui += rectangles
    canvasjugui += bezierCurves
    canvasjugui += arcs
    canvasjugui += line

    canvasjugui.draw()

  }



}
