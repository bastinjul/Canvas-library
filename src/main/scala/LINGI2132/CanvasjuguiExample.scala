package LINGI2132

import org.scalajs.dom
import dom.{document, html}
import Extensions._
import org.scalajs.dom.raw.HTMLImageElement

object CanvasjuguiExample {
  def run(canvas: html.Canvas): Unit = {
    val canvasjugui = new Canvasjugui(canvas)

    val circles = Array.fill(4)(JGCircle(50, 50, 50))
    val rectangles = Array.fill(2)(JGRectangle(20, 20, 20, 20))
    val bezierCurves = Array.fill(2)(JGBezierCurve(75, 37, 70, 25, 50, 25))
    val arcs = Array.fill(2)(JGArc(60, 60, 20, 0.0, Math.PI/2, true))
    val line = JGLine(0, 0, 35, 35)
    val linearGradient = JGLinearGradient(100, 100, 100 , 250, JGRectangle(100, 100, 150, 150))
    val radialGradient = JGRadialGradient(45, 45, 10, 52, 50, 30, JGRectangle(0, 0, 150, 150))
    val patterns = JGPatterns("https://mdn.mozillademos.org/files/222/Canvas_createpattern.png", "repeat", JGRectangle(0, 0, 150, 150))


    circles(0).translate(20, 20)
    rectangles(0).translateX(10)
    rectangles(1).translateY(5)
    rectangles(0).setLineDash(scala.scalajs.js.Array(4, 2))

    circles.changeColor("#7CC8C5")

    circles(2).strokeWidth(6)

    circles(3).fill(true)
    circles(3).transparency(0.5)

    rectangles(1).clearArea(true)

    bezierCurves.fill(true)
    bezierCurves.clearArea(true)

    arcs(1).fill(true)

    line.strokeWidth(10)
    line.strokeLineCap("round")

    linearGradient.addColorStop(0, "#00ABEB")
    linearGradient.addColorStop(0.5, "#fff")
    linearGradient.addColorStop(0.5, "#26C000")
    linearGradient.addColorStop(1, "#fff")
    linearGradient.fill(true)

    radialGradient.addColorStop(0, "#A7D30C")
    radialGradient.addColorStop(0.9, "#019F62")
    radialGradient.addColorStop(1, "rgba(1, 159, 98, 0)")
    radialGradient.fill(true)

    canvasjugui += circles
    canvasjugui += rectangles
    canvasjugui += bezierCurves
    canvasjugui += arcs
    canvasjugui += line
    canvasjugui += linearGradient
    canvasjugui += radialGradient
    canvasjugui += patterns

    canvasjugui.draw()

  }



}
