package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom

/**
  *
  * @param cp1x The x-axis coordinate of the control point.
  * @param cp1y The y-axis coordinate of the control point.
  * @param xe The x-axis coordinate of the end point.
  * @param ye The y-axis coordinate of the end point.
  */
case class JGQuadraticCurve(var xs: Double, var ys: Double, var cp1x: Double, var cp1y: Double, var xe: Double, var ye: Double) extends JGShape(x = xe, y = ye) {


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    false
  }

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.moveTo(xs, ys)
    ctx.quadraticCurveTo(cp1x, cp1y, x, y)
  }
}
