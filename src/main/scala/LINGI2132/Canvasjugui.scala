package LINGI2132

import org.scalajs.dom
import org.scalajs.dom.html

import scala.collection.mutable.ArrayBuffer

class Canvasjugui(canvas: html.Canvas) {

  var shapes = new ArrayBuffer[JGShape]

  def += (shape: Any): Unit = {
    shape match{
      case a: Array[JGShape] => a.foreach(s => shapes.append(s))
      case sh : JGShape => shapes.append(sh)
    }
  }

  def draw() : Unit = {
    shapes.foreach(s => s.draw())
  }

}

trait JGShape {

  object parameters {
    var color : String = "#000000"
    var strokeWidth : Double = 1.0
  }

  def draw(ctx: dom.CanvasRenderingContext2D, fill: Boolean) : Unit

}

case class JGRectangle(x: Int, y: Int, width: Double, height: Double) extends JGShape {

  override def draw(ctx: dom.CanvasRenderingContext2D, fill: Boolean): Unit = {
    if(fill) {
      ctx.fillStyle = this.parameters.color
      ctx.fillRect(x, y, width, height)
    }
    else {
      ctx.strokeStyle = this.parameters.color
      ctx.lineWidth = this.parameters.strokeWidth
      ctx.strokeRect(x, y, width, height)
    }
  }
}

case class JGCircle(x: Int, y: Int, radius: Double) extends JGShape {

  override def draw(ctx: dom.CanvasRenderingContext2D, fill: Boolean): Unit = {
    if(fill) {
      ctx.fillStyle = this.parameters.color
      ctx.beginPath()
      ctx.arc(x, y, radius, 0.0, Math.PI * 2, true)
      ctx.fill()

    }
    else {
      ctx.strokeStyle = this.parameters.color
      ctx.lineWidth = this.parameters.strokeWidth
      ctx.beginPath()
      ctx.arc(x, y, radius, 0.0, Math.PI * 2, true)
      ctx.stroke()
    }

  }
}

case class JGQuadraticCurve(cp1x: Int, cp1y: Int, x: Int, y: Int) extends JGShape {

  override def draw(ctx: dom.CanvasRenderingContext2D, fill: Boolean): Unit = {
    if(fill){
      ctx.fillStyle = this.parameters.color
      ctx.beginPath()
      ctx.quadraticCurveTo(cp1x, cp1y, x, y)
      ctx.fill()
    }
    else {
      ctx.strokeStyle = this.parameters.color
      ctx.lineWidth = this.parameters.strokeWidth
      ctx.quadraticCurveTo(cp1x, cp1y, x, y)
      ctx.stroke()
    }

  }
}

case class JGBezierCurve(cp1x: Int, cp1y: Int, cp2x: Int, cp2y: Int, x: Int, y: Int) extends JGShape {

  override def draw(ctx: dom.CanvasRenderingContext2D, fill: Boolean): Unit = {
    if(fill){
      ctx.fillStyle = this.parameters.color
      ctx.beginPath()
      ctx.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
      ctx.fill()
    }
    else {
      ctx.strokeStyle = this.parameters.color
      ctx.lineWidth = this.parameters.strokeWidth
      ctx.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
      ctx.stroke()
    }
  }
}