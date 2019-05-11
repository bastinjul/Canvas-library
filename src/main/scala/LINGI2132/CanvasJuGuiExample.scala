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


    val circles = Array.fill(4)(JGCircle(50, 50, 50))
    val rectangles = Array.fill(3)(JGRectangle(20, 20, 20, 20))
    val bezierCurves = Array.fill(2)(JGBezierCurve(75, 37, 70, 25, 50, 25))
    val arcs = Array.fill(2)(JGArc(60, 60, 20, 0.0, Math.PI/2, true))
    val line = JGLine(0, 0, 35, 35)



    val linearGradient = JGLinearGradient(100, 100, 100 , 250, JGRectangle(100, 100, 150, 150))
    val radialGradient = JGRadialGradient(45, 45, 10, 52, 50, 30, JGRectangle(0, 0, 150, 150))
    val patterns = JGPatterns("https://mdn.mozillademos.org/files/222/Canvas_createpattern.png", "repeat", JGRectangle(250, 450, 150, 150))
    val text = JGText("Hello World!", 850, 450)
    val image = JGImage("https://mdn.mozillademos.org/files/5397/rhino.jpg", 450, 450)
    val image2 = JGImage("https://mdn.mozillademos.org/files/5397/rhino.jpg", 450, 500)

    val clip = JGRectangle(0, 0, 500, 500)






    (circles ++ bezierCurves).setGlobalCompositeOperation("overlay")
    circles(0).translate(20, 20)
    rectangles(0).translateX(10)
    rectangles(1).translateY(5)
    rectangles(0).setLineDash(ArrayJs(4, 2))

    circles.changeColor("#7CC8C5")

    circles(2).strokeWidth(6)

    circles(3).fill(true)
    circles(3).transparency(0.5)
    rectangles(1).clearArea()
    rectangles(1).setGlobalCompositeOperation("overlay")

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
    image2.slice(33, 71, 104, 124, 500, 200, 87, 104)

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
    //canvasjugui += clip

    canvasjugui.draw()


    /*======INTERACTIONS=======*/

    val cir2 = JGCircle(100, 700, 40)
    cir2.fill(true)
    cir2.changeColor("#51BCD8")
    canvasjugui.interact(cir2, DisappearInteraction(3))

    val cir3 = JGCircle(200, 700, 40)
    cir3.fill(true)
    cir3.changeColor("#51BCD8")
    canvasjugui.interact(cir3, DisappearInteraction(-1))

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
