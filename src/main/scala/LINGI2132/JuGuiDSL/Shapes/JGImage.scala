package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.raw.{Event, HTMLImageElement}

case class JGImage(var src : String, var xi : Double, var yi : Double) extends JGShape(x = xi, y = yi) {

  var image : HTMLImageElement =  dom.document.createElement("img").asInstanceOf[HTMLImageElement]
  image.src = src

  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    if (this.slice.dWidth > 0) {
      JGRectangle(xi, yi, this.slice.dWidth, this.slice.dHeight).inLimits(xInput, yInput)
    }
    else {
      JGRectangle(xi, yi, image.width, image.height).inLimits(xInput, yInput)
    }

  }

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
