package LINGI2132

import org.scalajs.dom
import org.scalajs.dom.raw.{Event, HTMLImageElement}
import org.scalajs.dom.{CanvasRenderingContext2D, html}
import scala.scalajs.js.timers._

import scala.collection.mutable.ArrayBuffer

class Canvasjugui(canvas: html.Canvas) {

  val ctx : dom.CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

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

  def anime(shape: JGShape, animation: Animation[JGShape], animationZone: AnimationZone): Unit = {
    animation match{
      case lineDashAnimation : LineDashAnimation => lineDashAnimation.anime(shape, ctx, animationZone)
      case rotationAnimation: RotationAnimation => rotationAnimation.anime(shape, ctx, animationZone)
      case scalingAnimation: ScalingAnimation => scalingAnimation.anime(shape, ctx, animationZone)
    }
  }

}

class AnimationZone(xA: Double, yA: Double, widthA: Double, heightA: Double) {
  object zone{
    val x : Double = xA
    val y : Double = yA
    val width : Double = widthA
    val height : Double = heightA
  }
}

trait Animable {}

trait Animation [ApplyOn <: Animable] {
  def anime(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone) : Unit
  def draw(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone) : Unit
  def march(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone): Unit
}

case class LineDashAnimation(lineDash: scala.scalajs.js.Array[Double]) extends Animation[JGShape] {
  var offset = 0

  override def anime(shape: JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    shape.setLineDash(lineDash)
    march(shape, ctx, animationZone)
  }

  override def draw(shape:JGShape, ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone): Unit ={
    ctx.clearRect(animationZone.zone.x, animationZone.zone.y, animationZone.zone.width, animationZone.zone.height)
    shape.fill(false)
    shape.lineDashOffset(-this.offset)
    shape.setLineDash(lineDash)
    shape.draw(ctx)
  }

  override def march(shape:JGShape,ctx: dom.CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    offset += 1
    if(offset > 16){
      offset = 0
    }
    draw(shape, ctx, animationZone)
    setTimeout(20) {
      march(shape, ctx, animationZone)
    }
  }
}

case class RotationAnimation(centerX: Double, centerY: Double) extends Animation[JGShape] {
  var angle : Double = 0
  override def anime(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    march(shape, ctx, animationZone)
  }

  override def draw(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit ={
    ctx.clearRect(animationZone.zone.x, animationZone.zone.y, animationZone.zone.width, animationZone.zone.height)
    shape.rotate(angle, centerX, centerY)
    shape.draw(ctx)
  }

  override def march(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    angle += Math.PI / 180
    if (angle == Math.PI) {
      angle = 0
    }
    draw(shape, ctx, animationZone)
    setTimeout(60) {
      march(shape, ctx, animationZone)
    }
  }
}

case class ScalingAnimation(minXScale : Double, minYScale: Double, maxXScale: Double, maxYScale: Double) extends Animation[JGShape]{
  var scaleX : Double = minXScale
  var scaleY : Double = minYScale
  var ascent : Boolean = true

  override def anime(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    march(shape, ctx, animationZone)
  }

  override def draw(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    ctx.clearRect(animationZone.zone.x, animationZone.zone.y, animationZone.zone.width, animationZone.zone.height)
    shape.scale(scaleX, scaleY)
    shape.draw(ctx)
  }

  override def march(shape: JGShape, ctx: CanvasRenderingContext2D, animationZone: AnimationZone): Unit = {
    if (ascent) {
      scaleX += (maxXScale - minYScale) / 50
      scaleY += (maxYScale - minYScale) / 50
    }
    else {
      scaleX -= (maxXScale - minYScale) / 50
      scaleY -= (maxYScale - minYScale) / 50
    }
    if (scaleX >= maxXScale) {
      ascent = false
    }
    if (scaleX <= minXScale) {
      ascent = true
    }

    draw(shape, ctx, animationZone)
    setTimeout(100) {
      march(shape, ctx, animationZone)
    }
  }
}


abstract class JGShape(var x: Double, var y: Double) extends Animable {

  object parameters {
    var color : String = "#000000"
    var strokeWidth : Double = 1.0
    var transparency: Double = 1.0
    var fill : Boolean = false
    var clip : Boolean = false
    var lineDashOffset : Double = 0
    var lineDash: scala.scalajs.js.Array[Double] = scala.scalajs.js.Array.apply(0, 0)
    var scaleX : Double = 1
    var scaleY : Double = 1
    var compositeOperation : String = "source-over"

    object rotation {
      var angle: Double = 0
      var centerRotationX : Double = 0
      var centerRotationY : Double = 0
    }

    object shadow {
      var shadowOffsetX : Double = 0
      var shadowOffsetY : Double = 0
      var shadowBlur : Double = 0
      var shadowColor : String = "#000000"
    }

    object transformation {
      var horizontalScaling : Double = 1
      var horizontalSkewing : Double = 0
      var verticalSkewing : Double = 0
      var verticalScaling : Double = 1
      var horizontalMoving : Double = 0
      var verticalMoving : Double = 0
    }
  }

  def contextSetGlobalParameters(ctx: dom.CanvasRenderingContext2D): Unit ={
    ctx.globalCompositeOperation = this.parameters.compositeOperation
    ctx.globalAlpha = this.parameters.transparency
    ctx.shadowOffsetX = this.parameters.shadow.shadowOffsetY
    ctx.shadowOffsetY = this.parameters.shadow.shadowOffsetY
    ctx.shadowBlur = this.parameters.shadow.shadowBlur
    ctx.shadowColor = this.parameters.shadow.shadowColor
  }

  def contextSetSpecificTypeParameters(ctx: dom.CanvasRenderingContext2D): Unit ={
    if(this.parameters.fill) {
      ctx.fillStyle = this.parameters.color
    }
    else {
      ctx.setLineDash(this.parameters.lineDash)
      ctx.lineDashOffset = this.parameters.lineDashOffset
      ctx.strokeStyle = this.parameters.color
      ctx.lineWidth = this.parameters.strokeWidth
    }
  }

  def contextSetTransformation(ctx: dom.CanvasRenderingContext2D): Unit ={
    ctx.setTransform(this.parameters.transformation.horizontalScaling, this.parameters.transformation.horizontalSkewing,
      this.parameters.transformation.verticalSkewing, this.parameters.transformation.verticalScaling,
      this.parameters.transformation.horizontalMoving, this.parameters.transformation.verticalMoving)
    ctx.translate(this.parameters.rotation.centerRotationX, this.parameters.rotation.centerRotationY)
    ctx.rotate(this.parameters.rotation.angle)
    ctx.translate(-this.parameters.rotation.centerRotationX, -this.parameters.rotation.centerRotationY)
    ctx.scale(this.parameters.scaleX, this.parameters.scaleY)
    this.x = this.x / this.parameters.scaleX
    this.y = this.y / this.parameters.scaleY
  }

  def contextResetTransformation(ctx: dom.CanvasRenderingContext2D): Unit = {
    this.x = this.x * this.parameters.scaleX
    this.y = this.y * this.parameters.scaleY
    ctx.scale(1/this.parameters.scaleX, 1/this.parameters.scaleY)
    ctx.rotate(-this.parameters.rotation.angle)
    ctx.setTransform(1, 0, 0, 1, 0, 0)
  }

  def applyShape(ctx: dom.CanvasRenderingContext2D): Unit ={
    if(this.parameters.fill) {
      ctx.fill()
    }
    else if (this.parameters.clip) {
      ctx.clip()
    }
    else {
      ctx.stroke()
    }
  }

  def draw(ctx: dom.CanvasRenderingContext2D) : Unit = {
    contextSetGlobalParameters(ctx)

    contextSetSpecificTypeParameters(ctx)

    contextSetTransformation(ctx)

    drawShape(ctx)

    contextResetTransformation(ctx)

    applyShape(ctx)
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

  def shadowOffsetX(offset : Double): Unit = {
    this.parameters.shadow.shadowOffsetX = offset
  }

  def shadowOffsetY(offset: Double): Unit = {
    this.parameters.shadow.shadowOffsetY = offset
  }

  def shadowBlur(blur: Double): Unit = {
    this.parameters.shadow.shadowBlur = blur
  }

  def shadowColor(color: String): Unit = {
    this.parameters.shadow.shadowColor = color
  }

  def rotate(angle : Double, centerX: Double, centerY: Double): Unit = {
    this.parameters.rotation.angle = angle
    this.parameters.rotation.centerRotationY = centerY
    this.parameters.rotation.centerRotationX = centerX
  }

  def scale(x: Double, y: Double): Unit = {
    this.parameters.scaleY = y
    this.parameters.scaleX = x
  }

  def transform(horizontalScaling: Double, horizontalSkewing: Double, verticalSkewing: Double,
                verticalScaling: Double, horizontalMoving: Double, verticalMoving: Double): Unit = {
    this.parameters.transformation.horizontalScaling = horizontalScaling
    this.parameters.transformation.horizontalSkewing = horizontalSkewing
    this.parameters.transformation.verticalSkewing = verticalSkewing
    this.parameters.transformation.verticalScaling = verticalScaling
    this.parameters.transformation.horizontalMoving = horizontalMoving
    this.parameters.transformation.verticalMoving = verticalMoving
  }

  def setGlobalCompositeOperation(typeCompositeOperation : String): Unit ={
    this.parameters.compositeOperation = typeCompositeOperation
  }

  def clip(clip : Boolean): Unit ={
    this.parameters.clip = clip
  }

}

case class JGRectangle(var xr: Double, var yr: Double, var width: Double, var height: Double) extends JGShape(x = xr, y = yr) {

  override def draw(ctx: CanvasRenderingContext2D): Unit = {
    contextSetGlobalParameters(ctx)

    contextSetSpecificTypeParameters(ctx)

    contextSetTransformation(ctx)

    drawShape(ctx)

    contextResetTransformation(ctx)
  }

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    if(this.parameters.fill) {
      ctx.fillRect(x, y , width, height)
    }
    else if (this.parameters.clip) {
      ctx.beginPath()
      ctx.rect(x, y , width, height)
      ctx.clip()
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
  }
}

case class JGLinearGradient(var x1: Double, var y1: Double, var x2: Double, var y2: Double, rect: JGRectangle) extends JGShape(x = rect.x, y = rect.y) with ColorStop {

  override def draw(ctx: CanvasRenderingContext2D): Unit = {
    contextSetGlobalParameters(ctx)

    contextSetSpecificTypeParameters(ctx)

    contextSetTransformation(ctx)

    drawShape(ctx)

    contextResetTransformation(ctx)
  }

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

  override def draw(ctx: CanvasRenderingContext2D): Unit = {
    contextSetGlobalParameters(ctx)

    contextSetSpecificTypeParameters(ctx)

    contextSetTransformation(ctx)

    drawShape(ctx)

    contextResetTransformation(ctx)
  }

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
    ctx.beginPath()
    ctx.arc(x, y, radius, 0.0, Math.PI * 2, true)

  }
}

case class JGArc(var xa: Double, var ya: Double, var radius: Double, var startingAngle: Double, var endAngle: Double, var anticlockwise: Boolean) extends JGShape(x = xa, y = ya){
  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.arc(x, y, radius, startingAngle, endAngle, anticlockwise)
  }
}

case class JGQuadraticCurve(var cp1x: Double, var cp1y: Double, var xc: Double, var yc: Double) extends JGShape(x = xc, y = yc) {

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.quadraticCurveTo(cp1x, cp1y, x, y)
  }
}

case class JGBezierCurve(var cp1x: Double, var cp1y: Double, var cp2x: Double, var cp2y: Double, var xc: Double, var yc: Double) extends JGShape(x = xc, y = yc) {

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
  }
}

case class JGPatterns(var src: String, patternType: String, rect: JGRectangle) extends JGShape(x = rect.x, y = rect.y) {

  override def draw(ctx: CanvasRenderingContext2D): Unit = {
    contextSetGlobalParameters(ctx)

    contextSetSpecificTypeParameters(ctx)

    contextSetTransformation(ctx)

    drawShape(ctx)

    contextResetTransformation(ctx)
  }

  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    this.parameters.fill = true
    val image = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    image.src = src
    image.onload = { evt: Event =>
      val pattern = ctx.createPattern(image, patternType)
      ctx.fillStyle = pattern
      ctx.fillRect(rect.x, rect.y, rect.width, rect.height)
    }
  }
}

case class JGImage(var src : String, var xi : Double, var yi : Double) extends JGShape(x = xi, y = yi) {

  object slice {
    var slicing : Boolean = false
    var sx : Double = 0
    var sy : Double = 0
    var sWidth : Double = 0
    var sHeight : Double = 0
    var dWidth : Double = 0
    var dHeight : Double = 0
  }

  override def draw(ctx: CanvasRenderingContext2D): Unit = {
    contextSetTransformation(ctx)

    drawShape(ctx)

    contextResetTransformation(ctx)
  }

  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    val image = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    image.src = src
    image.onload = { evt: Event =>
      if(this.slice.slicing)
        ctx.drawImage(image, this.slice.sx, this.slice.sy, this.slice.sWidth, this.slice.sHeight, this.x, this.y, this.slice.dWidth, this.slice.dHeight)
      else
        if (this.slice.dWidth > 0 && this.slice.dHeight > 0)
          ctx.drawImage(image, x, y, this.slice.dWidth, this.slice.dHeight)
        else
          ctx.drawImage(image, x, y)
    }
  }

  def resize(width: Double, height: Double): Unit = {
    this.slice.dWidth = width
    this.slice.dHeight = height
  }

  def slice(sx: Double, sy: Double, sWidth: Double, sHeight : Double, dx: Double, dy: Double, dWidth: Double, dHeight: Double): Unit ={
    this.slice.sx = sx
    this.slice.sy = sy
    this.slice.sWidth = sWidth
    this.slice.sHeight = sHeight
    this.x = dx
    this.y = dy
    this.slice.dWidth = dWidth
    this.slice.dHeight = dHeight
    this.slice.slicing = true
  }
}

case class JGText(var text: String, var xt: Double, var yt: Double) extends JGShape(x = xt, y = yt) with StylingText {

  override def draw(ctx: CanvasRenderingContext2D): Unit = {
    contextSetGlobalParameters(ctx)

    contextSetSpecificTypeParameters(ctx)

    contextSetTransformation(ctx)

    drawShape(ctx)

    contextResetTransformation(ctx)
  }

  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    ctx.font = this.textStyle.font
    ctx.textAlign = this.textStyle.textAlign
    ctx.textBaseline = this.textStyle.textBaseline
    if(this.parameters.fill){
      ctx.fillText(text, x, y)
    }
    else {
      ctx.strokeText(text, x, y)
    }
  }
}

trait StylingText{
  object textStyle {
    var font : String = "10px sans-serif"
    var textAlign : String = "start"
    var textBaseline : String = "alphabetic"
  }

  def font(font: String): Unit ={
    this.textStyle.font = font
  }

  def textAlign(textAlignment: String): Unit ={
    this.textStyle.textAlign = textAlignment
  }

  def textBaseline(textBaseline: String): Unit ={
    this.textStyle.textBaseline = textBaseline
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

    def setLineDash(value: scala.scalajs.js.Array[Double]) : Array[ApplyOn] = {
      s.foreach(sh => sh.setLineDash(value))
      s
    }

    def lineDashOffset(value: Double) : Array[ApplyOn] = {
      s.foreach(sh => sh.lineDashOffset(value))
      s
    }

    def shadowOffsetX(offset : Double): Array[ApplyOn] = {
      s.foreach(sh => sh.shadowOffsetX(offset))
      s
    }

    def shadowOffsetY(offset: Double): Array[ApplyOn] = {
      s.foreach(sh => sh.shadowOffsetY(offset))
      s
    }

    def shadowBlur(blur: Double): Array[ApplyOn] = {
      s.foreach(sh => sh.shadowBlur(blur))
      s
    }

    def shadowColor(color: String): Array[ApplyOn] = {
      s.foreach(sh => sh.shadowColor(color))
      s
    }

    def rotate(rotation : Double, centerX : Double, centerY : Double): Array[ApplyOn] = {
      s.foreach(sh => sh.rotate(rotation, centerX, centerY))
      s
    }

    def scale(x: Double, y: Double): Array[ApplyOn] = {
      s.foreach(sh => sh.scale(x, y))
      s
    }

    def transform(horizontalScaling: Double, horizontalSkewing: Double, verticalSkewing: Double,
                  verticalScaling: Double, horizontalMoving: Double, verticalMoving: Double): Array[ApplyOn] = {
      s.foreach(sh => sh.transform(horizontalScaling, horizontalSkewing, verticalSkewing, verticalScaling, horizontalMoving, verticalMoving))
      s
    }

    def setGlobalCompositeOperation(typeCompositeOperation : String): Array[ApplyOn] ={
      s.foreach(sh => sh.setGlobalCompositeOperation(typeCompositeOperation))
      s
    }
  }
}