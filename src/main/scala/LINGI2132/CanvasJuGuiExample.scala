package LINGI2132

import LINGI2132.JuGuiDSL.Animations._
import LINGI2132.JuGuiDSL.Canvasjugui
import org.scalajs.dom
import dom.html

import scala.collection.mutable.Queue
import scala.scalajs.js.{Array => ArrayJs}
import LINGI2132.JuGuiDSL.ClassExtensions.ClassExtensions._
import LINGI2132.JuGuiDSL.Interactions._
import LINGI2132.JuGuiDSL.Shapes._



object CanvasJuGuiExample {
  def run(canvas: html.Canvas): Unit = {
    val canvasjugui = new Canvasjugui(canvas)

    val line = JGLine(300, 100, 250, 50)
    val arcs = Array.fill(2)(JGArc(450, 75, 20, 0.0, Math.PI/2, true))
    val quadraticCurve = JGQuadraticCurve(600, 50, 550, 30, 550, 100)
    val bezierCurves = Array.fill(2)(JGBezierCurve(100, 100, 150, 37, 20, 100, 50, 25))

    val circles = Array.fill(4)(JGCircle(100, 200, 50))
    val rectangles = Array.fill(3)(JGRectangle(300, 50, 40, 80))

    val radialGradient = JGRadialGradient(45, 495, 10, 52, 500, 30, JGRectangle(0, 450, 150, 150))
    val linearGradient = JGLinearGradient(200, 450, 200 , 600, JGRectangle(200, 450, 150, 150))
    val patterns = JGPatterns("https://mdn.mozillademos.org/files/222/Canvas_createpattern.png", "repeat", JGRectangle(450, 450, 150, 150))
    val text = JGText("Hello World!", 950, 450)
    val image = JGImage("https://mdn.mozillademos.org/files/5397/rhino.jpg", 650, 450)
    val image2 = JGImage("https://mdn.mozillademos.org/files/5397/rhino.jpg", 800, 450)

    val clip = JGRectangle(0, 0, 500, 500)



    (circles ++ bezierCurves).setGlobalCompositeOperation("overlay")
    circles(0).translate(50, 50)

    rectangles(0).translateX(10)
    rectangles(1).translateY(5)
    rectangles(0).setLineDash(ArrayJs(4, 2))

    circles.changeColor("#7CC8C5") // apply on group !
    circles(2).strokeWidth(6)
    circles(3).fill(true)
    circles(3).transparency(0.5)


    rectangles(1).clearArea()
    rectangles(1).setGlobalCompositeOperation("overlay")

    //bezierCurves.fill(true)
    //bezierCurves.clearArea(true)


    arcs(1).fill(true)

    line.strokeWidth(10)
    line.strokeLineCap("round")

    linearGradient.addColorStop(0, "#00ABEB")
    linearGradient.addColorStop(0.5, "#fff")
    linearGradient.addColorStop(0.5, "#26C000")
    linearGradient.addColorStop(1, "#fff")
    linearGradient.fill(true)
    linearGradient.shadowOffsetX(5)
    linearGradient.shadowOffsetY(5)
    linearGradient.shadowBlur(2)

    radialGradient.addColorStop(0, "#A7D30C")
    radialGradient.addColorStop(0.9, "#019F62")
    radialGradient.addColorStop(1, "rgba(1, 159, 98, 0)")
    radialGradient.fill(true)
    radialGradient.setGlobalCompositeOperation("overlay")

    text.changeColor("#3FD72F")
    text.font("48px serif")
    text.textBaseline("hanging")

    image.resize(100, 60)
    image2.slice(33, 71, 104, 124, 800, 450, 87, 104)

    rectangles(0).translate(50, 50)
    rectangles(0).rotate((Math.PI / 180) * 25, 0, 0)
    rectangles(0).scale(2, 2)


    rectangles(2).transform(2, 0, 0, 2, 30, 30)

    clip.clip(true)

    canvasjugui += circles
    canvasjugui += rectangles
    canvasjugui += bezierCurves
    canvasjugui += arcs
    canvasjugui += line
    canvasjugui += linearGradient
    canvasjugui += radialGradient
    canvasjugui += patterns
    canvasjugui += text
    canvasjugui += image
    canvasjugui += image2
    canvasjugui += quadraticCurve
    //canvasjugui += clip

    canvasjugui.draw()


    /*======INTERACTIONS=======*/

    val circles2 = Array.fill(2)(JGCircle(100, 700, 40))
    circles2.changeColor("#51BCD8")
    circles2.fill(true)
    circles2(1).translateX(100)
    canvasjugui.interact(circles2(0), DisappearInteraction(3))
    canvasjugui.interact(circles2(1), DisappearInteraction(-1))

    val rect3 = JGRectangle(350, 650, 50, 60)
    rect3.fill(true)
    canvasjugui.interact(rect3, ChangeColorInteraction(Queue.apply("#FF1313", "#3E792E", "#1647DF")))

    val rect4 = JGRectangle(500, 650, 50, 60)
    rect4.fill(true)
    canvasjugui.interact(rect4, ChangeColorInteraction(Queue.apply("#FF1313", "#3E792E", "#1647DF")))


    /*======ANIMATIONS======*/
    val cir = JGRectangle(450, 900, 30, 30)
    canvasjugui.animate(cir, LineDashAnimation(ArrayJs(4, 2)), new AnimationZone(395,845, 110, 110))

    val rect = JGRectangle(600, 900, 30, 30)
    canvasjugui.animate(rect, RotationAnimation(615, 915), new AnimationZone(550, 850, 200, 200))

    val rect2 = JGRectangle(100, 900, 60, 60)
    rect2.fill(true)
    rect2.changeColor("#1E5766")
    canvasjugui.animate(rect2, ScalingAnimation(0.7, 0.5, 2, 2.5), new AnimationZone(100, 900, 200, 200))

  }
}
