package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom

/**
  * @param xs
  * @param ys
  * @param cp1x The x-axis coordinate of the first control point.
  * @param cp1y The y-axis coordinate of the first control point.
  * @param cp2x The x-axis coordinate of the second control point.
  * @param cp2y The y-axis coordinate of the second control point.
  * @param xc The x-axis coordinate of the end point.
  * @param yc The y-axis coordinate of the end point.
  */
case class JGBezierCurve(var xs: Double, var ys: Double, var cp1x: Double, var cp1y: Double, var cp2x: Double, var cp2y: Double, var xe: Double, var ye: Double) extends JGShape(x = xe, y = ye) {


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    false
  }

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.moveTo(xs, ys)
    ctx.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
  }
}
