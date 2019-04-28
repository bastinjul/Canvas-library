package LINGI2132

import org.scalajs.dom
import org.scalajs.dom.ext.Color
import org.scalajs.dom.html

class Canvasy(canvas: html.Canvas) {
  val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  val w = 300
  canvas.width = w
  canvas.height = w

  //def += (shape: Array[Rectangle]) : Unit = shape.map(s => s.draw(ctx))
  //def += (shape: Array[Circle]) : Unit = shape.map(s => s.draw(ctx))
  def += (shape: Any) : Unit =
    shape match {
      case r : Array[Rectangle] => r.map(s => s.draw(ctx))
      case c : Array[Circle] => c.map(s => s.draw(ctx))
    }
}

case class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int) extends Shape {

  override def draw(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.strokeRect(x, y, width, height)
  }

}

case class Circle(var radius: Double, var x: Int, var y: Int) extends Shape {

  override def draw(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.arc(x, y, radius, 0.0, Math.PI * 2, true)
    ctx.stroke()
  }
}

trait Shape {
  def draw(ctx: dom.CanvasRenderingContext2D) : Unit

  object stroke {
    var width : Double = 0
    var radius : Double = 0
    var color : String = "#000000"
  }

}

trait CanvasyElement {

}

object Col {
  implicit class RGBHelper(val sc: StringContext) extends AnyVal {
    def rgb(args: Any*): String = {
      val strings = sc.parts.iterator
      val expressions = args.iterator
      val buf = new StringBuffer(strings.next)
      while(strings.hasNext) {
        buf append expressions.next
        buf append strings.next
      }
      buf.toString
    }
  }
}