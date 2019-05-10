package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D

case class JGRectangle(var xr: Double, var yr: Double, var width: Double, var height: Double) extends JGShape(x = xr, y = yr) {

  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    xInput >= xr && xInput <= xr+width && yInput >= yr && yInput <= yr+height
  }

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
