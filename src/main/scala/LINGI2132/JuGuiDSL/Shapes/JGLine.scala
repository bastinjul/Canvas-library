package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import LINGI2132.JuGuiDSL.Shapes.ShapeTraits.LineParameters
import org.scalajs.dom

/**
  *
  * @param x1 x coordinate start of line
  * @param y1 y coordinate start of line
  * @param x2 x coordinate end of line
  * @param y2 y coordinate end of line
  */
case class JGLine(var x1: Double, var y1: Double, var x2: Double, var y2: Double) extends JGShape(x = x1, y = y1) with LineParameters {

  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    false
  }

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    setParameters(ctx)
    ctx.moveTo(x1, y1)
    ctx.lineTo(x2, y2)
  }
}