package LINGI2132

import org.scalajs.dom
import org.scalajs.dom.html

class Canvasy(canvas: html.Canvas) {
  val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  val w = 300
  canvas.width = w
  canvas.height = w

  def += (shape: Array[Rectangle]) : Unit = shape.map(s => s.draw(ctx))
  def += (shape: Array[Circle]) : Unit = shape.map(s => s.draw(ctx))
}

class Rectangle(var x: Int, var y: Int, var width: Int, var height: Int) {

  def translateX(v: Int) : Unit = x = v
  def translateY(v: Int) : Unit = y = v
  def Width(v: Int) : Rectangle = {
    width = v
    this
  }
  def Height(v: Int) : Unit = height = v

  def draw(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.strokeRect(x, y, width, height)
  }

}

class Circle(var radius: Double, var x: Int, var y: Int) {

  def Radius(v: Double): Unit = radius = v

  def draw(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.arc(x, y, radius, 0.0, Math.PI * 2, true)
    ctx.stroke();
  }
}

class Shape