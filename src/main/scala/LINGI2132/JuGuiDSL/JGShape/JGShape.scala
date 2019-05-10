package LINGI2132.JuGuiDSL.JGShape

import LINGI2132.JuGuiDSL.Animations.AnimationTraits.Animable
import LINGI2132.JuGuiDSL.Interactions.Interactive
import org.scalajs.dom

import scala.scalajs.js.{Array => ArrayJs}



//this class is used to provide a default constructor, default functions
// and default values (contained in objects) for all classes extending this abstract class
abstract class JGShape(var x: Double, var y: Double) extends Animable with Interactive {

  object parameters {
    var color : String = "#000000"
    var strokeWidth : Double = 1.0
    var transparency: Double = 1.0
    var fill : Boolean = false
    var clip : Boolean = false
    var lineDashOffset : Double = 0
    var lineDash: ArrayJs[Double] = ArrayJs.apply(0, 0)
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

  def inLimits(xInput: Double, yInput: Double) : Boolean

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

  def setLineDash(value: ArrayJs[Double]) : Unit = {
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
