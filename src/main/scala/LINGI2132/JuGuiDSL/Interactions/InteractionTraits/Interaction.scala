package LINGI2132.JuGuiDSL.Interactions.InteractionTraits

import LINGI2132.JuGuiDSL.JGShape.JGShape
import org.scalajs.dom

trait Interaction [ApplyOn <: Interactive] {
  /**
    * make an interaction happen on a shape
    * @param shape the shape
    * @param ctx An instance of CanvasRenderingContext2D used for drawing shapes, text, ...
    * @param x The x coordinate where the click has taken place
    * @param y The y coordinate where the click has taken place
    */
  def interact(shape: JGShape, ctx: dom.CanvasRenderingContext2D, x: Double, y: Double)
}