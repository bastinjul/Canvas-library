package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom

/**
  *
  * @param xc x coordinate of the center of the circle
  * @param yc y coordinate of the center of the circle
  * @param radius radius of the circle
  */
case class JGCircle(var xc: Double, var yc: Double, var radius: Double) extends JGShape(x = xc, y = yc) {


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    JGRectangle(xc-radius, yc-radius, 2*radius, 2*radius).inLimits(xInput, yInput)
  }

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.arc(x, y, radius, 0.0, Math.PI * 2, true)

  }
}
