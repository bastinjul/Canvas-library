package LINGI2132.JuGuiDSL.Interactions

import LINGI2132.JuGuiDSL.Interactions.InteractionTraits.Interaction
import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom.CanvasRenderingContext2D

import scala.scalajs.js.timers.setTimeout

/**
  * @param seconds number of seconds to wait for the shape to reappear,
  *                -1 for not make it appear again
  */
case class DisappearInteraction(seconds: Double) extends Interaction[JGShape] {

  override def interact(shape: JGShape, ctx: CanvasRenderingContext2D, x: Double, y: Double): Unit = {
    println("smpl")
    if(shape.inLimits(x, y)){
      disappear(shape, ctx, shape.parameters.color, shape.parameters.fill)
    }
  }

  /**
    * makes a shape disappear
    * @param shape the shape that has to be disappeared
    * @param ctx an instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    * @param color the colour that will fill the shape to make it look disappeared
    * @param fill to specify whether the disappeared shape has to be filled or not
    */
  def disappear(shape: JGShape, ctx: CanvasRenderingContext2D, color: String, fill: Boolean): Unit = {
    shape.clearArea()
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
