package LINGI2132.KickStartDSL

import org.scalajs.dom
import org.scalajs.dom.html

import scala.collection.mutable.ArrayBuffer

class Canvasy(canvas: html.Canvas) {

  val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  val w = 300
  canvas.width = w
  canvas.height = w

  var shapes = new ArrayBuffer[Shape]()

  /**
    * Add shapes to arraybuffer
    * @param shape: A shape that extends JGShape
    */
  def += (shape: Any) : Unit =
    shape match {
      case r : Array[Rectangle] => {
        r.foreach(s => shapes.append(s))
      }
      case c : Array[Circle] => {
        c.foreach(s => shapes.append(s))
      }
    }

  /**
    * draw each shape contained in the buffer
    */
  def draw() : Unit = {
    shapes.foreach(sh => sh.draw(ctx))
  }

  /**
    * returns the instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
  def get_ctx() : dom.CanvasRenderingContext2D = ctx
}

case class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int) extends Shape {

  override def size(d: Double): Unit = {}

  override def draw(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.strokeStyle = this.stroke.color
    ctx.lineWidth = this.stroke.width
    ctx.strokeRect(x, y, width, height)
  }

  override def translateX(i: Int): Unit = {
    this.x += i
  }

  override def translateY(i: Int): Unit = {
    this.y += i
  }

}

case class Circle(var radius: Double, var x: Int, var y: Int) extends Shape {

  override def draw(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.strokeStyle = this.stroke.color
    ctx.lineWidth = this.stroke.width
    ctx.beginPath()
    ctx.arc(x, y, radius, 0.0, Math.PI * 2, true)
    ctx.stroke()
  }

  override def size(d: Double) : Unit = this.radius = d/2

  override def translateY(i: Int): Unit = {
    this.y += i
  }

  override def translateX(i: Int): Unit = {
    this.x += i
  }
}

trait Shape extends CanvasyElement {
  /**
    * draws the shape
    * @param ctx the instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
  def draw(ctx: dom.CanvasRenderingContext2D) : Unit

  object stroke {
    var width : Double = 1
    var color : String = "#000000"
  }

  /**
    * moves the shape vertically according to
    * @param i
    */
  def translateY(i: Int) : Unit

  /**
    * moves the shape horizontally according to
    * @param i
    */
  def translateX(i: Int) : Unit

  /**
    * resizes the shape according to
    * @param d
    */
  def size(d: Double) : Unit

}

trait CanvasyElement{

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

  object Color {
    def red : String = "#FF0000"
  }
}

object Extends {
  implicit class shapeExtend[ApplyOn <: Shape](s: ApplyOn){
    def translateY(i: Int): ApplyOn = {
      s.translateY(i)
      s
    }

    def translateX(i: Int): ApplyOn = {
      s.translateX(i)
      s
    }

    def and(shape: ApplyOn): Array[Shape] = {
      val a = new Array[Shape](2)
      a(0) = s
      a(1) = shape
      a
    }

    def and(a: Any): ApplyOn = {
      Changes.change(a, s)
    }

    def change(a: Any) : ApplyOn = {
      Changes.change(a, s)
    }

    def stroke(c: String) : ApplyOn = {
      s.stroke.color = c
      s
    }

    def stroke(i: Double) : ApplyOn = {
      s.stroke.width = i
      s
    }
  }

  implicit class shapeArrayExtend[ApplyOn <: Shape](s: Array[ApplyOn]){
    def translateY(i: Int) : Array[ApplyOn] = {
      s.foreach(sh => sh.translateY(i))
      s
    }

    def translateX(i: Int) : Array[ApplyOn] = {
      s.foreach(sh => sh.translateX(i))
      s
    }

    def change(a: Any): Array[ApplyOn] = {
      s.foreach(sh => Changes.change(a, sh))
      s
    }

    def and(a: Any): Array[ApplyOn] = {
      s.foreach(sh => Changes.change(a, sh))
      s
    }

    def stroke(c: String) : Array[ApplyOn] = {
      s.foreach(sh => sh.stroke.color = c)
      s
    }
  }

}

/**
  * trait for shape modifier
  * @tparam ApplyOn only apply on objects of type CanvasyElement
  */
trait CanvasyElementModifier[ApplyOn <: CanvasyElement] {
  /**
    * modify the shape x
    * @param x the shape to apply on
    */
  def change(x: ApplyOn): Unit
}

// Let us create an example
case class StrokeWidth(w: Double) extends CanvasyElementModifier[Shape] {
  // every Shape has a stroke.
  override def change(x: Shape): Unit = {
    x.stroke.width = w
  }
}

case class Radius(i: Int) extends CanvasyElementModifier[Shape] {
  override def change(x: Shape): Unit = {
    x match {
      case c : Circle => c.radius = i
    }
  }
}

case class Width(i: Int) extends CanvasyElementModifier[Shape] {
  override def change(x: Shape): Unit = {
    x match {
      case r : Rectangle => r.width = i
    }
  }
}

case class StrokeColor(c: String) extends CanvasyElementModifier[Shape] {
  override def change(x: Shape): Unit = x.stroke.color = c
}

object Changes {
  def change[ApplyOn <: Shape](a: Any, s: ApplyOn) : ApplyOn = {
    a match {
      case strokeWidth : StrokeWidth => {
        strokeWidth.change(s)
        s
      }
      case radius: Radius => {
        radius.change(s)
        s
      }
      case width: Width => {
        width.change(s)
        s
      }
      case strokeColor: StrokeColor => {
        strokeColor.change(s)
        s
      }
    }
  }
}