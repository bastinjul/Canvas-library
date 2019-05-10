package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import LINGI2132.JuGuiDSL.Shapes.ShapeTraits.StylingText
import org.scalajs.dom.CanvasRenderingContext2D

case class JGText(var text: String, var xt: Double, var yt: Double) extends JGShape(x = xt, y = yt) with StylingText {


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    false
  }

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