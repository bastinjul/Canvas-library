package LINGI2132

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, html}

import scala.collection.mutable.ArrayBuffer

class Canvasjugui(canvas: html.Canvas) {

  val ctx : dom.CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  val w = 300
  canvas.width = w
  canvas.height = w

  var shapes = new ArrayBuffer[JGShape]

  def += (shape: Any): Unit = {
    shape match{
      case a: Array[JGShape] => a.foreach(s => shapes.append(s))
      case sh : JGShape => shapes.append(sh)
    }
  }

  def draw() : Unit = {
    shapes.foreach(s => s.draw(ctx))
  }

}

abstract class JGShape(var x: Double, var y: Double) {

  object parameters {
    var color : String = "#000000"
    var strokeWidth : Double = 1.0
    var transparency: Double = 1.0
    var fill : Boolean = false
    var lineDashOffset : Double = 0
    var lineDash: scala.scalajs.js.Array[Double] = scala.scalajs.js.Array.apply(0, 0)
  }

  def draw(ctx: dom.CanvasRenderingContext2D) : Unit = {
    ctx.globalAlpha = this.parameters.transparency
    if(this.parameters.fill) {
      ctx.fillStyle = this.parameters.color
    }
    else {
      ctx.setLineDash(this.parameters.lineDash)
      ctx.lineDashOffset = this.parameters.lineDashOffset
      ctx.strokeStyle = this.parameters.color
      ctx.lineWidth = this.parameters.strokeWidth
    }
    drawShape(ctx)
  }

  def drawShape(ctx: dom.CanvasRenderingContext2D) : Unit

  def translateX(i: Double) : Unit = {
    this.x += i
  }

  def translateY(i: Double) : Unit = {
    this.y += i
  }

  def translate(x: Double, y: Double) : Unit = {
    this.x += x
    this.y += y
  }

  def changeColor(c: String) : Unit = {
    this.parameters.color = c
  }

  def fill(b: Boolean) : Unit = {
    this.parameters.fill = b
  }

  def strokeWidth(w: Double) : Unit = {
    this.parameters.strokeWidth = w
  }

  def clearArea(b: Boolean) : Unit = {
    this.parameters.fill = true
    this.parameters.color = "#FFFFFF"
  }

  def transparency(t: Double) : Unit = {
    this.parameters.transparency = t
  }

  def setLineDash(value: scala.scalajs.js.Array[Double]) : Unit = {
    this.parameters.lineDash = value
  }

  def lineDashOffset(value: Double) : Unit = {
    this.parameters.lineDashOffset = value
  }

}

case class JGRectangle(var xr: Double, var yr: Double, var width: Double, var height: Double) extends JGShape(x = xr, y = yr) {

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    if(this.parameters.fill) {
      ctx.fillRect(x, y, width, height)
    }
    else {
      ctx.strokeRect(x, y, width, height)
    }
  }

}

case class JGLine(var x1: Double, var y1: Double, var x2: Double, var y2: Double) extends JGShape(x = x1, y = y1) with LineParameters {
  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    setParameters(ctx)
    ctx.moveTo(x1, y1)
    ctx.lineTo(x2, y2)
    ctx.stroke()
  }
}

case class JGLinearGradient(var x1: Double, var y1: Double, var x2: Double, var y2: Double, rect: JGRectangle) extends JGShape(x = rect.x, y = rect.y) with ColorStop {

  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    val linearGradient = ctx.createLinearGradient(x1, y1, x2, y2)
    this.colors.colorStop.foreach(c => linearGradient.addColorStop(c._1, c._2))
    if(this.parameters.fill){
      ctx.fillStyle = linearGradient
      ctx.fillRect(rect.x, rect.y, rect.width, rect.height)
    }
    else {
      ctx.strokeStyle = linearGradient
      ctx.strokeRect(rect.x, rect.y, rect.width, rect.height)
    }
  }
}

case class JGRadialGradient(var x1: Double, var y1: Double, var r1: Double, var x2: Double, var y2: Double, var r2: Double, rect: JGRectangle) extends JGShape(x = rect.x, y = rect.y) with ColorStop {
  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    val radialGradient = ctx.createRadialGradient(x1, y1, r1, x2, y2, r2)
    this.colors.colorStop.foreach(c => radialGradient.addColorStop(c._1, c._2))
    if(this.parameters.fill){
      ctx.fillStyle = radialGradient
      ctx.fillRect(rect.x, rect.y, rect.width, rect.height)
    }
    else {
      ctx.strokeStyle = radialGradient
      ctx.strokeRect(rect.x, rect.y, rect.width, rect.height)
    }
  }
}

trait ColorStop {
  object colors {
    var colorStop: ArrayBuffer[(Double, String)] = ArrayBuffer()
  }

  def addColorStop(offset: Double, color: String) : Unit = {
    this.colors.colorStop.append((offset, color))
  }
}

trait LineParameters {
  object lineParameters {
    var strokeLineCap : String = "butt"
    var lineJoin : String = "round"
    var miterLimit : Double = 10.0
  }

  def strokeLineCap(endType: String) : Unit = {
    this.lineParameters.strokeLineCap = endType
  }

  def lineJoin(ltype: String) : Unit = {
    this.lineParameters.lineJoin = ltype
  }

  def miterLimit(value: Double) : Unit = {
    this.lineParameters.miterLimit = value
  }

  def setParameters(ctx: dom.CanvasRenderingContext2D): Unit ={
    ctx.lineCap = this.lineParameters.strokeLineCap
    ctx.lineJoin = this.lineParameters.lineJoin
    ctx.miterLimit = this.lineParameters.miterLimit
  }

}

case class JGCircle(var xc: Double, var yc: Double, var radius: Double) extends JGShape(x = xc, y = yc) {

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    if(this.parameters.fill) {
      ctx.beginPath()
      ctx.arc(x, y, radius, 0.0, Math.PI * 2, true)
      ctx.fill()

    }
    else {
      ctx.beginPath()
      ctx.arc(x, y, radius, 0.0, Math.PI * 2, true)
      ctx.stroke()
    }

  }
}

case class JGArc(var xa: Double, var ya: Double, var radius: Double, var startingAngle: Double, var endAngle: Double, var anticlockwise: Boolean) extends JGShape(x = xa, y = ya){
  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    if(this.parameters.fill) {
      ctx.beginPath()
      ctx.arc(x, y, radius, startingAngle, endAngle, anticlockwise)
      ctx.fill()

    }
    else {
      ctx.beginPath()
      ctx.arc(x, y, radius, startingAngle, endAngle, anticlockwise)
      ctx.stroke()
    }
  }
}

case class JGQuadraticCurve(var cp1x: Double, var cp1y: Double, var xc: Double, var yc: Double) extends JGShape(x = xc, y = yc) {

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    if(this.parameters.fill){
      ctx.beginPath()
      ctx.quadraticCurveTo(cp1x, cp1y, x, y)
      ctx.fill()
    }
    else {
      ctx.beginPath()
      ctx.quadraticCurveTo(cp1x, cp1y, x, y)
      ctx.stroke()
    }

  }
}

case class JGBezierCurve(var cp1x: Double, var cp1y: Double, var cp2x: Double, var cp2y: Double, var xc: Double, var yc: Double) extends JGShape(x = xc, y = yc) {

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    if(this.parameters.fill){
      ctx.beginPath()
      ctx.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
      ctx.fill()
    }
    else {
      ctx.beginPath()
      ctx.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
      ctx.stroke()
    }
  }
}

object Extensions {
  implicit class jgShapeArrayExtend[ApplyOn <: JGShape](s: Array[ApplyOn]){
    def translateY(i: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.translateY(i))
      s
    }

    def translateX(i: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.translateX(i))
      s
    }

    def translate(x: Double, y: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.translate(x, y))
      s
    }

    def changeColor(c: String) : Array[ApplyOn] = {
      s.foreach(sh => sh.changeColor(c))
      s
    }

    def fill(b: Boolean) : Array[ApplyOn] = {
      s.foreach(sh => sh.fill(b))
      s
    }

    def strokeWidth(w: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.strokeWidth(w))
      s
    }

    def clearArea(b: Boolean) : Array[ApplyOn] = {
      s.foreach(sh => sh.clearArea(b))
      s
    }

    def transparency(t: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.transparency(t))
      s
    }
  }
}