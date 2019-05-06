package LINGI2132



import org.scalajs.dom
import dom.{document, html}
import Col._
import Extends._

object WebApp {
  def main(args: Array[String]): Unit = {
    val canvas = document.createElement("canvas").asInstanceOf[html.Canvas]
    document.body.appendChild(canvas)

    val w = 2000
    canvas.width = w
    canvas.height = w

    //scalaJSDemo(canvas)
    //dslDemo(canvas)
    CanvasjuguiExample.run(canvas)
  }

  def scalaJSDemo(c: html.Canvas) = {
    val ctx = c.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val w = 300
    c.width = w
    c.height = w

    ctx.strokeStyle = "red"
    ctx.lineWidth = 3
    ctx.beginPath()
    ctx.moveTo(w/3, 0)
    ctx.lineTo(w/3, w/3)
    ctx.moveTo(w*2/3, 0)
    ctx.lineTo(w*2/3, w/3)
    ctx.moveTo(w, w/2)
    ctx.arc(w/2, w/2, w/2, 0, 3.14)

    ctx.stroke()
  }

  def dslDemo(canvas: html.Canvas) = {
    // From now on, we use the DSL
    val canvasy = new Canvasy(canvas) //isn't canvasy a nice name for a library?

    // Let us create some shapes
    val circles = Array.fill(4)(new Circle(50.0, 0, 0))
    val rectangles = Array.tabulate(2)(i => new Rectangle(i*50, i*50, 50, 100))

    // Tell the library to display both circles and rectangles in the canvas
    canvasy += circles
    canvasy += rectangles

    // the first way to edit elements is by modifying their properties directly
    circles(0) stroke rgb"#ee22aa"
    circles(0) stroke 12
    circles(1) translateY 50

    // array/list/iterables... of elements form Groups. Groups have a position, and can be translated.
    // this translates all the elements inside the group.
    circles translateX 100 translateY 100

    // easily create a group with the keyword "and"
    circles(2) and circles(3) translateX 50

    circles(2) translateX 22

    // of course, groups have foreach/map/flatmap clin d’œil
    (circles(0) and circles(1)).foreach(_.size(30))

    // to modify multiple elements at the same time on their inner properties, we use the "change" notation:
    circles change Radius(10)
    circles(0) change Radius(20) and StrokeColor(Color.red)

    // this should not compile, as a circle has no width:
    //circles change Width(10)

    // however, this should
    rectangles change Width(90)

    // Width, Radius,  StrokeColor and their friend are **not** functions. They are case classes, in order
    // to allow the end user of the library to create new modifiers. All these modifiers inherits from the following
    // trait:
    //trait CanvasyElementModifier[ApplyOn <: CanvasyElement] {
    //  def change(x: ApplyOn): Unit
    //}

    rectangles change StrokeWidth(3) and StrokeColor(rgb"#aa00e1")
    circles change StrokeWidth(2)

    //Another thing that should work is
    (rectangles ++ circles) change StrokeColor(Color.red)

    //But of course, this should not (circles have no width, but rather have a radius)
    //(rectangles ++ circles) change Width(10)

    // let us draw all these things
    canvasy draw()
  }
}