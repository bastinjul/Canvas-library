package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import LINGI2132.JuGuiDSL.Shapes.ShapeTraits.ColorStop
import org.scalajs.dom.CanvasRenderingContext2D

case class JGRadialGradient(var x1: Double, var y1: Double, var r1: Double, var x2: Double, var y2: Double, var r2: Double, rect: JGRectangle) extends JGShape(x = rect.x, y = rect.y) with ColorStop {


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

