package LINGI2132.JuGuiDSL.JGShape

import LINGI2132.JuGuiDSL.Animations.AnimationTraits.Animable
import LINGI2132.JuGuiDSL.Interactions.InteractionTraits.Interactive
import org.scalajs.dom

import scala.scalajs.js.{Array => ArrayJs}


/**
  * this class is used to provide a default constructor, default functions
  * and default values (contained in objects) for all classes extending this abstract class.
  * Transformations are only applied when the shape is removed then drawn again
 */

abstract class JGShape(var x: Double, var y: Double) extends Animable with Interactive {


  object parameters { //default values
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

    object rotation { //default values for rotation
      var angle: Double = 0
      var centerRotationX : Double = 0
      var centerRotationY : Double = 0
    }

    object shadow { //default values for the shadow
      var shadowOffsetX : Double = 0
      var shadowOffsetY : Double = 0
      var shadowBlur : Double = 0
      var shadowColor : String = "#000000"
    }

    object transformation { //default values for transformation
      var horizontalScaling : Double = 1
      var horizontalSkewing : Double = 0
      var verticalSkewing : Double = 0
      var verticalScaling : Double = 1
      var horizontalMoving : Double = 0
      var verticalMoving : Double = 0
    }
  }

  /**
    * Sets global parameters for the context
    * @param ctx An instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
  def contextSetGlobalParameters(ctx: dom.CanvasRenderingContext2D): Unit ={
    ctx.globalCompositeOperation = this.parameters.compositeOperation
    ctx.globalAlpha = this.parameters.transparency
    ctx.shadowOffsetX = this.parameters.shadow.shadowOffsetY
    ctx.shadowOffsetY = this.parameters.shadow.shadowOffsetY
    ctx.shadowBlur = this.parameters.shadow.shadowBlur
    ctx.shadowColor = this.parameters.shadow.shadowColor
  }

  /**
    * Sets specific type parameters for the context
    * @param ctx An instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
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

  /**
    * Sets transformation parameters for the context
    * @param ctx An instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
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

  /**
    * Resets transformation parameters
    * @param ctx An instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
  def contextResetTransformation(ctx: dom.CanvasRenderingContext2D): Unit = {
    this.x = this.x * this.parameters.scaleX
    this.y = this.y * this.parameters.scaleY
    ctx.scale(1/this.parameters.scaleX, 1/this.parameters.scaleY)
    ctx.rotate(-this.parameters.rotation.angle)
    ctx.setTransform(1, 0, 0, 1, 0, 0)
  }

  /**
    * Draws a shape
    * @param ctx An instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
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

  /**
    * Draws a shape and set important parameters
    * @param ctx An instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
  def draw(ctx: dom.CanvasRenderingContext2D) : Unit = {
    contextSetGlobalParameters(ctx)

    contextSetSpecificTypeParameters(ctx)

    contextSetTransformation(ctx)

    drawShape(ctx)

    contextResetTransformation(ctx)

    applyShape(ctx)
  }

  /**
    * Draws the shape
    * @param ctx An instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    */
  def drawShape(ctx: dom.CanvasRenderingContext2D) : Unit

  /**
    * @param xInput x coordinate of a point
    * @param yInput y coordinate of a point
    * @return true if (xInput, yInput) is contained in the shape's area, else false
    */
  def inLimits(xInput: Double, yInput: Double) : Boolean

  /**
    * moves the shape horizontally according to @param i
    */
  def translateX(i: Double) : Unit = {
    this.x += i
  }

  /**
    * moves the shape vertically according to @param i
    */
  def translateY(i: Double) : Unit = {
    this.y += i
  }

  /**
    * moves the shape horizontally and vertically according to
    * @param x (horizontal)
    * @param y (vertical)
    */
  def translate(x: Double, y: Double) : Unit = {
    this.x += x
    this.y += y
  }


  /**
    * change the color parameter of the shape according to @param c
    */
  def changeColor(c: String) : Unit = {
    this.parameters.color = c
  }

  /**
    * sets the boolean fill parameter of the shape to @param b
    */
  def fill(b: Boolean) : Unit = {
    this.parameters.fill = b
  }

  /**
    * sets the strokewidth parameter of the shape to @param w
    */
  def strokeWidth(w: Double) : Unit = {
    this.parameters.strokeWidth = w
  }

  /**
    * clears the area of the shape by filling it with white
    */
  def clearArea() : Unit = {
    this.parameters.fill = true
    this.parameters.color = "#FFFFFF"
  }

  /**
    * sets the transparency parameter of the shape to @param t
    */
  def transparency(t: Double) : Unit = {
    this.parameters.transparency = t
  }

  /**
    * sets the lineDash parameter of the shape to @param value
    */
  def setLineDash(value: ArrayJs[Double]) : Unit = {
    this.parameters.lineDash = value
  }

  /**
    * sets the lineDashOffset parameter of the shape to @param value
    */
  def lineDashOffset(value: Double) : Unit = {
    this.parameters.lineDashOffset = value
  }

  /**
    * sets the shadowOffsetX parameter of the shape to @param offset
    */
  def shadowOffsetX(offset : Double): Unit = {
    this.parameters.shadow.shadowOffsetX = offset
  }

  /**
    * sets the shadowOffsetY parameter of the shape to @param offset
    */
  def shadowOffsetY(offset: Double): Unit = {
    this.parameters.shadow.shadowOffsetY = offset
  }

  /**
    * sets the shadowBlur parameter of the shape to @param blur
    */
  def shadowBlur(blur: Double): Unit = {
    this.parameters.shadow.shadowBlur = blur
  }

  /**
    * sets the shadowColor parameter of the shape to @param color
    */
  def shadowColor(color: String): Unit = {
    this.parameters.shadow.shadowColor = color
  }

  /**
    * rotates the shape according to the following parameters
    * @param angle Angle according to which the shape has to rotate
    * @param centerX and @param centerY: Specify where the center of the rotation will be
    */
  def rotate(angle : Double, centerX: Double, centerY: Double): Unit = {
    this.parameters.rotation.angle = angle
    this.parameters.rotation.centerRotationX = centerX
    this.parameters.rotation.centerRotationY = centerY
  }

  /**
    * scales the shape according to
    * @param x horizontal scaling
    * @param y vertical scaling
    */
  def scale(x: Double, y: Double): Unit = {
    this.parameters.scaleY = y
    this.parameters.scaleX = x
  }

  /**
    * Add a transformation that is the result of the following parameters
    * @param horizontalScaling
    * @param horizontalSkewing
    * @param verticalSkewing
    * @param verticalScaling
    * @param horizontalMoving
    * @param verticalMoving
    */
  def transform(horizontalScaling: Double, horizontalSkewing: Double, verticalSkewing: Double,
                verticalScaling: Double, horizontalMoving: Double, verticalMoving: Double): Unit = {
    this.parameters.transformation.horizontalScaling = horizontalScaling
    this.parameters.transformation.horizontalSkewing = horizontalSkewing
    this.parameters.transformation.verticalSkewing = verticalSkewing
    this.parameters.transformation.verticalScaling = verticalScaling
    this.parameters.transformation.horizontalMoving = horizontalMoving
    this.parameters.transformation.verticalMoving = verticalMoving
  }

  /**
    * Sets a composite operation
    * @param typeCompositeOperation the composite operation to apply
    */
  def setGlobalCompositeOperation(typeCompositeOperation : String): Unit ={
    this.parameters.compositeOperation = typeCompositeOperation
  }

  /**
    * sets the boolean clip parameter of the shape to @param clip
    */
  def clip(clip : Boolean): Unit ={
    this.parameters.clip = clip
  }

}
