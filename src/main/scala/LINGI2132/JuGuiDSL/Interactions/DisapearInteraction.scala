package LINGI2132.JuGuiDSL.Interactions

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom.CanvasRenderingContext2D

import scala.scalajs.js.timers.setTimeout

case class DisapearInteraction(seconds: Double) extends Interaction[JGShape] {

  override def interact(shape: JGShape, ctx: CanvasRenderingContext2D, x: Double, y: Double): Unit = {
    println("smpl")
    if(shape.inLimits(x, y)){
      disapear(shape, ctx, shape.parameters.color, shape.parameters.fill)
    }
  }

  def disapear(shape: JGShape, ctx: CanvasRenderingContext2D, color: String, fill: Boolean): Unit = {
    shape.clearArea(true)
    shape.draw(ctx)
    shape.fill(false)
    shape.draw(ctx)
    if (seconds > 0){
      setTimeout(1000*seconds){
        shape.changeColor(color)
        shape.fill(fill)
        shape.draw(ctx)
      }

    }
  }
}
