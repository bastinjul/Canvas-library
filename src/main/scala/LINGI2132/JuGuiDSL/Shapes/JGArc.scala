package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom.CanvasRenderingContext2D

/**
  *
  * @param xa The x-axis (horizontal) coordinate of the arc's center.
  * @param ya The y-axis (vertical) coordinate of the arc's center.
  * @param radius The arc's radius. Must be non-negative.
  * @param startingAngle The angle at which the arc starts, measured
  *                      clockwise from the positive x-axis and expressed in radians.
  * @param endAngle The angle at which the arc ends, measured clockwise from
  *                 the positive x-axis and expressed in radians.
  * @param anticlockwise An optional Boolean which, if true, causes the arc to be drawn counter-clockwise between the start and end angles.
  *                      The default value is false (clockwise).
  */
case class JGArc(var xa: Double, var ya: Double, var radius: Double, var startingAngle: Double, var endAngle: Double, var anticlockwise: Boolean) extends JGShape(x = xa, y = ya){


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    JGCircle(xa, ya, radius).inLimits(xInput, yInput)
  }

  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.arc(x, y, radius, startingAngle, endAngle, anticlockwise)
  }
}