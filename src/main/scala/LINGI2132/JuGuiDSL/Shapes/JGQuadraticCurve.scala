package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom

case class JGQuadraticCurve(var cp1x: Double, var cp1y: Double, var xc: Double, var yc: Double) extends JGShape(x = xc, y = yc) {


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    false
  }

  override def drawShape(ctx: dom.CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.quadraticCurveTo(cp1x, cp1y, x, y)
  }
}
