package LINGI2132.JuGuiDSL.Interactions

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom.CanvasRenderingContext2D

import scala.collection.mutable.Queue

case class ChangeColorInteraction(colors: Queue[String]) extends Interaction[JGShape] {
  override def interact(shape: JGShape, ctx: CanvasRenderingContext2D, x: Double, y: Double): Unit = {
    println("dbl")
    colors.enqueue(shape.parameters.color)
    if(shape.inLimits(x, y)){
      changeColor(shape, ctx)
    }
  }

  def changeColor(shape: JGShape, ctx: CanvasRenderingContext2D): Unit = {
    shape.changeColor(colors.dequeue())
    colors.enqueue(shape.parameters.color)
    shape.draw(ctx)
  }
}
