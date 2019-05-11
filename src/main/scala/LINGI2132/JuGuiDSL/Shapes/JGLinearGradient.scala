package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import LINGI2132.JuGuiDSL.Shapes.ShapeTraits.ColorStop
import org.scalajs.dom.CanvasRenderingContext2D

/**
  *
  * @param x1 The x-axis coordinate of the start point.
  * @param y1 The y-axis coordinate of the start point.
  * @param x2 The x-axis coordinate of the end point.
  * @param y2 The y-axis coordinate of the end point.
  * @param rect A rectangle in which the linear gradient will fit
  */
case class JGLinearGradient(var x1: Double, var y1: Double, var x2: Double, var y2: Double, rect: JGRectangle) extends JGShape(x = rect.x, y = rect.y) with ColorStop {


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    rect.inLimits(xInput, yInput)
  }

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