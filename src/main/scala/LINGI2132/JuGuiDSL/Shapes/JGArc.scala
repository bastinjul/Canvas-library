package LINGI2132.JuGuiDSL.Shapes

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom.CanvasRenderingContext2D

case class JGArc(var xa: Double, var ya: Double, var radius: Double, var startingAngle: Double, var endAngle: Double, var anticlockwise: Boolean) extends JGShape(x = xa, y = ya){


  override def inLimits(xInput: Double, yInput: Double): Boolean = {
    JGCircle(xa, ya, radius).inLimits(xInput, yInput)
  }

  override def drawShape(ctx: CanvasRenderingContext2D): Unit = {
    ctx.beginPath()
    ctx.arc(x, y, radius, startingAngle, endAngle, anticlockwise)
  }
}