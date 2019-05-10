package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom

case class JGBezierCurve(var cp1x: Double, var cp1y: Double, var cp2x: Double, var cp2y: Double, var xc: Double, var yc: Double) extends JGShape(x = xc, y = yc) {


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    false
    //TODO
  }

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.bezierCurveTo(cp1x, cp1y, cp2x, cp2y, x, y)
  }
}
